/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.pricingai.service;

import jakarta.validation.constraints.DecimalMin;
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
}
