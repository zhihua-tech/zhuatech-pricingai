/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pricingai;

import static org.junit.jupiter.api.Assertions.*;

import cn.zhuatech.pricingai.common.BusinessException;
import cn.zhuatech.pricingai.service.PricingOptimizationService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
class PricingMarginRegressionTests {
  private final PricingOptimizationService service = new PricingOptimizationService();

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void floorUsesRevenueMarginNotMarkup() {
    var r =
        service.recommend(
            new PricingOptimizationService.Request(
                "SKU",
                new BigDecimal("100"),
                new BigDecimal("130"),
                new BigDecimal("105"),
                new BigDecimal("0.30"),
                140,
                new BigDecimal("2")));
    assertEquals(new BigDecimal("142.86"), r.floorPrice());
    assertTrue(r.projectedMarginRate().compareTo(new BigDecimal("0.30")) >= 0);
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void simulationUsesSameFloor() {
    var r =
        service.simulateGuardrail(
            new PricingOptimizationService.SimulationRequest(
                "SKU",
                new BigDecimal("100"),
                new BigDecimal("145"),
                new BigDecimal("118"),
                1000,
                new BigDecimal("1.4"),
                new BigDecimal("0.25"),
                new BigDecimal("0.15")));
    assertEquals(new BigDecimal("133.34"), r.floorPrice());
    assertEquals("BLOCK", r.guardrailDecision());
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void rejectsImpossibleOneHundredPercentMargin() {
    assertThrows(
        BusinessException.class,
        () ->
            service.recommend(
                new PricingOptimizationService.Request(
                    "SKU",
                    BigDecimal.ONE,
                    BigDecimal.ONE,
                    BigDecimal.ONE,
                    BigDecimal.ONE,
                    1,
                    BigDecimal.ONE)));
  }

  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  @Test
  void roundingNeverUndercutsMarginFloor() {
    var r =
        service.recommend(
            new PricingOptimizationService.Request(
                "SKU",
                new BigDecimal("0.01"),
                new BigDecimal("0.01"),
                new BigDecimal("0.01"),
                new BigDecimal("0.3"),
                40,
                BigDecimal.ZERO));
    assertEquals(new BigDecimal("0.02"), r.floorPrice());
  }
}
