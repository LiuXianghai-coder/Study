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

    		

    - Annotation配置

  - 

- 

