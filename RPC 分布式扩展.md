# RPC 分布式扩展

### Dubbo 集群容错

- Failover Cluster（失败自动切换）

  当出现失败，重试其它的机器。通常用于读操作，但是由于重试太多可能会带来更大的延迟，可以通过 retries="2" 来设置重试次数（不包含第一次）

  ```xml
  <dubbo:reference retires="2" />
  ```

- Failfast Cluster（快速失败）

  快速失败，只发起一次调用，失败立即保存，通常用于非幂等性的操作

  ```xml
  <dubb::service cluster="failfast" />
  ```

- Failsafe Cluster（失败安全）

  出现异常时，直接忽略。通常用于日志写入等操作

  ```xml
  <dubbo:service cluster="failsafe" />
  ```

- Failback Cluster（失败自动恢复）

  后台记录失败请求，定时重发。通常用于消息通知等操作

  ```xml
  <dubbo:service cluster="failback" />
  ```

- Forking Cluster

  并行调用多个服务器，只要有一个成功立即返回。通常用于实时性比较高的读操作，但是会浪费更多的服务器资源。可以通过 forks="2" 来设置最大并行数

  ```xml
  <dubbo:service cluster="forking" forks="2" />
  ```

- Braodcast Cluster

  广播调用所有提供者，逐个调用，任意一台报错则报错。通常用于通知所有提供者更新缓存或日志等本地资源信息。

  ```xml
  <dubbo:service clusetr="brodcast" forks="2" />
  ```



### Dubbo 负载均衡

- Random LoadBalance
  - 随机，按照权重设置随机概率。
  - 调用量越大越均匀
- RoundRabin LoadBalance
  - 轮询，按照公约的权重
- LeasteActive LoadBalance
  - 活跃数：前后调用的计数差
  - 最少活跃调用数，相同活跃数的随机
  - 因此性能越好的机器接收的请求越多
- ConsistenceHash LoadBalance（一致性 Hash）
  - 通过相关的 Hash 算法，将不同的请求发送到不同的机器上。
  
  - Hash 后将会将请求按照顺时针的方向进行派发
  
  - 由于 Hash 算法的的 hash 结果会导致这几个节点成簇，因此这时请求会派发到边缘的节点，导致服务过载，最终不可用。可用的解决方案是设置几个虚拟节点，来分散这些请求，如下图所示：
  
    <img src="https://i.loli.net/2021/08/23/wJDbyN9XR2fecuK.png" alt="image.png" style="zoom:80%;" />



### Dubbo实践

- 分包

  > 将服务接口、服务模型、服务等异常均放在 API 包中，符合分包原则：重用分布等价原则（REP）、共同重用原则（CRP）

- 粒度

  > 接口服务尽可能大粒度，每个服务方法代表一个功能，而不是某个功能的步骤，否则将面临分布式事务问题，Dubbo 暂未提供分布式事务支持
  >
  > 服务接口建议以业务场景为单位划分，并对相近业务做抽象，防止接口数量爆炸
  >
  > 不建议使用过于抽象的通用接口，如：`Map query()` ，这样的接口没有明确的语义，会给后期的维护带来不便

- 版本

  > 每个接口都应该定义版本号，为后续不兼容升级提供可能。如：<dubbo:service interface="com..", version="1.0">
  >
  > 建议使用两位版本号，因为第三位版本号通常表示兼容升级，只有不兼容时才需要变更服务版本
  >
  > 当不兼容时，先升级一半提供者为新版本，再将消费者全部升级为新版本，然后将剩下的一半提供者升级为新版本

- 兼容性

  > 服务接口增加新方法，或者服务模型增加字段，可向后兼容；删除方法或字段，将不兼容；枚举类型新增字段也不兼容，需要通过变更版本号升级。

- 枚举性

  > 如果是完备集，可以使用 Enum
  >
  > 如果是业务种类，以后明显会有类型增加，不建议使用 `Enum`，可以使用 `String` 来代替
  >
  > 如果是在返回值中使用了 `Enum`，并且新增了 `Enum` 值，建议先升级服务消费方，这样服务提供放不会返回新值
  >
  > 如果是在传入参数中使用了 `Enum`，并且新增了 `Enum` 值，建议先升级服务提供方，这样消费放不会传入新值

- 序列化

  > 服务参数及返回值建议使用 POJO 对象，通过 `setter` 和 `getter` 方法表示属性的对象

- 调用

  > `try...catch`应该加在合适的回滚边界上
  >
  > Provider 段需要对输入参数进行校验，如果有性能上的考虑，服务实现者可以考虑在 API 包上加上服务 Stub 类来完成校验



### Dubbo 推荐用法

- 在 Provider 端尽量多配置 Consumer 端属性

  > 作为服务的提供方，比服务消费端更加清楚服务的性能修改，如调用的超时时间、合理的重试次数等
  >
  > 在 Provider 端配置后，Consumer 端如果不配置会使用 Provider 端的配置，即 Provider 端的配置可以作为 Consumer 的缺省值 1；否则，Consumer 端将会使用 Consumer 端的全局配置，这对于 Provider 来讲是不可控的，并且往往是不合理的

  建议在 Provider 端配置的 Consumer 端属性：

  1. timout：方法调用的超时时间
  2. retries：失败重试次数，缺省是 2
  3. loadBalance：负载均衡算法，缺省是 Random，还可以配置轮询 roundrobin、最不活跃优先、一致性 Hash 等
  4. Actives：服务端的最大并发调用限制，即当 Consumer 对一个服务的并发调用到上限之后，新调用会阻塞直到超时，在方法上配置 dubbo:method 则针对指定的方法进行并发限制，在接口上配置 dubbo:service，则针对该服务进行并发限制

- 在 Provider 端配置合理的 Provider 属性

  建议在 Provider 端配置的属性

  1. threads：服务线程池大小，
  2. executes：一个服务提供者并行执行请求上限，即当 Provider 对一个服务的并发调用达到上限之后，新调用会阻塞，此时 Consumer 可能会超时。在方法上配置 dubbo:method 针对该方法进行比并发限制，在接口上配置 dubbo:service，则可以针对该服务进行并发限制

- 配置管理信息

  ​	目前有负责人信息和组织信息用于区分站点。以便于在发现问题时找到服务对应的负责人，建议至少配置两个人以便复制。负责人和组织信息可以在运维平台（Dubbo Ops）上看到。

  - 在应用层面配置负责人、组织信息

    ```xml
    <dubbo:application owner="*****" organization="*****" />
    ```

  - 在服务层面（服务端）配置负责人

    ```xml
    <dubbo:service owner="*****" />
    ```

  - 在服务层面（消费端配置负责人）

    ```xml
    <dubbo:reference owner="******" />
    ```

- 配置 Dubbo 缓存文件

  提供者列表缓存文件：

  ```xml
  <dubbo:registry file="${user.home}/output/dubbo.cache" />
  ```

  - 可以根据需要调整缓存文件路径，保证这个文件不会在发布过程中被删除
  - 如果有多个应用程序进程，请注意不要使用同一个文件，以免内容被覆盖
  - 该文件会缓存注册中心列表和服务提供者列表。配置缓存文件后，应用重新启动的过程中，若注册中心不可用，应用会从缓存文件读取服务提供者列表，进一步保证应用可靠性。

- 监控配置

  1. 使用固定端口暴露服务，不要使用随机端口。这样在注册中心推送有延迟的情况下，消费者通过缓存列表也能调用到原地址，保证调用成功。
  2. 使用 Dubbo Admin 监控注册中心上的服务提供方；使用 Dubbo Admin 监控服务在注册中心上的状态，确保注册中心上有该服务的存在。
  3. 服务提供方可以使用 Dubbo Qos 的 telnet 或 shell 监控项监控服务提供者状态：`echo status | nc -i 20880 | grep OK | wc -l`，其中 20880 为服务端口
  4. 服务消费方可以通过将服务强制转变为 EchoService，并调用 $echo() 测试该服务的提供者是否可用



### 分布式事务

- CAP 理论

  对于一个分布式计算系统来说，不能同时满足以下三点：

  - 一致性（**C**onsistency） （等同于所有节点访问同一份最新的数据副本）
  - 可用性（**A**vailability）（每次请求都能获取到非错的响应——但是不保证获取的数据为最新数据）
  - 分区容错性 （**P**artition tolerance）（以实际效果而言，分区相当于对通信的时限要求。系统如果不能在时限内达成数据一致性，就意味着发生了分区的情况，必须就当前操作在C和A之间做出选择

  <img src="https://www.wangbase.com/blogimg/asset/201807/bg2018071607.jpg">

- BASE 理论

  > BASE理论是指，Basically Available（基本可用），Soft-state（软状态），Eventual Consistency（最终一致性）。是基于CAP定理演化而来，是对CAP中一致性和可用性权衡的结果。
  >
  > 核心思想：即使无法做到强一致性，但每个业务根据自身的特点，采用适当的方式来使系统达到最终一致性。

- Zookeeper 中的 CAP 原则：CP

- 分布式事务常见方案

  - 2CP（XA）两阶段提交

  - TCC（事务补偿）

  - 本地消息事件 + 消息队列

    ![image.png](https://i.loli.net/2021/08/23/H3plTOXtUcGEsJ4.png)



### 分布式存储