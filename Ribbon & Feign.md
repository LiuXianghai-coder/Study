# `Ribbon`

> 基于 `Http` 和  `TCP` 的客户端负载均衡工具，基于 `Netflix Ribbon` 实现
>
> `Ribbon`  只具备负载均衡能力，并不具备发送请求的能力

<img src="https://i.loli.net/2021/09/21/p9615Kk2WEjfzLn.png" alt="1.png" style="zoom:50%;" />

- 实现负载均衡的三种方式
  - `DiscoveryClient` + 自实现负载均衡 + `RestTemplate`
  - `LoadBalancer` + `RestTemplate`
  - `@LoadBalanced` + `RestTemplate`
- `Ribbon` 负载均衡策略
  - `RoundRobinRule` 轮询策略，按顺序循环选择服务实例
  - `RandomRule` 随机策略，随机选择服务策略
  - `AvaliablityFilteringRule` 使用过滤策略，先过滤由于多次访问故障而处于断路器跳闸的服务，还有并发连接数量超过阈值的服务。然后对剩余的服务按照轮询策略进行访问
  - `WeightedResponseTimeRule`  响应时间加权策略，对不同的配置或者负载的服务，请求偏向于打到负载小或者性能高的服务器上。根据平均响应时间计算所有服务的权重，响应时间越快服务权重越大。刚开始时由于统计信息不足，会首先采用 `RoundRobinRule` ，等到统计信息足够了，再切换到本策略。
  - `RetryRule`重试策略，会使得客户对于服务列表中不可用服务的调用无感。首先按照 `RoundRobin`策略获取服务，如果获取失败，则会在指定的时间内重试，获取可用服务
  - `BestAvaliableRule`最低并发策略

# `OpenFeign`

> 一个声明式的伪 `Http` 客户端（即：封装了 `Http` 请求，底层使用 `RestTemplate` 发送 `Http` 请求）。
>
> 默认继承 `Ribbon`，因此实现了负载均衡。
>
> `Spring Cloud` 团队添加了 `Spring MVC` 的支持

