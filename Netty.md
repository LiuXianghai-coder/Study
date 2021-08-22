# Netty 网络编程

### BIO

- ServerSocket
- Socket

### NIO

> - JDK 1.4 引入的被称为 New IO，支持高性能和密集型的 IO 操作
>
>   模型：
>
>   ​	![image.png](https://i.loli.net/2021/08/17/lsXF4ZvdcgC1fBh.png)
>
>   - Disk Buffer：一个用于读取存储在磁盘上的数据块的 RAM，这个操作是一个十分慢的操作，因为它要调用物理磁盘指针的移动来获取数据
>   - OS Buffer：OS 有自己的缓存可以缓存更多的数据，并且可以更加优雅地对数据进行管理。这个缓存也可以在应用程序之间进行共享。
>   - Application Buffer：应用程序自己的缓存
>
> - NIO 的数据传输是通过实现了 java.nio.Buffer 类的被成为 buffers 来实现的。一个 Buffer 与数组有一些相似，但是通过与底层操作系统紧密耦合因此更加有效率一些。一个 Buffer 是一个连续的、线性的存储，这跟数组有点像。一个 Buffer 有固定的大小。
>
> - buffer 的实现类对除了 boolean 类型的基本数据类型都有相对应的实现。这些实现类的抽象父类 java.nio.Buffer 提供了对于所有 buffer 的一般属性，并且定义了一个小的一般操作集合。
>
> - 一个 buffer 有 capacity、position、limt和 mark
>
>   - 容量 capacity 在创建 Buffer 时必须指定并且之后不可以被改变。可以通过 capacity() 方法来获取容量。
>   - limt 指定当前已经占用的空间，换句话说，当前的 buffer 的有效数据是从 0 - limit - 1。可以通过调用 limit() 方法得到当前已经使用的空间，也可以通过调用 `limit(int newLimit)` 来重新设置 limit，重新设置的 limit 大小不能超过 capacity 的大小。
>   - 与数组不同，buffer 存在一个被称为 position （或者叫游标）用于指定要读取或者写入的数据片。可以通过 调用`position()`方法来得到当前的位置，或者通过 `position(int newPosition)`来重新设置。重新设置的 position 大小不能超过 limit 的大小
>   - mark 提供了一个 position 的标记，可以通过调用 `mark()` 方法标记当前的 position
>
> - 数据传输
>
>   - 每个基本数据类型对应的 buffer 实现类都定义了 get() 和 set() 操作集合，用于读取或者写入一个元素或数组到 buffer。在传输数据时，buffer 的 position 将会增加。
>
>     ```java
>     // IntBuffer - Similar operations available for ByteBuffer, CharBuffer, 
>     //             ShortBuffer, LongBuffer, FloatBuffer and DoubleBuffer
>     public int get()                  // Reads an element from the current position (relative get)
>     public int get(int position)      // Reads an element from the given position (absolute get)
>     public IntBuffer get(int[] dest)  // Relative bulk get into the destination array
>     public IntBuffer get(int[] dest, int offset, int length)
>       
>     public IntBuffer put(int element)                 // relative put (at current position)
>     public IntBuffer put(int position, int element)   // absolute put
>     public IntBuffer put(int[] source)                // relative bulk put
>     public IntBuffer put(int[] source, int offset, int length)
>     ```
>
>   - ByteBuffer 不仅提供了以上的一些方法，而且提供了一些额外的方法将原始的 bytes 解析为基本数据类型。它也可以作为 IO 操作的源头或终点。
>
> - Mark 和 Reset
>
>   - 通过使用 `mark()`  方法去标记当前的 position，调用`reset()` 方法设置当前的 position 到之前标记的 position。如果之前的标记的 position 可能不存在，如果此时调用 `reset()` 方法将会触发 `InvalidMarkException`。 如果之前标记的 position 存在，它应该永远不会大于当前的 position。当 postiion 或 limit 被调整到小于 mark 时，将会丢弃当前的 mark。因此，这四者之间的大小关系为：0 ≤ mark ≤ position ≤ limit ≤ capacity
>
> - Clear、Flip、Rewind
>
>   - clear()：设置当前的 postion 到 0，limit 至 capacity，丢弃 mark，为 buffer 输入做准备。
>   - flip()：设置 limit 到当前 position，position 到 0，丢弃 mark，此时 buffer 已经填充并且准备输出
>   - rewind()：设置 postion 至 0，丢弃 mark，为 buffer 重新读取做准备
>
> - 创建一个 buffer
>
>   - 有三种方式创建 buffer
>     - 调用 `allocate()` 方法，这将分配一个新的缓冲区，同时将 position 设置为 0，limit 设置为 capacity，清除 mark
>     - 通过调用 `wrap(type[] array, int offset, int length)` 或  `wrap(type[] array)` 方法包装一个数组转变为 buffer
>     - 通过创建一个已经存在的 ByteBuffer 的视图
>
> - 直接 buffer 和非直接 buffer
>
>   - 直接 buffer
>     - JVM 将会尽最大努力直接在 buffer 上执行 IO 操作，因此，它将试图去避免在每次调用系统 IO 操作之前将缓冲区内容复制到中间缓冲区。所以，直接 buffer 将会更加有效率些。
>     - 对于 ByteBuffer，可以通过调用 `allocateDirect(int capacity)` 分配一个直接 ByteBuffer，对于其他的 buffer（char、short、int、float、long、double），需要首先创建一个 ByteBuffer，然后通过类似于 `FloatBuffer()` 的方法创建一个视图。由于这些基本数据类型是由多个 byte 组成的单元，所以需要通过 `order(ByteOrder order)` 指定 byte 的端顺序（大端—大byte在前、小端—小 byte 在前）。order 参数可以是  `ByteOrder.BIG_ENDIAN`, `ByteOrder.LITTLE_ENDIAN`，或者 `ByteOrder.nativeOrder()`自动得到当前平台的端顺序。
>   - 非直接 buffer
>
> - ByteBuffer
>
>   - 用于 Channel I/O
>   - 可以直接分配直接 buffer
>   - 可以用于创建其他 buffer 的视图
>   - 可以直接获取/设置其它基本数据类型
>   - MapByteBuffer 用于映射 IO
>
> - MappedByteBuffer
>
>   - 被操作系统而不是应用程序管理的直接 buffer。也就是说，MappedByteBuffer 可以用于包转操作系统的 buffer，应用程序可以分配不同的直接 buffer 来查看不同部分的操作系统 buffer



> java.nio.channels.Channel
>
> - 一个 Channel 代表一个物理 IO 连接。跟标准 IO 流有点像，但是 Channel 是一个更加依赖平台的流版本。由于channel 跟平台之间的有着紧密联系，因此它能够实现更好的 IO 吞吐量。 channel 分为以下几种：
>   - FileChannel
>   - Socket Channel：支持非阻塞 TCP Socket 连接
>   - DatagramChannel：UDP 面向数据报 Socket 的连接
>
> - Channel 对象的获取
>
>   - 通过 `java.io.FileInputStream`, `java.io.FileOutputStream`, `java.io.RandomAccessFile`, `java.net.Socket`, `java.net.ServerSocket`, `java.net.DatagramSocket`, and `java.net.MulticastSocket` 等对象的 `getChannel()` 方法可以获取一个 Channel 对象。
>
>   - 从 `FileInputStream` 获取的 Channel 是只读的，而从 `FileOutputStream` 获取的 Channel 是只写的。对于 `FileChannel` ，数据传输是通过访问 ByteBuffer 对象的 read() / write() 方法。
>
>     ```java
>     public abstract int read(ByteBuffer dest)
>     public abstract int write(ByteBuffer source)
>     ```
>
>   - 可以通过以下方法方法在 Input Channel 和 Output Channel 之间直接访问
>
>     ```java
>     public abstract long transferFrom(ReadableByteChannel source, long position, long count)
>     public abstract long transferTo(long position, long count, WritableByteChannel target)
>     ```



- ServerSocketChannel	

- SocketChannel

- Selector API

  > 1. channel.register(selector) 注册监听
  > 2. while (true) + select() 轮询事件
  > 3. selectedKeys() 获得 SelectKey 对象，表示 channel 的注册信息
  > 4. SelectionKey.attach() 对 selectionKey 关联对象
  > 5. isReadable() / isAcceptable() / isWriteable() 判断事件类型
  > 6. 事件类型：OP_ACCEPT、OP_READ、OP_WRITE、OP_CONNECT

- 手写 Reactor 模式的挑战

  > - 处理 OP_WRITE 事件：写忙时需要注册 OP_WRITE，写不忙时要注销 OP_WRITE
  > - 处理 byteBuffer
  > - 处理粘包
  > - 处理多个 Selector
  > - 配置业务处理参数



### Netty 核心概念

- Channel — SocketChannel
- EventLoop — Selector
- ChannelPipleine
- ChannelHandler
- Bootstrap 
- ByteBuf
- Codec — 编码、解码



### Event Loop

![image.png](https://i.loli.net/2021/08/16/U4qijMtSJchy35o.png)

- 异步任务执行的方法运行在当前的线程中
- processSelectedKeys 和 runAllTasks 中不能运行阻塞的代码



- Event Loop Group

![image.png](https://i.loli.net/2021/08/16/kI6eUAQc51RswCF.png)

- Channel & Pipeline
  - 对应关系

  ![image.png](https://i.loli.net/2021/08/16/A7qJTcFQjaDBvWS.png)

  - Channel 生命周期

    ![image.png](https://i.loli.net/2021/08/16/UCpLeX3vSlBMP9f.png)

  - 各个组件之间的对应关系

    > - EventLoopGroup 1 ——> n  EventLoop
    > - EventLoop 1——> 1 Thread
    > - EventLoop 1 ——> n Channel
    > - Channel 1 ——> 1 ChannelPipeline
    > - ChannelPipeline 1 ——> n ChannelHandler
    > - ChannelHandler 1 ——> n ChannelPipeline



### 源码分析

- Register：AbstractChannel.register0()
- Accept：ServerBootStrapAcceptor
- Read：NioByteUnsafe.read()
- Write：AbstractChannel.flush0()



### 粘包和拆包
