/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.pricingai;

import cn.zhuatech.pricingai.service.PricingOptimizationService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

class PricingOptimizationServiceTests {
    private final PricingOptimizationService service = new PricingOptimizationService();

    @Test void lowersPriceForExcessInventory() {
        var result = service.recommend(new PricingOptimizationService.Request("SKU-001",
            new BigDecimal("60"), new BigDecimal("120"), new BigDecimal("110"),
            new BigDecimal("0.25"), 120, new BigDecimal("1.8")));
        assertThat(result.action()).isEqualTo("LOWER");
        assertThat(result.recommendedPrice()).isGreaterThanOrEqualTo(result.floorPrice());
    }

    @Test void protectsMarginFloor() {
        var result = service.recommend(new PricingOptimizationService.Request("SKU-002",
            new BigDecimal("100"), new BigDecimal("130"), new BigDecimal("105"),
            new BigDecimal("0.30"), 140, new BigDecimal("2.0")));
        assertThat(result.recommendedPrice()).isEqualByComparingTo("130.00");
        assertThat(result.reasons()).anyMatch(item -> item.contains("最低毛利"));
    }

    @Test void blocksCandidatePriceBelowMarginFloor() {
        var result = service.simulateGuardrail(new PricingOptimizationService.SimulationRequest(
            "SKU-002", new BigDecimal("100"), new BigDecimal("145"), new BigDecimal("118"),
            1000, new BigDecimal("1.4"), new BigDecimal("0.25"), new BigDecimal("0.15")));
        assertThat(result.guardrailDecision()).isEqualTo("BLOCK");
        assertThat(result.floorPrice()).isEqualByComparingTo("125.00");
        assertThat(result.guardrailHits()).anyMatch(item -> item.contains("最低毛利"));
    }
}
