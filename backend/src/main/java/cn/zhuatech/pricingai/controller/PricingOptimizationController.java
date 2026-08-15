/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.pricingai.controller;

import cn.zhuatech.pricingai.common.ApiResponse;
import cn.zhuatech.pricingai.service.PricingOptimizationService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/pricing")
@PreAuthorize("hasAnyRole('DOMAIN_USER','DOMAIN_OPERATOR','ADMIN')")
public class PricingOptimizationController {
    private final PricingOptimizationService service;
    public PricingOptimizationController(PricingOptimizationService service) { this.service = service; }

    @PostMapping("/recommend")
    public ApiResponse<PricingOptimizationService.Result> recommend(
        @Valid @RequestBody PricingOptimizationService.Request request) {
        return ApiResponse.ok("价格建议生成完成", service.recommend(request));
    }
}
