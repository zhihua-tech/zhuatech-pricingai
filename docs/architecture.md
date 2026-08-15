# PRICINGAI 架构

版权所有 © 2026 上海如静知华信息科技有限公司。

Vue 3 管理端和 H5 工作台通过 JWT 调用 Spring Boot REST API。领域服务 `PricingOptimizationService` 计算市场锚点、库存调整与最低毛利护栏，所有建议保留人工审批；JPA 与 Flyway 管理 MySQL 数据，Docker Compose 负责本地编排。

生产落地时应接入企业 SSO、竞品价格、消息通知、审计日志和自有模型网关，并对模型输出保留人工确认、权限控制和可追溯证据。
