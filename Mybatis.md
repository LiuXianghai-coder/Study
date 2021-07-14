# 主流持久层技术架构

### JDBC

- 访问数据库的五个步骤
  1. 注册驱动和数据库信息
  2. 获取 `Connection`，得到 `Statement` 对象
  3. 通过 `Statement` 对象预执行 `SQL` 语句，得到处理结果
  4. 对处理结果进行相关的处理
  5. 关闭数据库连接对象
- 缺点
  - 代码量很大，比较麻烦
  - 需要对异常进行捕获并进行相应的处理

### Hibernate

- 优点
  1. 将映射规则分离到 `xml` 或注解中，减少了代码的耦合度
  2. 无需管理数据库连接，住需配置对应的 `XML`即可
  3. 一个会话，只需要操作 `Session` 即可
  4. 关闭资源，只需要关闭 `Session` 即可
- 缺点
  1. 全表映射不便利，更新时需要发送所有字段
  2. 无法根据不同的条件组装不同的 `SQL`
  3. 对于多表连接和复杂 `SQL` 查询支持较差，需要自己手动编写 `SQL`，同时需要自己将处理结果组合为 POJO 对象
  4. `HQL` 性能较差，无法优化 `SQL`
  5. 不能有效支持存储过程

### MyBatis

- 优点
  1. 可以动态配置 `SQL`
  2. 可以对 `SQL` 进行优化，并通过配置来决定 `SQL` 的映射规则
  3. 支持存储过程
  4. 具有自动映射功能，在注意命名规则的基础上，无须再写映射规则
  5. MyBatis 提供接口编程的映射器，只需要一个接口和映射文件便可以运行
  6. 代码耦合度低

# 配置文件

- `mybatis-config.xml`

  - Mybatis 的配置文件，使用时稍作修改即可

    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE configuration PUBLIC
            "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <configuration>
        <!-- 一些配置属性，在此处配置即可在别的区域使用 `$` 进行引用，方便统一进行管理 -->
        <properties>
            <property name="url" value="jdbc:mysql://127.0.0.1:3306/effect_db?useSSL=true&amp;serverTimezone=UTC"/>
            <property name="username" value="root"/>
            <property name="password" value="17358870357yi"/>
            <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
        </properties>
    
        <settings>
            <!-- 有关日志的配置， 可选的值为 SLF4J、LOG4J、LOG4J2、JDK_LOGGING、COMMONS_LOGGING、STDOUT_LOGGING、NO_LOGGING 或者任何实现了 `org.apache.ibatis.logging.Log ` 接口，且构造方法以字符串为参数的类完全限定名 -->
            <setting name="logImpl" value="STDOUT_LOGGING"/>
            
            <!-- 设置列和字段属性的自动配置，NONE 表示取消自动配置，PARTIAL 设置部分映射，FULL 表示全自动映射（包括嵌套映射） -->
            <setting name="autoMappingBehavior" value="NONE"/>
            
            <!-- 是否开启将下划线命名转换为驼峰式命名，如果开启，则需要启动上文的自动映射行为 -->
            <setting name="mapUnderscoreToCamelCase" value="true"/>
        </settings>
    
        <environments default="dev">
            <environment id="dev">
                <transactionManager type="JDBC" />
                <dataSource type="POOLED">
                    <property name="driver" value="${driver}"/>
                    <property name="url" value="${url}"/>
                    <property name="username" value="${username}"/>
                    <property name="password" value="${password}"/>
                </dataSource>
            </environment>
        </environments>
    
        <databaseIdProvider type="DB_VENDOR" />
    
        <mappers>
            <mapper resource="mapper/UserMapper.xml" />
        </mappers>
    </configuration>
    ```

    

- `mapper` 映射文件

  - 入参方式

    > 1. `@Param` 注解指定传入参数名称
    > 2. 不带任何注解，则以传入的参数名为传入的参数
    > 3. 使用 `Map`， 设置对应的查询参数，将 `Map` 作为参数传入，可以得到 `Map` 的 key 的参数值
    > 4. 以实体的方式传入参数，可以得到实体的字段属性值

  - 相关标签

    > 1. <mapper>
    >    - 映射文件的主标签，设置 `namespace` 属性指定关联的要实现接口
    > 2. <resultMap>
    >    - 配置类属性和查询列的映射规则
    > 3. <insert>
    >    - 插入方法执行后需要手动提交事物，才能完成记录的插入（或者在调用 `openSession` 方法时设置为 true，即可实现插入时的自动提交）
    >    - 设置 `useGenearatedKeys` 实现自增主键的回写

  - 入参与结果集

    > 1. `$` 与 `#` 传递参数的区别
    >    - `$` 使用值传递的方式构建 `SQL` 语句， 可能会造成 `SQL` 注入，存在安全性的问题
    >    - `#` 使用预编译的方式构建 `SQL` 语句
    > 2. 存储结果集的方式
    >    - `Map` 方式存储结果集
    >    - `POJO` 方式存储结果

# 级联

![2021-07-04 18-53-57 的屏幕截图.png](https://i.loli.net/2021/07/04/2NzYDGC7xKhkqSi.png)

- 一对一级联
  - <assocaition> 直接关联到 `DAO` 即可
- 一对多级联
  - <collection> 直接关联对应的 `DAO` 即可

# 缓存

- 一级缓存
  - MyBatis 默认开启的缓存
  - 多次对同一个 Session 的同一对象的查询，将会从缓存中查询
  - 每次新的 Session 的创建，都会使得一级缓存失效
- 二级缓存
  - 需要手动开启
  - 在 `Mapper.xml` 文件内，添加 `<cache />` 标签即可开启二级缓存
  - 每次记录的读都要手动提交事务，才能使得二级缓存可以命中
  - 要求实例化对象实现 `Serializable` 接口，即实例化对象是可序列化的
- 自定义缓存
  - 要求实现 `org.apache.ibatis.cache.Cache` 接口
  - 在 `Mapper.xml` 中加入 <cache> 标签
  - 将 <cache> 标签的 `type` 属性设置为自定义标签



# MyBatis 组件

1. `Executor`
2. `StatementHandler`
3. `PreparementHandler`
4. `ResultSetHandler`

# Mybatis 执行流程

1. 首先在创建 `Session` 会将对应的 `Mybatis` 配置文件读入 `Configuration` 对象中，注册相关的 `Mapper.xml` 映射文件

2. `getMapper` 的执行流程

   - `DefaultSqlSession` 调用 `Configuration` 的 `getMapper`
   - `MapRegister` 提供一个 `HashMap`， 保存对每个 `Mapper.xml` 的注册
   - 通过对应的`Mapper.xml`生成对应的代理对象，该代理对象使用的是 JDK 的动态代理的方式

   ![阶段1：获得Mapper动态代理阶段.jpg](https://i.loli.net/2021/07/04/tobpELi6GUF5JVR.png)

   

3.  `mapper`接口方法的执行

   - 调用代理对象的 `invoke` 方法，被代理的方法所在的类

     ```java
     method = getUserById(2L);
     declaringClass = mapper.UserMapper.class;
     ```

   - 如果得到的的 `declaringClass` 是 `java.lang.Object`，则调用 `Object` 的方法；如果 `method` 是 `defaultMethod` ， 那么调用 `defaultMethod`

   - 如果以上两个条件都不满足，则会获取 `MapperMethod` 实例

     - 如果缓存中存在对应的 `MapperMethod` 实例，则直接从缓存中获取

     - 创建 `SqlCommand` 对象

       - `SqlCommand` 只维护两个属性

         ```java
         // MappedStatement 的唯一标识 id
         private final String name;
         
         // sql的命令类型 UNKNOWN, INSERT, UPDATE, DELETE, SELECT, FLUSH;
         private final SqlCommandType type;
         ```

       - 拼装 `statementId`

         > 如 `mapper.UserMapper.getUserById`

         - 获取 `MappedStatement` 对象
           - 以 `statementId` 为 `key`，查询 `Configuration` 配置对象内的 `Map<String, MappedStatement> mappedStatements `，如果对应的 `MappedStatement`，则直接返回该 `MappedStatement` 实例对象
           - 如果 `Configuration` 对象不包含对象，则遍历 `Mapper` 接口类的方法，查询这些接口方法是否有对应的 `MappedStatement`， 如果有，则直接返回该实例对象，否则返回 `null`
         - 得到 `SqlCommand` 对象
           - 如果得到的 `MappedStatement` 不为 `null`，则可以从 `MappedStaement` 中得到对应的方法签名和该 `SQL` 的操作类型。
           - 如果得到的 `MappedStatement` 为 `null`，则会检查对应的方法是否设置了 `@Flush` 注解，如果设置了 `@Flush` 注解，那么就会将方法全限定名设置为 `null`，`SQL` 执行类型为 `FLUSH`。
           - 如果未设置 `@Flush` 注解，则会抛出 `BindingException` 异常

     - 创建 `MethodSignature` 对象

   - 将 `MapperMethod` 放入缓存

   ![阶段2：获得MapperMethod对象.jpg](https://i.loli.net/2021/07/04/hObkAZe4E1WU7xi.png)

   

4. 选择合适的执行方法

   - 按照传入的 `SqlCommand` 参数对象，选择相应的方法执行具体的方法
   - 该执行采用的是命令模式

   ![阶段3：根据SQL指令跳转执行语句.jpg](https://i.loli.net/2021/07/04/2T9VPpsCox57cdH.png)

5. 具体执行（针对缓存的的处理）

   ![阶段4：查询前的缓存处理.jpg](https://i.loli.net/2021/07/04/72cSsRnZVbykz6g.png)

6. 具体数据库查询

   ![阶段5：执行DB查询操作.jpg](https://i.loli.net/2021/07/04/mXfiRWPOG3poAxv.png)

7. 对于结果进行封装

   ![阶段6：针对ResultSet结果集转换为POJO.jpg](https://i.loli.net/2021/07/04/3P14OuiF7sVh92R.png)
