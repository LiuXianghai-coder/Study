# 运行时数据区

![image.png](https://i.loli.net/2021/06/30/s6kIWBgu48a3oDX.png)

1. ### 方法区

   - Object Class Data（加载类定义数据）存储在方法区
   - 常量、静态变量、JIT 编译后的代码存储在方法区
   - 方法区与存储数据的堆区有一种类比关系，因此也被成为 No-Heap 区。方法区可以有内存不连续的区域组成，可以设置为固定大小，也可以设置为可扩展的，这点与堆区一致。
   - 垃圾回收在方法区出现频率较低，回收的主要目的是针对常量池的回收和类的卸载
   - 运行时常量池（Runtime Constant Poll）
     - 存储编译器产生的字面量和符号引用
     - 运行时产生的新常量也会放入常量池中，比如 `String` 的 `intern()` 方法
     - 常量池是这个类型用到的常量的有序集合。包括直接常量（基本数据类型、String）和对其它类型的、方法、字段的符号引用。主要包括：
       - 类和接口的全限定名
       - 字段和名称的描述符
       - 方法和名称的描述符
   - 永久代是 `HostSpot` 的一个具体实现，永久代是方法区的具体实现，方法区是 JVM 的一个概念。
   - `JDK 1.8` 之后使用元空间取代，不再放入 `JVM`，而是放入本地物理机器内存。元空间取代后将常量和静态变量等放入堆区，使得方法区不再进行垃圾回收。

2. ### 堆区

   - 内存数据区，在 `JVM`	 启动时被创建，专门用来保存对象的实例。
   - 保存对象的实例也只是保存对象的实例属性，属性类型和对象本身的类型标记等以栈帧的形式保存在虚拟机栈中。
   - 对象实例在堆区分配之后，将会将自身的引用保存在一个 Java 虚拟机栈中，用于定位该实例在堆区中的位置。
   - Java 堆区只需逻辑上的空间连续即可。

3. ### 虚拟机栈

   - 方法本身是指令的操作码部分，保存在 `Stack`  中。
   - 方法内部变量作为指令的操作数部分，跟在指令操作码之后，保存在 `Stack` 中，对象类型在 `Stack` 中保存地址，在堆区中保存实例。
   - 在线程创建时随之创建，它的生命周期跟随线程的生命周期，因此不存在垃圾收集。
   - 局部变量表存放了编译可知的各种基本数据类型、对象的引用和下一条字节码指令的地址。局部变量表所需的运行空间在编译期间完成分配，在方法运行前，该局部变量表所需要的内存空间是固定的，运行期间也不会发生改变。

4. ### 本地方法栈

   为 `native` 方法提供执行方法的服务

5. ### 程序计数器

   当前线程执行的字节码的位置指示器。

# 内存分配

1. `new` 一个对象的流程

   <img src="https://i.loli.net/2021/06/30/JnwOPt1KxcDmjfB.png" alt="image.png" style="zoom:100%;" />

2. 总体流程

   <img src="https://i.loli.net/2021/06/30/gK1MhWLTY6cvkVz.png" alt="image.png" style="zoom:150%;" />

   - `TLAB`（Thread Local Allocation Buffer ）线程本地分配缓存，线程专用的内存分配区域。
     - 设置原因：大部分的对象的创建存活期比较短，多个线程进行对象的分配是会导致指针碰撞，使用 `TLAB` 为每个线程设置独立的空间，避免多个线程同时操作分配指针。
     - 设置虚拟机启动参数 `-XX:UseTLAB` 启动 `TLAB`，这样在线程初始化时就会申请一块指定大小的内存，只给当前线程使用，这样就不会存在多个线程之间的竞争。
     - `TLAB` 空间很小，默认情况下仅占有整个 Eden 区的 1 %，可以通过选项 `XX:TLABWasteTargetPercent` 设置对应的百分比。
     - `TLAB` 通过三个指针 start、top、和 end 来管理自己的内存区间，如果 top == end 则说明空间已经耗尽。
     - 缺点：空间大小固定，可能会导致内存碎片

# 对象头

1. `HotSpot` 虚拟机中，对象在内存中的布局分为三部分：对象头（Header）、实例数据（Instance Data）和对齐填充（Padding）

   - 对象在内存中的分布

     <img src="https://i.loli.net/2021/06/30/WMKIcRp4ni78sJU.png" alt="image.png" style="zoom:150%;" />

     - 第一部分 Mark Word 用于存储自身的运行时数据，如 `hashCode`、`GC` 分代年龄、锁标志状态、线程持有锁、偏向指针等。
     - 第二部分是 `KClass Pointer`，指针指向该对象对应的 Class 元数据的内存地址。使用 `+UseCompressedOops` 选项开启指针压缩，可以将 8 字节的指针压缩到 4 字节。
     - 第三部分是数组对象的大小，只有数组对象才有该字段。
     - 第四部分是对象的实例数据，包括对象的所有成员变量。
     - 第五部分是对齐填充部分，为了与计算机的寻址相匹配以提高效率，一般会填充到 8字节的倍数。

   - 在 64 位的机器上，具体布局如下所示：

     ![image.png](https://i.loli.net/2021/06/30/cGCS3knD7T4mzgW.png)

   - 64 位的机器上，Maker Word 分布如图所示：

     <img src="https://segmentfault.com/img/remote/1460000037646109/view" alt="preview" style="zoom:200%;" />

     - 由于锁的状态头保存在 Mark Word 中，因此使用 `Synchronized` 锁住的是对象。
     - `Synchronized` 有四种锁状态， 从低到高分别为：无锁、偏向锁、轻量级锁、重量级锁，锁的转换只能从低往上转换，无法逆转。

# JVM 整体结构

<img src="https://i.loli.net/2021/06/30/GFm9Pche8wIEYLq.png" alt="image.png" style="zoom:150%;" />



# 类加载

<img src="https://i.loli.net/2021/06/30/Tl827FaQrcogYKB.png" alt="image.png" style="zoom:150%;" />

1. ### 加载

   ​	将 `.class` 文件中的二进制数据加载到 `JVM` 中，将其放入运行时数据区的方法区内，然后在方法区创建一个 `java.lang.Class` 对象，以封装该类在方法区内的数据结构。在这个阶段，主要完成三件事：

   - 通过一个类的全限定名（包名与类名）获取此类的二进制字节流（Class 文件）。获取方式可以从磁盘或者网络流中获取。
   - 将字节流所代表的静态存储结构转变为方法区的运行时数据结构。
   - 在内存中生成一个代表这个类的 `java.lang.Class` 对象，作为这个类各种元数据的访问入口。该 `Class` 对象较为特殊，存放在方法区而不是堆区。

2. ### 链接

   ​	将之前加载的二进制数据文件的二进制数据合并到 `JRE`  中，此步骤分为三步。

   - 验证
     - 验证加载的类格式是否正确，类数据是否符合虚拟机规范。
   - 准备
     - 为类的静态变量赋默认初始值
     - 对于 `final` 修饰的类成员变量，将直接赋设定的初值。
   - 解析
     - 将二进制数据中的符号引用转换为直接引用

3. ### 初始化

   ​	类加载的最后一步，真正开始执行 `Java` 代码。以下几种情况会触发初始化：

   - 遇到 `new`、`getstatic`、`putstatic`、`invokestatic`这四条字节码指令时，如果类没有初始化，则初始化该类。生成这4条指令的最常见的Java代码场景是：使用new关键字实例化对象的时候、读取或设置一个类的静态字段（被final修饰、已在编译器把结果放入常量池的静态字段除外）的时候，以及调用一个类的静态方法的时候。
   - 使用 `java.lang.reflect` 包方法对类进行反射调用时，如果该类没有初始化，则首先初始化该类。
   - 初始化一个子类时，如果发现父类没有初始化，则首先触发父类初始化。对于静态字段，只有直接定义这个字段的类才会被初始化，因此，当使用子类引用父类的静态字段时，只会触发父类的初始化。
   - 虚拟机启动时，需要指定一个主类（main 方法），虚拟机会首先初始化该类。

4. ### 使用

   初始化完成之后， `JVM` 便开始从入口方法开始执行用户的程序代码。

5. ### 卸载

   程序代码执行完之后，`JVM` 便开始销毁创建的 `Class` 对象，最后负责运行的 `JVM` 也退出。

# 双亲委派模型

​	`JVM`在启动时，会通过不同的类加载器加载不同的类。对于每一个类，都需要有加载它的类加载器和这个类来确定在 `JVM` 中的唯一性，也就是说，两个类只有来源于同一个 `Class` 文件，并且被同一个类加载器加载，这两个类才是相等的。

![image.png](https://i.loli.net/2021/06/30/wEhGXDasKP3ZQBf.png)

1. ​	`JVM` 提供了三种类加载器：引导（BootStrap）类加载器、扩展（Extension）类加载器、系统（System）类加载器（应用类加载器）；其它的类加载器都是以应用类加载器为父类加载器的自定义类加载器。

   - BootStrap 类加载器（没有父类加载器）：
     - 主要加载 `JVM` 自身需要的类，这个类加载器由 `C++` 实现，是 `JVM `的一部分。负责加载 `/lib` 目录下的核心类库或 `-Xbootclasspath` 参数指定的路径下的 `jar` 包。该类加载器按照对应的文件名加载对应的 `jar` 包，因此即使将其它的 `jar` 包放入加载目录下也不会加载。（BootStrap 类加载器只加载包名为 `java`、`javax`、`sun`等开头的类）。

   - `ExtClassLoader` 类加载器（父类加载器为 null）
     - 负责加载 `/lib/ext` 目录下或 `-Djava.ext.dir` 指定路径中的类库。
   - `AppClassLoader` 类加载器（父类加载器为 `ExtClassLoader` 类加载器）
     - 负责加载系统类路径 `--classpath` 或 `-D java.class.path` 指定路径下的类库。一般情况下是程序中默认的类加载器，通过 `ClassLoader.getSystemClassLoader()` 方法可以获取到该类加载器。

2. 这三种类加载器之间存在一个父子关系，这是通过组合而不是继承来实现的。

3. 当一个类需要加载一个目标类时，先会委托父类加载器去加载，由父类加载器搜索目标类，父类加载器无法找到目标类时，才会交给子类加载器加载目标类。

4. 实现原理

   ![image.png](https://i.loli.net/2021/07/01/dwMAjV7W1N9u5vR.png)

   - `ClassLoader` 中的 `loadClass()` 方法便是双亲委派的实现，顶层的类加载器是 `ClassLader` 类，它是一个抽象类，其后所有的类加载器都要继承自 `ClassLoader`。

   - 主要的几个方法

     - `loadClass(String name, boolean resolve)` 是一个重载方法，`resovle` 参数代表是否生成 `Class` 对象时同时进行解析的相关操作。

       ```java
       protected Class<?> loadClass(String name, boolean resolve)
               throws ClassNotFoundException {
               synchronized (getClassLoadingLock(name)) {
                   // First, check if the class has already been loaded
                   // 首先，检测要加载的类是否已经被加载
                   Class<?> c = findLoadedClass(name);
                   if (c == null) {
                       long t0 = System.nanoTime();
                       try {
                           if (parent != null) {
                               // 如果父类加载器不为 null，则交给父类加载器去加载
                               c = parent.loadClass(name, false);
                           } else {
                               // 如果父类加载器为空，则交给 BootStrap 类加载器进行加载
                               c = findBootstrapClassOrNull(name);
                           }
                       } catch (ClassNotFoundException e) {
                           // ClassNotFoundException thrown if class not found
                           // from the non-null parent class loader
                       }
       
                       if (c == null) {
                           // If still not found, then invoke findClass in order to find the class.
                           // 如果依旧没有找到该 Class，则调用 findClass() 去查找该类
                           long t1 = System.nanoTime();
                           c = findClass(name);
       
                           // this is the defining class loader; record the stats
                           PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                           PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                           PerfCounter.getFindClasses().increment();
                       }
                   }
                   if (resolve) { // 是否需要链接到该类
                       resolveClass(c);
                   }
                   return c;
               }
           }
       ```

       

     - 自定义的类加载逻辑在 `findClass()` 方法中，需要子类重写该方法。由于该方法需要 `loadClass()` 方法进行调用，因此也是符合双亲委派的模式。一般情况下，自定义类加载器时，会直接覆盖 `ClassLoader` 的 `findClass()` 方法，将对应的加载类的字节码转换为流，然后调用 `defineClass()` 方法生成 `Class` 对象。

       ```java
       protected Class<?> findClass(String name) throws ClassNotFoundException {
       	byte[] classData = getClassData(name);
       	if (classData == null) {
       		throw new ClassNotFoundException();} 
           else {
               // 调用 defineClass() 方法生成该类的 Class 对象，这个 Class 对象还没有进行解析。
       		return defineClass(name, classData, 0, classData.length);
       	}
       }
       ```

     - `resolveClass(Class<?> c)`：使用该方法可以使得类的`Class` 对象创建完成时被解析。

   - `ClassLoader` 为抽象类，部分需要的方法未实现，`URLClassLoader` 提供了这些方法的具体实现，并增加了 `URLClassPath` 等类协助获得 `Class` 字节码的功能。在编写自定义类加载器时，可以考虑直接继承该类。

   - 双亲委派模式的好处：

     - 提高系统安全性，避免错误加载由用户自定义的系统类。
     - 避免类被重复加载。在 `JVM` 中，只有 `Class` 的全限定名和加载该类的类加载器实例对象一致才会认为是同一个`Class` 对象。

5. 自定义类加载器

   - 使用场景
     - 加密，自定义加载逻辑避免 `Class` 文件被反编译。
     - 从非标准来源（如数据库、云端等）加载类。
   - 定义方式
     - 如果不想打破双亲委派模型，那么只需要重写 `findClass()` 方法即可。
     - 如果想打破双亲委派模型，需要重写整个 `loadClass()` 方法。

6. 线程上下文类加载器

   ​	`Java` 中的服务提供接口（`SPI`），这些接口允许第三方提供实现，而这些 `SPI` 属于 `Java` 的核心库，只能由 `BootStrap` 类加载器进行加载，因此这时如果想要加载这些第三方库需要破坏双亲委托模型。

   ​	通过线程上下文，可以获得当前线程的类加载器，这个类加载器默认属于 `AppClassLoader`，因此可以加载第三方的 `jar` 包。可以通过 `getContextClasLoader()` 和 `setContextClassLoader()` 方法来获取和设置线程上下文类加载器。

   ​	具体如图所示（`DriverManager` 通过这种方式引入第三方驱动包）：

   ![image.png](https://i.loli.net/2021/07/01/cFvMYS3pLbrVtiK.png)

7. Tomcat 类加载器结构

   1. Tomcat 要解决的问题

      - 部署在同一 Web 容器上的两个 Web 应用程序所使用的 Java 类库可以相互隔离
      - 部署在统一 Web 容器上的两个 Web 应用程序所使用的 Java 类库可以共享
      - Web 容器需要尽可能地保证自身安全不受部署的 Web 应用程序影响

   2. Tomcat 加载机制设计图

      <img src="https://i.loli.net/2021/07/01/dvXYgepQbj8P1yN.png" alt="image.png" style="zoom:150%;" />

      默认的加载顺序：

      1. 先从缓存中加载
      2. 如果没有，则从 JVM 的 BootStrap 类加载器中加载
      3. 如果没有，则从当前类加载器加载（按照 `WEB-INF/classes`, `WEB-lib` 的顺序）
      4. 如果没有，则从父类加载器加载，加载顺序为 AppClassLoader、Common、Shared。

      <img src="https://i.loli.net/2021/07/01/esz7yQSfba1gqYL.png" alt="image.png" style="zoom:150%;" />