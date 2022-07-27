1. 超时控制
2.

超时配置

```yaml
ribbon: #设置feign客户端连接所用的超时时间，适用于网络状况正常情况下，两端连接所用时间
  ReadTimeout: 1000 #指的是建立连接所用时间
  ConnectTimeout: 1000 #指建立连接后从服务读取到可用资源所用时间
```

日志配置

```java
class Config {
    /**
     * NONE：默认的，不显示任何日志
     * BASIC：仅记录请求方法、URL、响应状态码及执行时间
     * HEADERS：出了BASIC中定义的信息之外，还有请求和响应的头信息
     * FULL：除了HEADERS中定义的信息之外，还有请求和响应的正文及元素
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}

```

熔断机制
hystrix

负载均衡

ribbon

```yaml
#服务名称
provider:
  ribbon:
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
#NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule #配置规则 随机
#NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule #配置规则 轮询
#NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RetryRule #配置规则 重试
#NFLoadBalancerRuleClassName: com.netflix.loadbalancer.WeightedResponseTimeRule #配置规则 响应时间权重
#NFLoadBalancerRuleClassName: com.netflix.loadbalancer.BestAvailableRule #配置规则 最空闲连接策略
```
