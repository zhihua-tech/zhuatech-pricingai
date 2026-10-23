/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pricingai.workbench;

import static cn.zhuatech.pricingai.workbench.WorkbenchInput.*;

import java.math.*;
import java.util.*;
import org.springframework.stereotype.Service;

/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
  /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
  public Map<String, Object> execute(Map<String, Object> r) {
    validate(r);
    keys(
        r,
        "unitCost",
        "listPrice",
        "discountRate",
        "commissionRate",
        "unitFreight",
        "targetMargin");
    var cost = money(r, "unitCost");
    var price = money(r, "listPrice");
    var freight = money(r, "unitFreight");
    var discount = decimal(r, "discountRate", 0, 0.99);
    var commission = decimal(r, "commissionRate", 0, 0.99);
    var target = decimal(r, "targetMargin", 0, 0.99);
    var factor = BigDecimal.ONE.subtract(commission).subtract(target);
    if (factor.signum() <= 0) throw new IllegalArgumentException("佣金率与目标贡献率之和必须小于1");
    var net = price.multiply(BigDecimal.ONE.subtract(discount));
    var fee = net.multiply(commission);
    var contribution = net.subtract(fee).subtract(cost).subtract(freight);
    var denominator = BigDecimal.ONE.subtract(discount).multiply(factor);
    var required = cost.add(freight).divide(denominator, 2, RoundingMode.CEILING);
    return Map.of(
        "netRevenue",
        net.setScale(2, RoundingMode.HALF_UP),
        "commission",
        fee.setScale(2, RoundingMode.HALF_UP),
        "contribution",
        contribution.setScale(2, RoundingMode.HALF_UP),
        "marginDefined",
        net.signum() > 0,
        "contributionMargin",
        net.signum() == 0 ? BigDecimal.ZERO : contribution.divide(net, 6, RoundingMode.HALF_UP),
        "minimumListPrice",
        required,
        "meetsTarget",
        net.signum() > 0 && contribution.compareTo(net.multiply(target)) >= 0);
  }
}
