/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.pricingai.controller;

import cn.zhuatech.pricingai.common.ApiResponse;
import cn.zhuatech.pricingai.service.PricingOptimizationService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/ai/pricing")
@PreAuthorize("hasAnyRole('DOMAIN_USER','DOMAIN_OPERATOR','ADMIN')")
public class PricingOptimizationController {
    private final PricingOptimizationService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public PricingOptimizationController(PricingOptimizationService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/recommend")
    public ApiResponse<PricingOptimizationService.Result> recommend(
        @Valid @RequestBody PricingOptimizationService.Request request) {
        return ApiResponse.ok("价格建议生成完成", service.recommend(request));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/simulate-guardrail")
    public ApiResponse<PricingOptimizationService.SimulationResult> simulateGuardrail(
        @Valid @RequestBody PricingOptimizationService.SimulationRequest request) {
        return ApiResponse.ok("价格护栏模拟完成", service.simulateGuardrail(request));
    }
}
