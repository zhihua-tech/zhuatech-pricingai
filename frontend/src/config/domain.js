/* Copyright 2026 上海如静知华信息科技有限公司 */
export const domain={
 code:'PRICINGAI',systemName:'知华智能定价 AI 平台',englishName:'AI PRICING OPTIMIZATION',theme:{primary:'#3d6370',dark:'#203941',accent:'#d2873f'},
 workspace:'商业运营中心 / 定价策略组',fieldWorkspace:'零售事业部 / 华东定价组',period:'2026-08-15 · 日内更新',liveText:'成本、库存与竞品数据于 10:32 更新',fieldContextLabel:'生效周期',fieldContext:'08-15 12:00—24:00',fieldUser:'唐悦',fieldRole:'定价分析师',adminUser:'孟川',adminRole:'商业运营负责人',
 adminTitle:'价格策略运营中心',adminBreadcrumb:'智能定价 / 全渠道态势',adminSubtitle:'综合成本、竞争、库存和价格弹性，生成带毛利护栏且需要人工批准的价格建议。',exportAction:'导出价格策略',createAction:'新建定价任务',
 chartTitle:'价格建议执行趋势',chartSubtitle:'已审核比例 / 发布目标',chartLabels:['01日','05日','09日','13日','17日','21日','25日','29日','31日'],loadTitle:'品类调价负荷',loadSubtitle:'待审核建议占团队处理能力',recordsTitle:'重点定价任务',recordsSubtitle:'按库存压力、竞争变化和利润影响排序',issueTitle:'需要关注的价格事件',issueSubtitle:'涉及毛利、价盘或客户体验风险',
 recordName:'定价任务',itemName:'商品组合',unitName:'定价团队',batchName:'策略版本',planName:'SKU',doneName:'已建议',exceptionName:'越界',unitLabel:'个',
 listBreadcrumb:'价格运营 / 定价任务',listSubtitle:'管理建议价、毛利护栏、竞争信号、审批和渠道生效结果。',listSummary:[['定价对象','3,680'],['今日建议','286'],['待审核','34',true],['护栏拦截','12']],tabs:['全部','待处理','进行中','待确认','已归档'],
 fieldBreadcrumb:'定价工作台 / 分析师',fieldTitle:'价格建议工作台',fieldSubtitle:'待审核 11 项 · 毛利预警 3 项 · 今日生效 68 项',fieldSecondary:'查看价格日历',reportAction:'提交定价意见',fieldNoticeTitle:'价格数据准备完成',fieldNotice:'竞品覆盖率 91.8%',
 steps:['信号采集','价格建议','护栏校验','人工审批','渠道生效'],documentAction:'查看定价规则',printAction:'导出审批单',resourceCardTitle:'定价资源状态',resourceValueLabel:'今日计算量',resourceHealthLabel:'服务健康度',quickSubtitle:'常用定价入口',
 quickActions:[['价格审核','/shopfloor/report','建议价、毛利和调整依据'],['商品组合','/shopfloor/material','成本、渠道和价格层级'],['策略中心','/shopfloor/resources','模型、护栏和竞品数据'],['价格升级','/shopfloor/andon','倒挂、乱价和重大活动']],
 reportDefaults:[6,1],reportTitle:'定价审核反馈',reportSubtitle:'记录采纳价、驳回原因和生效范围。',reportSuccess:'定价审核反馈已提交',reportPlaceholder:'填写价格判断、竞争依据、毛利影响和生效建议',reportFootnote:'所有建议价必须经过授权人员审批后才能生效',ruleTitle:'价格发布安全护栏',ruleSubtitle:'PRICING-AI · V1.0',rules:[['最低毛利','禁止突破'],['大幅调价','二级审批'],['竞品数据','校验时效'],['效果复盘','每周',true]],fieldTotals:[['11','待审核建议'],['3','毛利预警'],['68','今日生效'],['91.8%','竞品覆盖率']],
 adminMenus:[['/admin','home','价格策略中心'],['/admin/work-orders','order','定价任务'],['/admin/samples','box','商品组合'],['/admin/schedule','calendar','价格日历'],['/admin/methods','process','策略与护栏'],['/admin/reviews','quality','价格审批'],['/admin/resources','machine','定价资源'],['/admin/report','chart','价格分析']],
 fieldMenus:[['/shopfloor','home','定价工作台'],['/shopfloor/report','report','价格审核'],['/shopfloor/tasks','order','我的任务'],['/shopfloor/material','box','商品组合'],['/shopfloor/resources','machine','策略状态'],['/shopfloor/andon','risk','价格升级',3]],
 moduleTitles:{tasks:['我的定价任务','查看优先级、利润影响和生效时间'],material:['商品组合台账','查看成本、价盘和渠道范围'],resources:['定价资源中心','管理模型、护栏和数据连接器'],andon:['重大价格升级','提交倒挂、乱价和活动冲突'],samples:['商品组合台账','维护产品层级、渠道和责任人'],schedule:['价格生效日历','协调活动、渠道和版本窗口'],methods:['策略与护栏','维护毛利、调价幅度和价格规则'],reviews:['价格审批','记录采纳、驳回和修改意见'],report:['价格表现分析','分析销量、毛利、库存和竞争位置']},
 tagline:'让价格建议既响应市场，也守住利润底线',storyTitle:'不是自动改价，<br/>而是把定价依据摆在审批人面前',storyText:'连接成本、库存、竞品与弹性信号，生成透明、受护栏约束、需要人工批准的价格建议。',pattern:[2,4,7,10,13,16,19,22,25,28,31],loginStats:[['3,680','定价对象'],['286','今日建议'],['12','护栏拦截']],loginTitle:'价格策略运营中心',adminDemo:'价格 / 利润 / 竞争',fieldDemo:'建议 / 审核 / 生效'
}
export const records=[
 {no:'PR-260815-018',name:'夏季便携风扇组合',code:'FAN-SUMMER',unit:'小家电定价组',group:'消费事业部',plan:186,done:142,exception:9,due:'11:00',batch:'PRICE-v3.1',status:'待确认',progress:76,priority:'加急'},
 {no:'PR-260815-021',name:'商用净水滤芯',code:'FILTER-B2B',unit:'商用定价组',group:'大客户事业部',plan:96,done:64,exception:3,due:'12:00',batch:'B2B-策略',status:'进行中',progress:67,priority:'关注'},
 {no:'PR-260815-026',name:'办公耗材清仓组合',code:'OFFICE-CLEAR',unit:'零售定价组',group:'渠道中心',plan:240,done:82,exception:12,due:'14:30',batch:'库存策略',status:'待处理',progress:34,priority:'加急'},
 {no:'PR-260814-015',name:'常规清洁用品',code:'CLEAN-BASE',unit:'日用定价组',group:'消费事业部',plan:168,done:168,exception:2,due:'08-14',batch:'稳定价盘',status:'已归档',progress:100,priority:'正常'},
 {no:'PR-260815-031',name:'华东渠道新品',code:'EAST-NEW',unit:'区域定价组',group:'渠道中心',plan:72,done:44,exception:5,due:'16:00',batch:'新品策略',status:'进行中',progress:61,priority:'关注'}
]
export const resources=[{code:'COST-01',name:'成本与返利数据连接器',unit:'财务数据组',status:'运行中',health:99,value:'3,680',valueUnit:'SKU',note:'标准成本于 09:50 完成同步'},{code:'ENGINE-02',name:'价格优化与护栏引擎',unit:'商业算法组',status:'运行中',health:96,value:'286',valueUnit:'建议',note:'12 条建议被最低毛利拦截'},{code:'COMP-03',name:'竞品价格采集服务',unit:'市场情报组',status:'预警',health:88,value:'91.8',valueUnit:'%',note:'一个平台采集延迟 28 分钟'}]
export const reviews=[{no:'RV-260815-032',title:'便携风扇建议降价 6.8%',type:'活动价格',detail:'预计周转改善 14 天 · 孟川',result:'待确认'},{no:'RV-260815-011',title:'商用滤芯合同价保护',type:'客户价盘',detail:'毛利 31.2% · 唐悦',result:'通过'},{no:'RV-260814-018',title:'办公耗材建议价低于护栏',type:'毛利拦截',detail:'差额 2.4 元',result:'异常'}]
export const adminMetrics=[['定价对象','3,680','覆盖 8 个渠道','blue'],['今日建议','286','预计毛利 +1.2%','green'],['待审核建议','34','11 项临近生效','orange'],['护栏拦截','12','避免利润损失 8.6 万','red']]
export const fieldMetrics=[['我的任务','11','3 项高优先级','blue'],['今日已审核','42','采纳率 76.2%','green'],['毛利预警','3','全部禁止自动生效','orange'],['竞品覆盖率','91.8%','较昨日下降 1.1%','slate']]
export const chartActual=[54,61,67,72,76,81,85,89,92],chartTarget=[58,64,70,75,80,84,88,92,95]
export const loads=[['小家电',86,'待审核 14 项'],['办公耗材',79,'待审核 11 项'],['商用产品',62,'待审核 5 项'],['日用消费',48,'待审核 4 项']]
export const issues=[['毛利','办公耗材清仓建议低于最低毛利','12 个 SKU 已被护栏拦截','待决策'],['竞争','核心竞品平台价格采集延迟','暂沿用最近可信价格','处理中'],['价盘','华东经销新品与直营活动冲突','需要渠道负责人统一生效范围','待协调']].map(x=>({type:x[0],title:x[1],detail:x[2],status:x[3]}))
