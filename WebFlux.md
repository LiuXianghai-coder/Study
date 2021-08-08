# Spring WebFlux 核心组件

- HttpHandler

- WebHandler

  > WebFlux 将 DispatcherHandler 作为一个默认实现。具体体现在 `org.springframework.web.reactive.config.WebFluxConfigurationSupport` 的 `webHandler` 方法中。

- HandlerMapping（annotation、route function）

- Route Function

  - 处理流程

    ​	每个请求经过上层的 WebHandler 分发后，将对应的请求转发到对应的路由函数中，在路由函数中执行对应的 HandlerFunction，由于是非阻塞的异步事件，因此会之间返回一个 Mono\<Resonpse\> 而无需等待 HandlerFunction 执行完毕。 

    <img src="https://i.loli.net/2021/08/07/auOTomB8rDcNx6C.png" alt="image.png" style="zoom:80%;" />

- HandlerFunction

### 相关配置

- `ReactiveWebServerFactoryAutoConfiguration`

  - 导入相关存在的服务器类（`Tomcat`，`Neety`、`Jesey` 等）来选择应用程序使用的服务器

- `WebFluxAutoConfiguration`

  - 导入相关的一些配置文件

- `EnableWebFluxConfiguration`

  - 相当于给 `Spring`应用程序添加了 `@EnableWebFlux` 注解

- `WebFluxConfigurationSupport`

  - 将 `DispatcherHandler` 做为 `WebHandler` 的默认实现

    

### Spring Reactor 的启动流程

<img src="https://i.loli.net/2021/08/07/v2wOU6TFtuYJVcb.png" alt="image.png" style="zoom:67%;" />



1. 根据 `classpath`，推断出 `WebApplication` 类型

   ```java
   // org.springframework.boot.WebApplicationType
   static WebApplicationType deduceFromClasspath() {
       // 如果 WebFlux 对应的类出现在应用中并且能够被加载且 Tomcat 和 Jersey 对应的类没有出现，那么该 Web 应用对应类型的就是 WebFlux
       if (ClassUtils.isPresent(WEBFLUX_INDICATOR_CLASS, null) && !ClassUtils.isPresent(WEBMVC_INDICATOR_CLASS, null)
           && !ClassUtils.isPresent(JERSEY_INDICATOR_CLASS, null)) {
           return WebApplicationType.REACTIVE;
       }
       // javax.servlet.Servlet 和 org.springframework.web.context.ConfigurableWebApplicationContext 是否出现并且能够被加载
       // 如果不能被加载，说明该应用不是一个 Web 应用
       for (String className : SERVLET_INDICATOR_CLASSES) {
           if (!ClassUtils.isPresent(className, null)) {
               return WebApplicationType.NONE;
           }
       }
       // 如果以上两个类都能被加载，那么该 Web应用是基于 Servlet 类型的
       return WebApplicationType.SERVLET;
   }
   ```

2. 获取 `AnnotationConfigReactiveWebServerApplicationContext` 应用上下文

3. 创建 WebServer

   1. 通过 `EmbeddedNetty` 获取 `NettyReactiveWebServerFactory`

      ```java
      // org.springframework.boot.autoconfigure.web.reactive 静态类 EmbeddedNetty
      static class EmbeddedNetty {
          @Bean
          @ConditionalOnMissingBean
          ReactorResourceFactory reactorServerResourceFactory() {
              return new ReactorResourceFactory();
          }
          
          @Bean
          NettyReactiveWebServerFactory nettyReactiveWebServerFactory(ReactorResourceFactory resourceFactory,
                                                                      ObjectProvider<NettyRouteProvider> routes, 		
                                                                      ObjectProvider<NettyServerCustomizer> serverCustomizers) {
              NettyReactiveWebServerFactory serverFactory = new NettyReactiveWebServerFactory();
              serverFactory.setResourceFactory(resourceFactory);
              routes.orderedStream().forEach(serverFactory::addRouteProviders);
              serverFactory.getServerCustomizers().addAll(serverCustomizers.orderedStream().collect(Collectors.toList()));
              return serverFactory;
          }
      
      }
      ```

   2. 通过 `NettyReactiveWebServerFactory` 得到 WebServer

      ```java
      public WebServer getWebServer(HttpHandler httpHandler) {
          HttpServer httpServer = createHttpServer();
          ReactorHttpHandlerAdapter handlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);
          NettyWebServer webServer = createNettyWebServer(httpServer, handlerAdapter, this.lifecycleTimeout,
                                                          getShutdown());
          webServer.setRouteProviders(this.routeProviders);
          return webServer;
      }
      ```

   3. 创建 WebServer

      ```java
      // org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext
      
      private void createWebServer() {
          WebServerManager serverManager = this.serverManager;
          if (serverManager == null) {
              StartupStep createWebServer = this.getApplicationStartup().start("spring.boot.webserver.create");
              String webServerFactoryBeanName = getWebServerFactoryBeanName();
              
              ReactiveWebServerFactory webServerFactory = getWebServerFactory(webServerFactoryBeanName);
              createWebServer.tag("factory", webServerFactory.getClass().toString());
              boolean lazyInit = getBeanFactory().getBeanDefinition(webServerFactoryBeanName).isLazyInit();
              // 桥接 http 请求与 Spring Handler
              this.serverManager = new WebServerManager(this, webServerFactory, this::getHttpHandler, lazyInit);
              getBeanFactory().registerSingleton("webServerGracefulShutdown",
                                                 new WebServerGracefulShutdownLifecycle(this.serverManager.getWebServer()));
              getBeanFactory().registerSingleton("webServerStartStop",
                                                 new WebServerStartStopLifecycle(this.serverManager));
              createWebServer.end();
          }
          initPropertySources();
      }
      ```

4. 启动 WebServer

5.  `HttpHandlerAutoConfiguration.httpHandler()` 将应用上下文中 `WebFilter`、`WebHandler`、`exceptionHandlers` 等构建并且组合。 

6. 调用处理函数

