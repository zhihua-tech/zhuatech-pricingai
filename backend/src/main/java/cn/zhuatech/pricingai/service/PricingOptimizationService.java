/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pricingai.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PricingOptimizationService {
    public Result recommend(Request request) {
        BigDecimal floorPrice = request.unitCost().multiply(BigDecimal.ONE.add(request.minimumMarginRate()));
        BigDecimal marketAnchor = request.competitorMedianPrice().multiply(new BigDecimal("0.6"))
            .add(request.currentPrice().multiply(new BigDecimal("0.4")));
        BigDecimal adjustment = BigDecimal.ONE;
        if (request.inventoryCoverDays() > 90) adjustment = adjustment.subtract(new BigDecimal("0.08"));
        else if (request.inventoryCoverDays() < 14) adjustment = adjustment.add(new BigDecimal("0.06"));
        if (request.priceElasticity().compareTo(new BigDecimal("1.5")) > 0) adjustment = adjustment.subtract(new BigDecimal("0.03"));
        BigDecimal recommended = marketAnchor.multiply(adjustment).max(floorPrice).setScale(2, RoundingMode.HALF_UP);
        BigDecimal marginRate = recommended.subtract(request.unitCost()).divide(recommended, 4, RoundingMode.HALF_UP);
        BigDecimal changeRate = recommended.subtract(request.currentPrice())
            .divide(request.currentPrice(), 4, RoundingMode.HALF_UP);
        String action = changeRate.compareTo(new BigDecimal("0.02")) > 0 ? "RAISE"
            : changeRate.compareTo(new BigDecimal("-0.02")) < 0 ? "LOWER" : "HOLD";
        List<String> reasons = new ArrayList<>();
        if (request.inventoryCoverDays() > 90) reasons.add("库存覆盖天数偏高，需要加快周转");
        if (request.inventoryCoverDays() < 14) reasons.add("库存偏紧，价格需要保护供应能力");
        if (request.priceElasticity().compareTo(new BigDecimal("1.5")) > 0) reasons.add("需求价格敏感度较高");
        if (recommended.compareTo(floorPrice) == 0) reasons.add("推荐价受最低毛利护栏约束");
        if (reasons.isEmpty()) reasons.add("价格、竞争与库存信号处于合理区间");
        return new Result(request.skuCode(), recommended, floorPrice.setScale(2, RoundingMode.HALF_UP),
            marginRate, changeRate, action, reasons, true);
    }

    public SimulationResult simulateGuardrail(SimulationRequest request) {
        BigDecimal floorPrice = request.unitCost().multiply(BigDecimal.ONE.add(request.minimumMarginRate()))
            .setScale(2, RoundingMode.HALF_UP);
        BigDecimal changeRate = request.candidatePrice().subtract(request.currentPrice())
            .divide(request.currentPrice(), 4, RoundingMode.HALF_UP);
        BigDecimal demandFactor = BigDecimal.ONE.subtract(request.priceElasticity().multiply(changeRate))
            .max(new BigDecimal("0.10")).min(new BigDecimal("2.50"));
        int projectedUnits = BigDecimal.valueOf(request.baselineUnits()).multiply(demandFactor)
            .setScale(0, RoundingMode.HALF_UP).intValue();
        BigDecimal projectedRevenue = request.candidatePrice().multiply(BigDecimal.valueOf(projectedUnits))
            .setScale(2, RoundingMode.HALF_UP);
        BigDecimal projectedGrossProfit = request.candidatePrice().subtract(request.unitCost())
            .multiply(BigDecimal.valueOf(projectedUnits)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal projectedMarginRate = request.candidatePrice().subtract(request.unitCost())
            .divide(request.candidatePrice(), 4, RoundingMode.HALF_UP);
        List<String> guardrailHits = new ArrayList<>();
        if (request.candidatePrice().compareTo(floorPrice) < 0) guardrailHits.add("候选价低于最低毛利价格");
        if (changeRate.abs().compareTo(request.maximumChangeRate()) > 0) guardrailHits.add("单次调价幅度超过审批门槛");
        if (projectedUnits < Math.round(request.baselineUnits() * 0.6f)) guardrailHits.add("预测销量下降超过 40%");
        if (guardrailHits.isEmpty()) guardrailHits.add("候选价格通过全部模拟护栏");
        String decision = request.candidatePrice().compareTo(floorPrice) < 0 ? "BLOCK"
            : changeRate.abs().compareTo(request.maximumChangeRate()) > 0 ? "REVIEW" : "APPROVE";
        return new SimulationResult(request.skuCode(), decision, floorPrice, changeRate,
            projectedMarginRate, projectedUnits, projectedRevenue, projectedGrossProfit, guardrailHits);
    }

    public record Request(@NotBlank String skuCode,
                          @DecimalMin("0.01") BigDecimal unitCost,
                          @DecimalMin("0.01") BigDecimal currentPrice,
                          @DecimalMin("0.01") BigDecimal competitorMedianPrice,
                          @DecimalMin("0") BigDecimal minimumMarginRate,
                          int inventoryCoverDays,
                          @DecimalMin("0") BigDecimal priceElasticity) {}
    public record Result(String skuCode, BigDecimal recommendedPrice, BigDecimal floorPrice,
                         BigDecimal projectedMarginRate, BigDecimal priceChangeRate,
                         String action, List<String> reasons, boolean approvalRequired) {}
    public record SimulationRequest(@NotBlank String skuCode,
                                    @DecimalMin("0.01") BigDecimal unitCost,
                                    @DecimalMin("0.01") BigDecimal currentPrice,
                                    @DecimalMin("0.01") BigDecimal candidatePrice,
                                    @Min(1) int baselineUnits,
                                    @DecimalMin("0") BigDecimal priceElasticity,
                                    @DecimalMin("0") @DecimalMax("1") BigDecimal minimumMarginRate,
                                    @DecimalMin("0.01") @DecimalMax("1") BigDecimal maximumChangeRate) {}
    public record SimulationResult(String skuCode, String guardrailDecision, BigDecimal floorPrice,
                                   BigDecimal priceChangeRate, BigDecimal projectedMarginRate,
                                   int projectedUnits, BigDecimal projectedRevenue,
                                   BigDecimal projectedGrossProfit, List<String> guardrailHits) {}
}
