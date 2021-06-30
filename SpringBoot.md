# SpringBoot 2.x

- 微服务
- 响应式编程
- 云开发（Spring Cloud、Spring Cloud Alibaba）
- web 应用开发
- 无服务
- 事件驱动
- 任务处理（Batch）

# 常用注解

- @SpringBootApplication

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) }) // 组件扫描
public @interface SpringBootApplication
```

- @SpringBootConfiguration

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration // 标注该类是一个配置类
@Indexed
public @interface SpringBootConfiguration
```

- @ComponentScan：具备组件扫描能力

  ```java
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  @Documented
  @Repeatable(ComponentScans.class)
  public @interface ComponentScan 
  ```

- @EnableAutoConfiguration

  ```java
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @Inherited
  @AutoConfigurationPackage
  @Import(AutoConfigurationImportSelector.class)
  public @interface EnableAutoConfiguration
  ```

- @AutoConfigurationPackage

  ```java
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @Inherited
  @Import(AutoConfigurationPackages.Registrar.class) // 获取当前主应用的包路径 register(registry, new PackageImports(metadata).getPackageNames().toArray(new String[0]))
  public @interface AutoConfigurationPackage 
  ```

- @Import(AutoConfigurationImportSelector.class)

  ```java
  public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
  ```

  ```java
  private static Map<String, List<String>> loadSpringFactories(ClassLoader classLoader) {
  		Map<String, List<String>> result = cache.get(classLoader);
  		if (result != null) {
  			return result;
  		}
  
  		result = new HashMap<>();
  		try {
  			Enumeration<URL> urls = classLoader.getResources(FACTORIES_RESOURCE_LOCATION);
  			while (urls.hasMoreElements()) {
  				URL url = urls.nextElement();
  				UrlResource resource = new UrlResource(url);
  				Properties properties = PropertiesLoaderUtils.loadProperties(resource);
  				for (Map.Entry<?, ?> entry : properties.entrySet()) {
  					String factoryTypeName = ((String) entry.getKey()).trim();
  					String[] factoryImplementationNames =
  							StringUtils.commaDelimitedListToStringArray((String) entry.getValue());
  					for (String factoryImplementationName : factoryImplementationNames) {
  						result.computeIfAbsent(factoryTypeName, key -> new ArrayList<>())
  								.add(factoryImplementationName.trim());
  					}
  				}
  			}
  
  			// Replace all lists with unmodifiable lists containing unique elements
  			result.replaceAll((factoryType, implementations) -> implementations.stream().distinct()
  					.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList)));
  			cache.put(classLoader, result);
  		}
  		catch (IOException ex) {
  			throw new IllegalArgumentException("Unable to load factories from location [" +
  					FACTORIES_RESOURCE_LOCATION + "]", ex);
  		}
  		return result;
  	}
  ```

  加载原理：

  - 查找所有项目依赖包
  - 遍历所有 jar 包下所有的 `META-INF/spring.factories` 文件。（有的没有）
  - 将其加入 IOC 容器中，作为候选 Bean

# SpringBoot 原理

```java
public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
		return new SpringApplication(primarySources).run(args);
}
```

启动分为两部分：

1. 创建 `SpringApplication`

   ```java
   public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
   		return new SpringApplication(primarySources).run(args);
   	}
   ```

   - 一共分为7个步骤，都是初始化属性

     - 初始化 `ResourceLoader`（资源加载器）

       ```java
       this.resourceLoader = resourceLoader; // 启动时为 null
       ```

       

     - 初始化 `Set<Class<?>> primaryResource`（主要资源）

       ```java
       this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
       ```

     - 初始化 `WebApplicationType webApplicationType` 。（Web 应用类型：NONE、SERVLET、REACTIVE）

       通过 `classpath` 判断当前使用的 Web 应用类型

     - 初始化`List<BootstrapRegistryInitializer> bootstrapRegistryInitializers`引导程序注册容器

       ​	从 `META-INF/spring.factories` 配置文件中，获取 `Bootstrapper.class` 和 `BootstrapRegistryInitializer.class` 两个配置类型，然后合并到 `BootstrapRegistryInitializers` 中。

       ​	二者都是通过 `getSpringFactoriesInstances()` 方法获取。

     - 使用 `getSpringFactoriesInstances()` 获取  `ApplicationContextInitializer.class`， 初始化 `ApplicationContextInitializer<?>> initializers`（应用上下文）

       

     - 使用 `getSpringFactoriesInstances()` 获取 `ApplicationListener.class`，初始化 `ApplicationListener<?>> listeners`（应用监听器）

     

     - 初始化启动类

   - `getSpringFactoriesInstances()` 从 `Spring`容器中加载

2. 调用 `SpringApplication` 实例方法`run()`启动

   

   

