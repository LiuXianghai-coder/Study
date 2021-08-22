# Dubbo

### 架构的演变

- 单一应用架构

	> 将项目所有的功能模块都放在一个工程中，所有的服务组合为一个整体

	- 优点
		1. 项目前期开发节奏快，能够快速迭代
		2. 架构简单
		3. 易于测试
		4. 易于部署
	- 缺点
		1. 随着业务的增加，项目变得臃肿，业务耦合严重
		2. 新增业务困难
		3. 核心业务与边缘业务混合在一起，相互影响

- 垂直应用架构

	> 对业务进行垂直划分，把一个单体架构拆分为一堆的单体应用

	- 优点
		1. 可以针对相关的模块进行单独的优化
		2. 方便水平扩展，系统容错性高
		3. 各个应用之间相互独立，有利于业务迭代
	- 缺点
		1. 系统部署较为复杂
		2. 服务之间的调用不统一
		3. 服务监控不到位

- SOA 架构

	> (Server-Oriented Architecture) 面向服务的架构。根据实际业务，把系统拆分成合适的，独立部署的模块，模块之间相互独立。



### RPC

- RPC 核心和调用的大体流程

  > // Client端
  >
  > 1. 把这个调用映射为 Call ID
  > 2. 把 Call ID 和参数序列化，以二进制的形式打包
  > 3. 将打包的二进制数据包发送给服务端
  > 4. 等待服务器返回结果
  > 5. 如果服务器调用成功，则将结果反序列化
  >
  > 
  >
  > // Server 端
  >
  > 1. 在本地维护一个 Call ID 的函数指针映射 call_id_map，可以使用 Map<String, Method> callIdMap
  > 2. 等待客户端请求
  > 3. 得到一个请求后，将其数据包反序列化，得到 Call ID
  > 4. 通过在 callIdMap 中查找，得到相应的函数指针
  > 5. 将参数反序列化后，调用本地函数，得到结果
  > 6. 将结果序列化后通过网络返回给 Client

  <img src="https://dubbo.apache.org/imgs/dev/send-request-process.jpg">

  - 一次服务调用过程

  	> 1. 服务消费者（Client ）通过本地调用的方式调用服务
  	> 2. 客户端存根（Client Stub）接收到请求调用后负责将方法、入参等信息序列化，组成网络能够进行网络传输的消息体
  	> 3. 客户端存根（Client Stub）找到远程服务的地址，将序列化的消息体发送给服务端
  	> 4. 服务端存根（Server Stub）收到消息后进行解码（反序列化操作）
  	> 5. 服务端存根（Server Stub）根据解码的结果调用本地的服务进行相关的处理
  	> 6. 服务端（Server）对本地服务业务进行处理
  	> 7. 处理结果返回给服务器存根（Server Stub）
  	> 8. 服务端存根（Server Stub）对结果进行序列化
  	> 9. 服务端存根（Server Stub）将结果发送给客户端（Client）
  	> 10. 客户端存根（Client Stub）接收到消息，并进行反序列化

- 架构

	![image.png](https://i.loli.net/2021/08/20/fQ9mSMXFExHZpJI.png)

	-  服务发现：缓存注册中心中的服务地址，避免由于注册中心故障带来的问题
	- 注册中心通过 Watch 自动将服务端的信息 “送到” 客户端

- RPC 核心功能的实现

	- 服务寻址

		> 1. 服务寻址使用 Call ID 映射。在本地调用中，函数体是直接通过函数指针来指定的
		> 2. 在 RPC 中，所有的函数都必须有一个自己的 ID， 这个 ID 在所有进程中都是唯一确定的
		> 3. 客户端再做远程调用时，必须附带 Call ID。同时需要在客户端和服务端分别维护一个函数和 Call ID 的对应表，客户端在进行远程调用时，查找本地的 Call ID 表，得到相应的调用函数。

		实现方式：服务注册中心

	- 数据流的序列化和反序列化

		> 将对象序列化为二进制流的过程称为序列化；将二进制流转换为对象的过程称为反序列化

		常见的序列化方式：

		1. Hessian: 一种二进制 Web 服务协议，与框架无关。
		2. Protobuf: Google 提供的一种序列化方式；效果更佳
		3. Thrift: Facebook 提供的一种 RFC 框架，同时也有对应的序列化方式

	- 网络传输

		- 直接调用现有的连接库（Netty 等）即可



### Dubbo 特点

- 面向接口代理的高性能 RPC 调用
	- 服务以接口为粒度，屏蔽远程调用的底层细节
- 智能负载均衡
- 服务自动注册与发现
- 高度可扩展能力
- 运行期间的流量调度
- 可视化的服务治理与运维



### Dubbo 架构

<img src="https://dubbo.apache.org/imgs/user/dubbo-architecture.jpg">

- 节点角色说明

	| 节点        | 角色说明                               |
	| ----------- | -------------------------------------- |
	| `Provider`  | 暴露服务的服务提供方                   |
	| `Consumer`  | 调用远程服务的服务消费方               |
	| `Registry`  | 服务注册与发现的注册中心               |
	| `Monitor`   | 统计服务的调用次数和调用时间的监控中心 |
	| `Container` | 服务运行容器                           |

- 调用关系说明

1. 服务容器负责启动，加载，运行服务提供者。
2. 服务提供者在启动时，向注册中心注册自己提供的服务。
3. 服务消费者在启动时，向注册中心订阅自己所需的服务。
4. 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
5. 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
6. 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。



### Dubbo 详细架构

<img src="https://dubbo.apache.org/imgs/dev/dubbo-framework.jpg">

各层说明

- **config 配置层**：对外配置接口，以 `ServiceConfig`, `ReferenceConfig` 为中心，可以直接初始化配置类，也可以通过 spring 解析配置生成配置类
- **proxy 服务代理层**：服务接口透明代理，生成服务的客户端 Stub 和服务器端 Skeleton, 以 `ServiceProxy` 为中心，扩展接口为 `ProxyFactory`
- **registry 注册中心层**：封装服务地址的注册与发现，以服务 URL 为中心，扩展接口为 `RegistryFactory`, `Registry`, `RegistryService`
- **cluster 路由层**：封装多个提供者的路由及负载均衡，并桥接注册中心，以 `Invoker` 为中心，扩展接口为 `Cluster`, `Directory`, `Router`, `LoadBalance`
- **monitor 监控层**：RPC 调用次数和调用时间监控，以 `Statistics` 为中心，扩展接口为 `MonitorFactory`, `Monitor`, `MonitorService`
- **protocol 远程调用层**：封装 RPC 调用，以 `Invocation`, `Result` 为中心，扩展接口为 `Protocol`, `Invoker`, `Exporter`
- **exchange 信息交换层**：封装请求响应模式，同步转异步，以 `Request`, `Response` 为中心，扩展接口为 `Exchanger`, `ExchangeChannel`, `ExchangeClient`, `ExchangeServer`
- **transport 网络传输层**：抽象 mina 和 netty 为统一接口，以 `Message` 为中心，扩展接口为 `Channel`, `Transporter`, `Client`, `Server`, `Codec`
- **serialize 数据序列化层**：可复用的一些工具，扩展接口为 `Serialization`, `ObjectInput`, `ObjectOutput`, `ThreadPool`



大体流程：

![image.png](https://i.loli.net/2021/08/21/n5pVvEM691GcSPe.png)

- 架构特点

	> 连通性、健壮性、伸缩性、以及面向未来架构的升级性

	- 连通性
		- 注册中心负责服务地址的注册与查找，相当于目录服务，服务提供者和消费者只在启动时与注册中心交互，注册中心不处理请求，压力较小
		- 监控中心统计各服务调用次数，调用时间等，统计现在内存汇总后每分钟一次发送到监控中心服务器，并以报表展示
		- 服务提供者向注册中心注册其提供的服务，并汇报调用时间到监控中心，此时间不包含网络开销
		- 服务消费者向注册中心服务提供者提供的地址列表，并根据负载算法直接调用提供者，同时汇报调用时间到监控时间，此时间包含网络开销
		- 注册中心，服务提供者，服务消费者三者之间的连接均为长连接，监控中心除外
		- 注册中心通过长连接感知服务提供者的存在，如果服务提供者宕机，注册中心将立即推送事件通知消费者
		- 注册中心和监控中心全部宕机，不影响已经运行的服务提供者和服务消费者，消费者在本地缓存了服务提供者的目录列表
		- 注册中心和服务中心都是可选的，服务消费者可以直接连接服务提供者
	- 健壮性
		- 监控中心宕机不影响使用，只是丢失部分采样数据
		- 数据库宕机后，注册中心仍然能够通过缓存提供服务列表查询，但不能注册新的服务
		- 注册中心对等集群，任意一台宕机后，将自动切换到另一台
		- 注册中心全部宕机后，服务提供者和服务消费者依旧能够铜锅本地缓存通信
		- 服务提供者无状态，任意一台宕机后，不影响使用
		- 服务提供者全部宕机之后，服务消费者无法使用，并将无限重新连接等待服务提供者恢复服务
	- 伸缩性
		- 注册中心为对等集群，可动态增加机器部署实例，所有客户端都将自动发现新的注册中心
		- 服务提供者无状态，可动态增加机器部署实例，注册中心将推送新的服务提供者信息给消费者

- Zookeeper 健壮性
	- Zookeeper 底层采用的是 ZAB 协议
	- 当 1/2 的实例宕机后，Zookeeper 依旧可以使用
	- 读写过程
		- 所有的写请求都会转发到主进程，子进程即便收到写请求也会再转发给主进程
		- 当主进程收到写请求时，会将所有的数据复制给所有的子进程，子进程收到后将会发送一个 ACK 给主进程
		- 主进程在收到 ACK 后，统计是否大于 1/2 子进程数，如果大于，则直接提交本次的写操作



### Spring Boot 中 Dubbo 的使用

- Spring Boot 集成 Dubbo

  - 核心依赖

  	```xml
  	<dependency>
  	  <groupId>org.apache.dubbo</groupId>
  	  <artifactId>dubbo-spring-boot-starter</artifactId>
  	  <version>3.0.2</version>
  	</dependency>
  	<dependency>
  	  <groupId>org.apache.dubbo</groupId>
  	  <artifactId>dubbo-registry-zookeeper</artifactId>
  	  <version>3.0.2</version>
  	</dependency>
  	<dependency>
  	  <groupId>org.apache.dubbo</groupId>
  	  <artifactId>dubbo-metadata-report-zookeeper</artifactId>
  	  <version>3.0.2</version>
  	</dependency>
  	```

  - 配置方式

    - XML 文件配置

      - 对于服务提供者，一般的配置如下所示

      	```xml
      	<?xml version="1.0" encoding="UTF-8"?>
      	
      	<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      	       xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
      	       xmlns="http://www.springframework.org/schema/beans"
      	       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
      	       http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
      	
      	  	<!-- 服务提供者的名字 -->
      	  	<dubbo:application name="demo-provider-xml"/>
      	
      	  	<!-- 注册中心的配置，这里以 Zookeeper 为例 -->
      	    <dubbo:registry address="zookeeper://127.0.0.1:2181"/>
      	
      	  	<!-- 对应的传输协议 -->
      	    <dubbo:protocol name="dubbo" port="20890"/>
      	
      	  	<!-- Bean 实例的配置 -->
      	    <bean id="orderService" class="org.xhliu.dubboproviderxml.impl.OrderServiceImpl"/>
      	
      	    <dubbo:service interface="org.xhliu.dubbotest.service.OrderService" ref="orderService"/>
      	
      	  	<!-- 元数据，Dubbo 2.7 以上才有价值，可以使用其他的方式存储元数据，如 Redis -->
      	<!--    <dubbo:metadata-report address="zookeeper://127.0.0.1:2181" />-->
      	</beans>
      	```

      - 对于服务消费者，一般的配置如下所示

      	```xml
      	<?xml version="1.0" encoding="UTF-8"?>
      	
      	<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      	       xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
      	       xmlns="http://www.springframework.org/schema/beans"
      	       xsi:schemaLocation="http://www.springframework.org/schema/beans
      	       http://www.springframework.org/schema/beans/spring-beans.xsd
      	       http://dubbo.apache.org/schema/dubbo
      	       http://dubbo.apache.org/schema/dubbo/dubbo.xsd">
      	  	<!-- 服务消费者的名字 -->
      	    <dubbo:application name="demo-consumer"/>
      	
      	  	<!-- 注册中心的配置 -->
      	    <dubbo:registry group="aaa" address="zookeeper://127.0.0.1:2181"/>
      	
      	  	<!-- 有关服务类的配置 -->
      	    <dubbo:reference id="orderService" check="false"
      	                     interface="org.xhliu.dubbotest.service.OrderService"/>
      	</beans>
      	```

      - 配置属性信息如下表所示：

      | 标签                                                         | 用途         | 解释                                                         |
      | ------------------------------------------------------------ | ------------ | ------------------------------------------------------------ |
      | `<dubbo:service/>`                                           | 服务配置     | 用于暴露一个服务，定义服务的元信息，一个服务可以用多个协议暴露，一个服务也可以注册到多个注册中心 |
      | `<dubbo:reference/>` [2](https://dubbo.apache.org/zh/docs/references/configuration/xml/#fn:2) | 引用配置     | 用于创建一个远程服务代理，一个引用可以指向多个注册中心       |
      | `<dubbo:protocol/>`                                          | 协议配置     | 用于配置提供服务的协议信息，协议由提供方指定，消费方被动接受 |
      | `<dubbo:application/>`                                       | 应用配置     | 用于配置当前应用信息，不管该应用是提供者还是消费者           |
      | `<dubbo:module/>`                                            | 模块配置     | 用于配置当前模块信息，可选                                   |
      | `<dubbo:registry/>`                                          | 注册中心配置 | 用于配置连接注册中心相关信息                                 |
      | `<dubbo:monitor/>`                                           | 监控中心配置 | 用于配置连接监控中心相关信息，可选                           |
      | `<dubbo:provider/>`                                          | 提供方配置   | 当 ProtocolConfig 和 ServiceConfig 某属性没有配置时，采用此缺省值，可选 |
      | `<dubbo:consumer/>`                                          | 消费方配置   | 当 ReferenceConfig 某属性没有配置时，采用此缺省值，可选      |
      | `<dubbo:method/>`                                            | 方法配置     | 用于 ServiceConfig 和 ReferenceConfig 指定方法级的配置信息   |
      | `<dubbo:argument/>`                                          | 参数配置     | 用于指定方法参数配置                                         |

      - 配置之间的对应关系

        <img src="https://dubbo.apache.org/imgs/user/dubbo-config.jpg">

      - 不同粒度配置的覆盖关系（以 timeout 属性为例）

        1. 方法级优先，接口级次之，全局配置再次之
        2. 如果级别一样，则消费方优先，提供方次之

        ![dubbo-config-override](https://dubbo.apache.org/imgs/user/dubbo-config-override.jpg)

      - 

    - Annotation配置

      - 服务提供者

        配置属性

        ```properties
        # dubbo-provider.properties
        dubbo.application.name=dubbo-annotation-provider
        dubbo.registry.address=zookeeper://${zookeeper.address:127.0.0.1}:2181
        dubbo.protocol.name=dubbo
        dubbo.protocol.port=20880
        dubbo.provider.token=true
        ```

        配置类

        ```java
        @Configuration
        @EnableDubbo(scanBasePackages = "org.xhliu.dubboproviderannotation.service.impl")// 实现接口的类所在包
        @PropertySource("classpath:/dubbo/dubbo-provider.properties")
        public class ProviderConfiguration {
            @Bean
            public ProviderConfig providerConfig() {
                ProviderConfig providerConfig = new ProviderConfig();
                providerConfig.setTimeout(1000);
                return providerConfig;
            }
        }
        ```

        服务实现类示例如下：

        ```java
        import lombok.extern.slf4j.Slf4j;
        import org.apache.dubbo.config.annotation.DubboService;
        import org.apache.dubbo.config.annotation.Method;
        import org.apache.dubbo.rpc.RpcContext;
        import org.xhliu.dubbotest.service.OrderService;
        
        @Slf4j
        @DubboService(
                version = "1.0.0", group = "test", timeout = 30000,
                methods = {@Method(name = "doOrder", timeout = 5000)}
        )
        public class OrderServiceImpl implements OrderService {
            @Override
            public String doOrder(String info) {
                log.info("Get Info: " + info);
                return "Hello " + info + ", response from provider: " + RpcContext.getServerContext().getLocalAddress();
            }
        }
        ```

        

      - 服务消费者

        配置属性

        ```properties
        # dubbo-consumer.properties
        dubbo.application.name=dubbo-annotation-consumer
        dubbo.registry.address=zookeeper://${zookeeper.address:127.0.0.1}:2181
        dubbo.consumer.timeout=1000
        ```

        配置类：与服务提供者的类似

        对于 RPC 方法的调用，示例如下：

        ```java
        import lombok.extern.slf4j.Slf4j;
        import org.apache.dubbo.config.annotation.DubboReference;
        import org.springframework.stereotype.Service;
        import org.xhliu.dubbotest.service.OrderService;
        
        @Slf4j
        @Service(value = "orderServiceCall")
        public class OrderServiceCall implements OrderService {
            @DubboReference(group = "test", version = "1.0.0") // 对应方法所在的组、版本号
            private OrderService orderService;
        
            @Override
            public String doOrder(String info) { // 本地方法对于指定方法的实现，这里调用了 RPC 的方法
                log.info("Consumer send Info: " + info);
        
                return orderService.doOrder(info);
            }
        }
        ```

- API 与 SPI

  - API

    > 应用程序接口

  - SPI

    > （Service Provider Interface）定义一组规范，要求调用方实现和扩展

    - 应用场景

      1. 数据库驱动 Driver等
      2. 日志 log，如 `slf4j` 和 `log4j`
      3. Spring Boot 自动装配规则

    - JDK SPI 与 Dubbo SPI

      > 区别：JDK SPI 文件格式不同，JDK 为 value 结构, Dubbo 为 Key-Value 结构；JDK SPI 会一次性全部加载，而 Dubbo 则是按需加载；因此可以使用 Dubbo 的 SPI 实现按需注入

      ![image.png](https://i.loli.net/2021/08/22/5gONp4GAXenSTx8.png)

      - 获取 JDK 的 SPI

        ```java
        import lombok.extern.slf4j.Slf4j;
        import org.xhliu.dubbotest.service.OrderService;
        
        import java.util.ServiceLoader;
        
        @Slf4j
        public class Application {
            public static void main(String[] args) {
                ServiceLoader<OrderService> orderServices = ServiceLoader.load(OrderService.class); // 加载对应的实现类，得到的是一个集合
                log.info(orderServices.toString());
                orderServices.forEach(orderService -> log.info(orderService.doOrder("Hello World!")));
            }
        }
        ```

      - 获取 Dubbo 的 SPI

        Dubbo 的 SPI 配置文件示例：

        ```
        man1=dubbo.impl.Man // man1 的实现类为 dubbo.impl.Man
        woman1=dubbo.impl.Woman // 同上
        ```

        

        ```java
        import dubbo.spi.Person;
        import org.apache.dubbo.common.extension.ExtensionLoader;
        
        public class SpiTest {
            public static void main(String[] args) {
                ExtensionLoader<Person> personExtensionLoader = ExtensionLoader.getExtensionLoader(Person.class);
                Person man = personExtensionLoader.getExtension("man1"); // 获取指定的实现类
                man.say();
            }
        }
        ```

        

- 线程派发模型

  - 配置

    ```xml
    <dubbo:protocol name="dubbo" port="20880" threadpool="fixed" 
                        threads="200" iothreads="8" accepts="0" queues="100" dispatcher="all">
    ```

    ![image.png](https://i.loli.net/2021/08/22/1fZHeCJAImWDa4u.png)

    1. Boss 线程

       > 接受客户端的连接，将接受到的连接注册到一个 Worker 线程上；
       >
       > 一般情况下，服务端每绑定一个端口，开启一个 Boss 线程

    2. worker 线程

       > 处理注册在其身上连接的 Connection 的各种 IO 事件；
       >
       > 一般情况下，Worker 线程的个数为计算机 CPU 的核心数 + 1；
       >
       > 一个 Worker 线程可以注册多个 Connection，但是一个 Connection 只能注册在一个 Worker 线程上

  - 线程派发策略

    1. All（默认）：将所有的消息都派发到线程池，包括请求、响应、连接事件、断开连接、心跳等。即 Worker 线程在收到事件之后，将该事件提交到业务线程池中，自己再去处理其他事。
    2. direct: Worker 线程在接收到事件之后，由 Worker 线程执行到底（所有线程消息都不派送到线程池，全部在 IO 线程上直接执行）
    3. message: 只有请求响应消息派发到线程池，其它连接断开事件、心跳等，直接在 IO 线程上执行
    4. execution: 只有请求消息派发到线程池，不含响应（客户端线程池）；响应和其它连接断开事件、心跳等信息，直接在 IO 线程上执行
    5. connection: 在 IO 线程上，将连接断开事件放入队列，有序逐个执行，其它消息派发到线程池。

  - 常用的 ThreadPool

    1. fixed: 固定大小线程池，启动时建立线程，不关闭，一直持有
    2. cached: 缓存线程池，空闲一分钟自动删除，需要时再重新创建
    3. limited: 可伸缩线程池，但是池中的线程数只会增长不会收缩。这是为了避免由于收缩时由于大流量引起的性能问题
       1. eager : 优先创建 Worker 线程池，在任务数量大于 corePollSize 但是小于 maximumPollSize 时，优先创建 Worker 来处理任务。当任务数量大于 maxmumPollSize 时，将任务放入阻塞队列中。阻塞队列填满时抛出 RejectExecuption。（相比较于 cached，cached 在任务数量超过 maxmumPollSize 时直接抛出异常而不是将任务放入阻塞队列） 

- Dubbo 线程模型图

  ![image.png](https://i.loli.net/2021/08/22/H7TLh3mQVfnqlUZ.png)

  - 整体步骤
    1. 客户端的主线程发出一个请求后得到 future，在执行 get() 方法时阻塞；
    2. 服务端使用 worker 线程（Netty 通信模型）接受到请求之后，将请求提交到 Server线程池中进行处理
    3. Server 线程处理完成之后，将相应的处理结果返回给客户端的 Worker 线程池（Netty 通信模型），最后，Worker 线程将响应结果提交到 Client 线程池进行处理
    4. client 线程将响应结果填充到 Future 中，然后唤醒等待的主线程，主线程获取结果，返回给客户端
