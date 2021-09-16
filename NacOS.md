# NacOS

## 配置中心

- 出现原因

  单体应用的局限：一个系统过大时会导致功能的耦合，无法针对相应的业务进行开发；不同的功能业务对于硬件的需求程度不同，在单体应用上很难进行提升；难以使得不同的业务采用不同的底层架构

  

  > 随着系统的开发和迭代，系统的功能越来越多，此时单一的应用程序已经无法满足要求，微服务架构因此应运而生
  >
  > 微服务架构提高了系统中各个模块的独立性，同时整体上提高了系统的性能和可伸缩性，但是随之而来的问题就是各个微服务系统之间如何进行统一的管理和配置，配置中心便是为了解决这一问题而出现。
  >
  > 常见的配置中心有 `NacOS`、`Euraka`、`zookeeper` 等

  

  服务的变化：单体应用 ——> 水平划分、垂直划分（服务重用、信息孤岛等问题） ——> `SOA`（服务的拆分） ——> 微服务（优点：服务小、可扩展性强。缺点：运维困难、数据一致性难以保障、出现问题难以排查）

  

  `SOA` 与微服务：

  > `SOA` 致力于解决服务重用、信息孤岛的问题
  >
  > 微服务为了解决业务解耦问题

- 主流的配置中心

  - `Spring Cloud Config`：Spring Cloud 生态组件，与 Spring Cloud 整合较好；由 Git 作为版本管理的工具；只支持 `Java` ；单击读写、3节点读写都比较慢
  - `Apollo`：携程开源的配置管理中心，支持语言较多；文档较为详细
  - `NacOS`：阿里开源的配置管理中心；性能较好；文档较少

- 领域模型

  <img src="https://cdn.nlark.com/yuque/0/2019/jpeg/338441/1561217857314-95ab332c-acfb-40b2-957a-aae26c2b5d71.jpeg" style="zoom:80%">

- 配置优先级

  `Spring` 自定义 > `ext-config` > `share-details`



## Spring Cloud

### 种类

- `Spring Cloud Netflix`
  - `Eureka`：服务注册测与发现
  - `Zuul`：服务网关
  - `Ribbon`：负载均衡
  - `Feign`：远程服务的客户代理
  - `Hystrix`：断路器，提供服务熔断和限流的功能
  - `Tuibine`：将各个服务实例上的 `Hystrix` 监控信息进行统一聚合
- `Spring Cloud Alibaba`
  - `NacOS`：分布式配置中心
  - `NacOS`：服务注册与发现
  - `Sentinel`：流量控制与服务降级
  - `RocketMQ`：消息驱动
  - `Seate`：分布式事务
  - `Dubbo`：RPC 通信
- 其它



### 服务治理

- 传统系统部署中，服务运行在一个固定的已知的 `IP` 和固定端口上，不会发生变化。而在现代容器化和虚拟化的环境下，服务的启动和销毁是很频繁的，因此服务地址和端口也在不断发生变化。此时，需要服务发现机制

- 服务发现的种类

  - 基于客户端的服务发现

    > 服务注册到注册中心，由客户端提供负载均衡算法
    >
    > <img src="https://i.loli.net/2021/09/07/WwfabeX2BJAINi9.png" alt="client.png" style="zoom:50%;" />
    >
    > 优点：客户端知道所有可用服务的实际网络地址，所以可以非常方便地实现负载均衡
    >
    > 缺点：耦合性很强。针对不同的编程语言，每个服务的客户端都必须实现一套服务发现的功能

  - 基于服务端的服务发现

    > 由服务端提供负载均衡
    >
    > <img src="https://i.loli.net/2021/09/07/lVIyzZkbuRC5e2X.png" alt="server.png" style="zoom:50%;" />
    >
    > 优点：服务的发现逻辑对客户端是不透明的。客户端只需要向 `LOAD BALANCE` 发送请求即可
    >
    > 缺点：必须要关心该负载均衡组件的可用性

- 服务发现技术对比

  - NacOS
    - 一致性协议：`CP` + `AP`
    - 健康检查基于 TCP、HTTP、MYSQL、Client Beat
    - 支持容器化
    - 项目维护性好
  - Eureka
    - 一致性协议：`AP`
    - 不再被维护。。。。



### NacOS 架构

<img src="https://cdn.nlark.com/yuque/0/2019/jpeg/338441/1561217892717-1418fb9b-7faa-4324-87b9-f1740329f564.jpeg" style="zoom:80%" />

- `Provider APP`：服务的提供者

- `Consumer APP`：服务的消费者

- `Name`：通过 `VIP` 或者 `DNS` 实现 `NacOS` 的高可用路由

- `NacOS Server`

  > `Open API`：功能的访问入口
  >
  > `Config Service`: 配置服务模块
  >
  > `Naming Service`：名字服务模块
  >
  >  `Consitency Protocol`：一致性协议。集群时保证数据同步



### 源码解析

- 服务注册

  > `ServiceRegistry`：定义 `Spring Cloud` 的规范。`spring-cloud-common`
  >
  > `NacosServiceRegistry`：`Spring Cloud Alibaba`

- `spring-cloud-commons`的 `spring.factories`

  > `org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration`
