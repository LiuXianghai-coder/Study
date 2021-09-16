# Eureka 服务治理

## Eureka

- Eureka 是 Netflix 的一个子模块，也是核心模块之一
- Eureka 是一个基于 `REST` 的服务，用于定位服务，以实现云端中间层服务发现和故障转移
- 服务注册和发现对于微服务架构来说是非常重要的，有了服务发现和注册，只需要使用服务的标识符，就可以访问到服务，而不需要修改服务调用的配置文件
- Eureka 采用 C-S 设计架构。包含两个组件：Eureka Server 和 Eureka Client
- Eureka Server 作为服务注册功能的服务器，是服务的注册中心
- 系统中其它的微服务，使用 Eureka Client 连接到 Eureka Server 并维持心跳连接。这样系统的维护人员就可以通过 Eureka Server 来监控各个微服务是否正常运行。



## CAP理论

- C：一致性

  > 分布式系统中，不同的节点之间都有各自的数据，如果能够在同一时间内都能保证所有节点都能访问最新的数据副本

- A：可用性

  > 可用在服务上，在集群中一部分节点出现故障之后，整个集群是否还能响应客户端的请求

- P：分区容错性

  > 分区相当于对通信的时限要求，如果系统不能在时限内达成数据一致性，则意味着发生了分区的情况

CAP理论：分布式系统最多同时满足 CAP 中的两项



## BASE 理论

- BA：基本可用
- S：软状态
- E：最终一致性

BASE 理论：在 CAP 理论上构建的系统被称作 `BASE`架构



## Eureka 架构

<img src="https://i.loli.net/2021/09/14/IoGLCfEs8YXdK2w.png" style="zoom:80%" />



## Eureka 核心功能

- 服务注册功能

  > - 查看所有服务的注册列表
  >   - GET http://{host}:{port}/eureka/apps
  >
  > - 查看某一服务的注册列表
  >   - GET http://{host}:{port}/eureka/apps/{SERVICE-NAME}

  ```xml
  
  <applications>
    <versions__delta>1</versions__delta>
    <apps__hashcode>UP_2_</apps__hashcode>
    <application>
      <name>EUREKA-CONSUMER</name> <!-- 服务名 -->
      <instance>
        <instanceId>192.168.0.4:eureka-consumer:8080</instanceId> <!-- 实例ID -->
        <hostName>192.168.0.4</hostName>
        <app>EUREKA-CONSUMER</app>
        <ipAddr>192.168.0.4</ipAddr>
        <status>UP</status>
        <overriddenstatus>UNKNOWN</overriddenstatus>
        <port enabled="true">8080</port>
        <securePort enabled="false">443</securePort>
        <countryId>1</countryId>
        <dataCenterInfo class="com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo">
          <name>MyOwn</name>
        </dataCenterInfo>
        <leaseInfo>
          <renewalIntervalInSecs>30</renewalIntervalInSecs>
          <durationInSecs>90</durationInSecs>
          <registrationTimestamp>1631573695225</registrationTimestamp>
          <lastRenewalTimestamp>1631573695225</lastRenewalTimestamp>
          <evictionTimestamp>0</evictionTimestamp>
          <serviceUpTimestamp>1631573694710</serviceUpTimestamp>
        </leaseInfo>
        <metadata>
          <management.port>8080</management.port>
        </metadata>
        <homePageUrl>http://192.168.0.4:8080/</homePageUrl>
        <statusPageUrl>http://192.168.0.4:8080/actuator/info</statusPageUrl>
        <healthCheckUrl>http://192.168.0.4:8080/actuator/health</healthCheckUrl>
        <vipAddress>eureka-consumer</vipAddress>
        <secureVipAddress>eureka-consumer</secureVipAddress>
        <isCoordinatingDiscoveryServer>false</isCoordinatingDiscoveryServer>
        <lastUpdatedTimestamp>1631573695225</lastUpdatedTimestamp>
        <lastDirtyTimestamp>1631573694685</lastDirtyTimestamp>
        <actionType>ADDED</actionType>
      </instance>
    </application>
    <application>
      <name>EUREKA-CLIENT-PRODUCER</name>
      <instance>
        <instanceId>192.168.0.4:eureka-client-producer:7000</instanceId>
        <hostName>192.168.0.4</hostName>
        <app>EUREKA-CLIENT-PRODUCER</app>
        <ipAddr>192.168.0.4</ipAddr>
        <status>UP</status>
        <overriddenstatus>UNKNOWN</overriddenstatus>
        <port enabled="true">7000</port>
        <securePort enabled="false">443</securePort>
        <countryId>1</countryId>
        <dataCenterInfo class="com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo">
          <name>MyOwn</name>
        </dataCenterInfo>
        <leaseInfo>
          <renewalIntervalInSecs>30</renewalIntervalInSecs>
          <durationInSecs>90</durationInSecs>
          <registrationTimestamp>1631571414235</registrationTimestamp>
          <lastRenewalTimestamp>1631573785227</lastRenewalTimestamp>
          <evictionTimestamp>0</evictionTimestamp>
          <serviceUpTimestamp>1631571413728</serviceUpTimestamp>
        </leaseInfo>
        <metadata>
          <management.port>7000</management.port>
        </metadata>
        <homePageUrl>http://192.168.0.4:7000/</homePageUrl>
        <statusPageUrl>http://192.168.0.4:7000/actuator/info</statusPageUrl>
        <healthCheckUrl>http://192.168.0.4:7000/actuator/health</healthCheckUrl>
        <vipAddress>eureka-client-producer</vipAddress>
        <secureVipAddress>eureka-client-producer</secureVipAddress>
        <isCoordinatingDiscoveryServer>false</isCoordinatingDiscoveryServer>
        <lastUpdatedTimestamp>1631571414235</lastUpdatedTimestamp>
        <lastDirtyTimestamp>1631571413714</lastDirtyTimestamp>
        <actionType>ADDED</actionType>
      </instance>
    </application>
  </applications>
  ```

  

- 服务续约功能

- 服务同步功能

- 服务获取功能

- 服务调用功能

- 服务下线功能

  > - 服务下线
  >   - PUT http://{host}:{port}/eureka/apps/{SERVICE-NAME}/{INSTABCE-ID}/status?value=OUT_OF_SERVICE
  > - 服务恢复
  >   - PUT http://{host}:{port}/eureka/apps/{SERVICE-NAME}/{INSTANCE-ID}/status?value=UP

- 服务剔除功能

  > - 服务剔除
  >   - DELETE http://{host}:{port}/eureka/apps/{SERVICE-NAME}/{INSTANCE-ID}

- 服务的自我保护



## 源码解析

- Eureka Client 源码解析
  - `@EnableEurekaClient`
  - `@EnableDiscoveryClient`

- Eureka Server 源码解析

