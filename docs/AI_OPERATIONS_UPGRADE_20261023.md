# 折扣佣金后的净贡献核算

知华科技（上海如静知华信息科技有限公司） · https://www.zhuatech.cn/

## 已实现功能

核算折扣、佣金、单件运费和成本后的净贡献，反推目标贡献率所需最低标价；不自动修改价格。

输入金额最多两位小数。先按未舍入净收入计算佣金与贡献，展示金额再四舍五入；目标贡献率定义为(净收入−佣金−成本−运费)/净收入，不是成本加价率。最低标价向上保留两位；折扣最大99%，佣金率+目标贡献率必须小于1。净收入0时比率不可定义。

这是真正执行计算的本地算法，不是固定内容的演示响应，不调用外部大模型。仅处理调用者本次已获授权的数据，不查询或修改业务库，不自动审批、转账、改价、冻结、关单、发布内容或操作设备。

## 使用与接口

登录后从管理端或业务端右下角「业务计算工具」进入。可编辑JSON、执行校验、查看前100行结果、导出完整JSON；支持加载、空结果、格式错误、超时、无效认证与后端异常提示。退出登录后卸载页面内存中的输入及结果；不额外存储账号或密码。合成示例不是生产数据。

`POST /api/workbench/net-margin-waterfall`，`Content-Type: application/json`，使用现有Bearer JWT。

- 有效认证200；无有效身份401；参数错误400。现有安全过滤器不变，控制器另有身份检查。
- 所有已认证账号可对自己提交的数据计算；不读取记录，也不授予额外业务记录权限。
- 未声明字段、重复业务编号、非法类型、越界数值/日期均明确拒绝。通用限制：深度12、节点10000、单字符串64000字符、累计128000字符、单数组最多500项；业务还有更小限制。
- 生产网关应设置128 KiB请求体和速率限制；业务校验在JSON解析之后，不替代传输层防护。前端输入另限制128 KiB。
- 日期通常为1900–2200年ISO-8601日期；时间字段按本功能口径，涉及组织本地时间时不做时区推断。

### 参数与示例

| 顶层字段 | 类型 |
| --- | --- |
| `unitCost` | number |
| `listPrice` | number |
| `discountRate` | number |
| `commissionRate` | number |
| `unitFreight` | number |
| `targetMargin` | number |

```json
{
  "unitCost": 100,
  "listPrice": 150,
  "discountRate": 0.1,
  "commissionRate": 0.05,
  "unitFreight": 5,
  "targetMargin": 0.2
}
```

```sh
# 将示例保存为request.json。TOKEN来自你自己在现有系统中的登录。
curl --fail-with-body -X POST "$API_BASE/api/workbench/net-margin-waterfall" \\
  -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' \\
  --data-binary @request.json
```

成功响应为 `{success:true,message,data}`，失败为明确错误，不生成伪造结果。输出字段及精度可在 `workbench/AiWorkbenchService.java` 查看。

## 既有接口兼容与修复

同时修复原推荐价与价格模拟接口：最低毛利价改为成本÷(1−毛利率)，向上取分，并拒绝100%毛利率。

\`minimumMarginRate\` 始终解释为(售价−成本)/售价，不再混同成本加价率。比如成本100、目标25%，最低售价为133.34，不是125。原测试中的错误期望随业务口径纠正；新增4项回归覆盖真实毛利、模拟一致性、100%边界与分币向上舍入。

Existing recommendation/simulation APIs now use cost divided by one minus the gross margin, rounded up to cents, and reject a 100 percent margin.

## 测试与限制

`AiWorkbenchServiceTest` 有14项测试：业务结果、领域边界、类型/范围校验、确定性/不修改输入、空请求、未知字段、文本/嵌套限制、独立控制器认证及错误处理。

`AiWorkbenchSecurityIntegrationTest` 另有4项测试：通过Spring Boot真实安全过滤器验证匿名拒绝、无效令牌拒绝、有效JWT计算及参数错误。测试JWT由测试上下文签发，H2集成环境不向运行配置添加账号或密钥。

本次继续运行原有测试。执行 `cd backend && mvn test` 及 `cd frontend && npm run build`。这些结果不等于已完成全部旧页面端到端测试、真实MySQL生产流程、容量、渗透或外部模型验收。既有许可不变。

## English

**Net contribution price waterfall.** Calculates net contribution after discount, commission, cost and freight, and derives the minimum list price needed for a target contribution margin.

The endpoint above performs authenticated deterministic computation on caller-supplied, authorized data. Use the JSON sample, existing Bearer token and business UI. Errors and undefined metrics are explicit; results can be exported. No model invocation, record lookup/update, autonomous business decision, payment or device control is performed. Production gateway limits, data authorization and human review remain necessary. Tests cover algorithms, controllers and the real security chain with H2; they are not full production acceptance.

Commercial licensing or customization: [han@zhuatech.cn](mailto:han@zhuatech.cn), [jack@zhuatech.cn](mailto:jack@zhuatech.cn), [WhatsApp +86 17521234993](https://wa.me/8617521234993).

商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。中文README原有两个微信二维码保持不变。
