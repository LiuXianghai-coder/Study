# 类加载流程

<img src="https://i.loli.net/2021/07/01/FsdBCDzJSf5U7ux.png" alt="image.png" style="zoom:150%;" />

1. 主动加载类的方式
   - `new`  关键字，会检测是否已经加载该类，如果没有加载，则加载该类。
   - 反射、Clone
   - 初始化子类会自动初始化父类
   - 调用一个静态方法
2. 类加载过程
   - 加载（Loader）
     - 通过类的全限定名，获取类的二进制数据
     - 解析流，将类的信息放入方法区
     - 创建该类的 `Class` 实例
   - 验证
     - 验证加载的字节码是否合法（格式、语义等）
   - 准备
     - `JVM` 为该类分配相应的内存空间
   - 解析
     - 将符号引用转换为直接引用
   - 初始化
     - 类初始化，生成 `Class` 对象



# `JMM`

### 程序计数器

- 内存空间较小
- 一般非 `native` 方法，程序计数器相当于一个行号指示器；对于 `native` 方法，程序计数器为空
- 内存为线程私有
- 唯一一个不会发生 `OutOfMemoryError` 情况的区域

### 本地方法栈

- 为 `native` 方法服务

### 虚拟机栈

- 为线程私有
- 描述方法执行的内存模型（栈帧）

### 方法区/永久代/元空间

- 为线程共有
- 方法区
  - 逻辑上的概念
  - 存储类的信息、常量、方法数据、JIT 编译后的代码等
- 永久代（JDK 7及之前）
  - 加载类信息和元数据信息
  - 如果空间被加载的类数据占满，会引发 `OO ` 异常
- 元空间（JDK 8 及之后）
  - 处于取代永久代的目的
  - 直接使用本地的内存

### 堆

- 线程公有
- 创建对象或数组保存的区域
- 垃圾收集的区域
- 新生代（Eden、Survivor）与老生代

# 垃圾回收

1. 标记垃圾对象

   - 可触及性：确定一个对象是否可以被回收。从根节点开始，访问某个对象，如果能够访问到，则说明这个对象是可用的。

2. 引用级别

   - 强引用

     > 一般程序中使用的引用

   - 软引用

     > 堆空间不足时，回收该引用的实例对象

   - 弱引用

     > 只是给予实例对象一次存活的机会，发生 `GC` 时会回收该实例对象

   - 虚拟用

3. 槽位复用

# 对象的分配

### 总体流程

​										

<img src="https://i.loli.net/2021/07/02/XbHD6rysipZKnlQ.png" alt="image.png" style="zoom:120%;" />

### 栈上分配

- 栈上分配

  - 逃逸分析

    分析 `Java` 对象的动态作用域是否在方法体之外。

    ```java
    Student stu = new Student(); // 此对象即为逃逸对象，无法栈上分配
    public void test() {
        Systen.out.println();
    }
    ```

  - 同步省略

    > ​	在 `JIT` 编译过程中，如果发现一个对象不会被多线程访问，那么针对这个对象的同步策略即可省略掉，即锁消除。如 `Vector` 和 `StringBuffer` 这样的类，大部分方法都会带有锁，当某个对象确定是线程安全的情况下， `JIT` 会在编译这段代码的时候进行锁消除来提升效率。

  - 标量替换

    > 1. 标量
    >    - 不可进一步分解的量。（基本数据类型：int、long等）
    > 2. 聚合量
    >    - 可以被进一步分解的量
    > 3. 替换
    >    - 通过逃逸分析，确定这个对象不会被外部访问
    >    - 对这个对象进行分解（若干个变量替代，用若干个标量替换聚合量）

### 对象分配的方式

- 指针碰撞（`Serial`、`ParNew`等带 `Compact` 过程的收集器）

  > ​	假设Java堆中内存是绝对规整的，所有用过的内存都放在一边，空闲的内存放在另一边，中间放着一个指针作为分界点的指示器，那所分配内存就仅仅是把那个指针向空闲空间那边挪动一段与对象大小相等的距离，这种分配方式称为“指针碰撞”（Bump the Pointer）

- 空闲列表

  > ​	如果Java堆中的内存并不是规整的，已使用的内存和空闲的内存相互交错，那就没有办法简单地进行指针碰撞了，虚拟机就必须维护一个列表，记录上哪些内存块是可用的，在分配的时候从列表中找到一块足够大的空间划分给对象实例，并更新列表上的记录，这种分配方式称为“空闲列表”（Free List）。



### `TLAB` 分配

> Thread Local Allocated Buffer
>
> 作用：避免多线程之间的空间分配的冲突。
>
> 缺点：空间较小，无法容纳大的对象。



### 堆上的分配

> 主要的对象分配区域



### 是否能进入老年代的条件

> 1. 是否实例对象所占空间过大，当新生代无法容纳该大对象时，直接放入老年代
> 2. 对象头内部有一个 4 bit 的分代年龄，当分代年龄达到阈值时进入老年代（`-XX:MaxTenuringThreshold` 参数设置）
> 3. survivor 区空间不足 

# 垃圾回收算法

### 标记清除算法

> 1. 标记垃圾节点
> 2. 清除所有垃圾节点，不做任何额外操作
>
> 优点：清理速度较快
>
> 缺点：容易造成内存碎片化

### 标记复制算法

> 1. 将原有内存分为两块，每次只使用其中一块内存
> 2. `GC` 时将存活的对象复制到另一空白内存，同时清空整个当前使用的内存
>
> 优点：速度快、无内存碎片
>
> 缺点：降低了系统的整个实际可用空间

### 标记整理算法

> 1. 标记所有的存活节点
> 2. 将所有的存活节点压缩到内存的另一端
> 3. 清除所有的其它节点
>
> 优点：不会产生内存碎片、可利用内存空间足
>
> 缺点：需要耗费更多的时间（主要使用在老年代）

### 分代算法

> 将堆空间划分为新生代和老年代，根据不同的特点，选择不同的垃圾收集算法。
>
> <img src="https://i.loli.net/2021/07/02/JKm2WfBqPHMv8hw.png" alt="image.png" style="zoom:50%;" />

### 分区算法

> ​	将对空间划分为连续的不同小区间，每个区间独立使用、回收。这样就可以使得 `GC` 优先选择垃圾比重较大的区域，从而总体上减少`GC` 造成的停顿时间

# 垃圾收集器

![image.png](https://i.loli.net/2021/07/02/VqvwsPHyQUKIe3W.png)

### 串行回收器（Serial）

- 特点

  - 只使用单线陈进行 `GC`
  - 独占式的 `GC`

- 串行收集器是 `JVM Client` 模式下默认的垃圾收集器

  ![image.png](https://i.loli.net/2021/07/02/h4w9zxEIimQC5HK.png)

- 总体流程

  <img src="https://i.loli.net/2021/07/02/oqJaLXyrFbn1kMz.png" alt="image.png" style="zoom:45%;" />

### 并行回收器（`ParNew`、`ParallelGC`、`ParallelOldGC`）

- 特点

  - 将串行回收器多线程化
  - 与串行回收器有相同的回收策略、算法、参数

- 参数配置

  <img src="https://i.loli.net/2021/07/02/UHOKq8NPeQC2MFR.png" alt="image.png" style="zoom:80%;" />

- 总体流程

  <img src="https://i.loli.net/2021/07/02/dAvhc486PXTGUFD.png" alt="image.png" style="zoom:50%;" />

### 并行回收器（`CMS`）

- 总体流程

  <img src="https://i.loli.net/2021/07/02/LY8Piaz6j3pGDF5.png" alt="image-20210702114302888.png" style="zoom:150%;" />

- 优缺点

  > 优点：并发收集，低停顿
  >
  > 缺点：对 CPU 资源非常敏感；无法处理浮动垃圾（并发执行时有应用进程产生）；标记清除会产生垃圾碎片

### `G1` 垃圾收集器

- 执行流程

  <img src="https://i.loli.net/2021/07/02/8x7Cu1vArltDENH.png" alt="image.png" style="zoom:80%;" />

- `G1`  堆区结构

  ![image.png](https://i.loli.net/2021/07/02/ABYoU7PcnZ2h8Km.png)

  H 代表 Humongous，表示这些 Region 存储的是巨大对象（H-Obj）（大小大于 Region 的对象）。

  H-Obj 的特征：

  - H-Obj 直接分配到 Old Region，防止反复拷贝和移动。
  - H-Obj 在 global concurrent marking 阶段的 clean up 和 `Full GC ` 阶段回收
  - 在分配 H-Obj 之前会检查是否超过 initiating  heap occupancy percent 和 the marking threshold，如果超过，则启动 global concurrent marking，为的是提早回收，防止 evacuation 和 Full GC 

  为了减少连续 H-Obj 分配对 GC 的影响，需要把大对象转换为普通的对象，同时建议增大 Region Size

  一个 Region 的大小可以通过参数 `-XX:G1HeapRegionSize` 指定，取值范围为 1 M 到 32 M，如果未设置该值，则 `G1` 会根据 Heap 大小自动指定。

- `SATB`（Snapshot—At—The—Beginning）

  - GC 开始时活着的一个快照，通过 Root Tracing 得到，作用是维护并发 GC 的正确性。

  - 三色标记

    > ​	三色标记屏蔽了 GC 的实现算法、遍历策略等细节。具体来说在 GC 遍历引用关系图时，对象会被标记为三种颜色：
    >
    > - 黑色（Black）：表明对象被 Collector 访问过，属于可到达对象。
    > - 灰色（gray）：也表明对象被引用过，但是它的子节点还没有被扫描到。
    > - 白色（White）：表明没有被访问到，如果在本轮遍历结束时还是白色，那么就会收回。
    >
    > 
    >
    > ​	增加的中间状态灰色要求 mutator 不会把黑色对象直接指向白色对象（这称为三色不变性 tri-color invariant），collector 就能够认为黑色对象不需要在 scan，只需要遍历灰色对象即可。
    >
    > 
    >
    > 如果要使 Collector 错误地回收可到达对象，Mutator 必须做到以下两点（缺一不可）
    >
    > 1. 把白色对象的指针存储在一个黑色对象中，称为条件 1.
    > 2. 在 Collector 访问该白色对象之前，破坏它指向它的原有指针，称为条件 2.
    >
    > 因此如果想要解决漏标的问题，可以从以上两个条件入手
    >
    > 1. 读屏障（read barrier），它会立刻禁止 mutator 访问白色对象，当检测到 mutator 即将访问白色对象时， collector 会立刻访问该对象并将该对象标记为灰色。当 mutator 不能访问指向白色对象的指针，也就无法使黑色对象指向它们了。
    >
    >    - Incremental Copying（IC，增量式复制），使用宽度优先搜索的方式遍历关系图，把所有引用的对象拷贝到 `topspace`  的scan 指针处。但是这个过程是与 mutator 并行的，而且 mutator 为了保证引用关系的一致性，也可能会触发拷贝操作。具体流程如下所示：
    >
    >      1.  GC 开始时会有一个原子性的 flip 过程，会把由 root set 可直接到达的对象由 `fromspace` 拷贝到 `topspace`
    >      2. mutator 恢复执行，与 collector 交替执行。
    >      3. 当 mutator 访问到 `fromspace` 中的对象时，立即拷贝到 `topspace`中，这个 copy—on—demand 使用 read—barrier来保证。
    >
    >      IC 的一个重要特点是：在增量回收时，新分配的对象直接分配在 `topspace`，也就是三色标记中的黑色。为了保证 GC 能在内存耗尽之前发现所有可到达对象并复制到 `topspace`，复制的速率与分配对象的速率息息相关。
    >
    >    - Non—Copying—Treadmill，对 IC 的一种改进，称为 Treadmill。Treadmill 使用双向链表来区别不同颜色的对象的集合，这样就可以通过修改指针来避免移动对象与更新指针的操作，不同集合首尾相连形成环形结构，便于对象的转换。
    >
    >      <img src="https://i.loli.net/2021/07/02/KL435FPeO8zxj2T.png" alt="image.png" style="zoom:100%;" />
    >
    >      该环结构分为四个区域：
    >
    >      1. new 区。在 GC 期间的对象分配在这里，默认为黑色。在 GC 开始时，该区为空。
    >      2. from 区。对应 `fromspace`，GC 开始前对象分配区域。
    >      3. to 区。对应 `tospace`。在 GC 开始时，该区为空。
    >      4. free 区。与 new、from 区相连，别于分配新对象
    >
    >      
    >
    > ​		GC 工作过程与之前方式相似，再将 from 区对象连接到 to 区后，遍历 to 区里面的灰色对象，直到全部为黑色时GC结束。然后，new 与 to 合并后形成新 to 区，from 与 free 合并形成新的 free 区。
    >
    >    
    >
    > 2. 写屏障（write barrier ），他会记录 mutator 新增的由黑色—> 白色对象的指针，并把该对象标记为灰色，这样 Collector 就又能访问有问题的对象了。
    >
    >    - Incremental Update（IU 增量式更新）算法，避免条件 1 的产生。IU 最早由 Dijkstra 提出，该算法的核心
    >
    >      > 它会启发式（或者保守式）保留在 GC 遍历	结束时 live 对象。在遍历期死亡的对象（该对象还没有被遍历到），不会再被访问、标记。
    >
    >      为了避免指向白色对象的指针被隐藏在黑色对象中，这些指针在存储到黑色对象中时会被捕捉到，这时会把白色对象重新置为灰色，这个过程会一直迭代下去，直到没有灰色对象为止。
    >
    >      新创建的对象在 Dijkstra 算法中会被乐观的认为是白色，这是该算法的一大优势，因为大多数对象的生命周期都比较短。如果在 GC 遍历到它们之前就已经不可到达，这就意味着它们永远不用访问了。
    >
    >    - Snapshot At Beginning（SAB）避免条件 2 的产生。该算法在开始 GC 之前，会使用  copy—on—write 的方式复制一份当时的引用关系图。也就是说，在 GC 开始时引用关系图是固定的。因此，即使之后的黑色指针指向了白色指针，也会查找到该白色节点。
    >
    > 3. 一般来讲，写屏障的效率要高于读屏障，主要原因是因为 Heap 指针的读操作要多于写操作。

  - 漏标分析

    > - 对于漏标条件，首先分析条件一。如果该白对象是 `new` 出来的，由于 Region 中存在两个 top—at—mark—start（TAMS）指针，分别为 `prevTAMS` 和 `nextTAMS`，在 TAMS上的对象是新分配的，这是一种隐形的标记，因此不会出现漏标。
    > - 对于 GC 时已经存在的白对象，如果它还活着，那么它必然会被一个灰对象引用。如果该灰对象到白对象的直接引用或者间接引用被替换、删除了，白对象就会漏标。
    >
    > 因此 `SATB` 可能会导致漏标，此时可以通过写屏障记录下来。
    >
    > 此外，如果被替换的白对象就是要被收集的垃圾，这次的标记会让它躲过GC，这就是float garbage。因为`SATB`的做法精度比较低，所以造成的float garbage也会比较多。

- `RSet`（Remembered Set）

  > - 辅助 GC 的一种结构，记录 GC 要收集的 Region 集合，集合里的 Region 可以是任意年代的。在 GC 时，对于 old —> young 和 young —> old 的跨代对象引用，只要扫描对应的 `CSet`（Collection Set）中的 `RSet` 即可。
  >
  > - 逻辑上讲每个 Region 都有一个 `RSet`，`RSet` 记录了其他 Region 中的对象引用本 Region 中对象的关系，属于 point-into结构（谁引用了我）。
  >
  >   
  >
  > - `G1` 的 `RSet` 是在Card Table 的基础上实现的，每个 Region 会记录别的 Region 有指向自己的指针，并标记这些指针分别在哪些 Card 范围内。这个 `RSet` 其实是一个Hash Table，Key是别的Region的起始地址，Value是一个集合，里面的元素是Card Table的Index。
  >
  >   ![image.png](https://i.loli.net/2021/07/02/LamAjhyD3F4TJpK.png)
  >
  >   ​	每个Region被分成了多个Card，在不同Region中的Card会相互引用，Region 1 中的Card中的对象引用了 Region 2 中的Card中的对象，蓝色实线表示的就是points-out的关系，而在Region 2 的 `RSet` 中，记录了 Region 1 的Card，即红色虚线表示的关系，这就是points-into。
  >
  >   ​	维系 `RSet` 中的引用关系靠 post-write barrier 和 Concurrent refinement threads来维护。post-write barrier记录了跨Region的引用更新，更新日志缓冲区则记录了那些包含更新引用的Cards。一旦缓冲区满了，Post-write barrier就停止服务了，会由Concurrent refinement threads处理这些缓冲区日志。
  >
  > - `RSet` 辅助 GC 的流程
  >
  >   - 在做 Young GC 的时候，只需要选定young generation region的`RSet` 作为根集，这些 `RSet` 记录了old->young的跨代引用，避免了扫描整个old generation。 而 Mixed GC 的时候，old generation中记录了old->old的 `RSet`，young->old的引用由扫描全部 young generation region 得到，这样也不用扫描全部old generation region。

- 停顿预测模型（Pause Prediction Model）

  > - 通过停顿预测模型设置满足用户的定义的停顿时间目标，并根据指定的停顿时间选择要收集的 Region 数量。
  >
  > - 参数 `-XX:MaxGCPauseMillis` 指定停顿时间，默认值是 200 ms。
  >
  > - 其它一些相关参数
  >
  >   <table>
  >       <thead>
  >           <tr>
  >               <th>参数</th>
  >               <th>含义</th>
  >           </tr>
  >       </thead>
  >       <tbody>
  >           <tr>
  >               <td>-XX:G1HeapWastePercent</td>
  >               <td>在global concurrent marking结束之后，我们可以知道old gen regions中有多少空间要被回收，在每次YGC之后和再次发生Mixed GC之前，会检查垃圾占比是否达到此参数，只有达到了，下次才会发生Mixed GC</td>
  >           </tr>
  >           <tr>
  >               <td>-XX:G1MixedGCLiveThresholdPercent</td>
  >               <td>old generation region中的存活对象的占比，只有在此参数之下，才会被选入CSet</td>
  >           </tr>
  >           <tr>
  >               <td>-XX:G1OldCSetRegionThresholdPercent</td>
  >               <td>一次Mixed GC中能被选入CSet的最多old generation region数量</td>
  >           </tr>
  >           <tr>
  >               <td>-XX:G1MixedGCCountTarget</td>
  >               <td>一次global concurrent marking之后，最多执行Mixed GC的次数</td>
  >           </tr>
  >           <tr>
  >               <td>-XX:G1HeapRegionSize=n</td>
  >               <td>设置Region大小，并非最终值</td>
  >           </tr>
  >           <tr>
  >               <td>-XX: G1OldCSetRegionThresholdPercent</td>
  >               <td>一次Mixed GC中能被选入CSet的最多old generation region数量</td>
  >           </tr>
  >           <tr>
  >               <td>-XX:MaxGCPauseMillis</td>
  >               <td>设置G1收集过程目标时间，默认值200ms，不是硬性条件</td>
  >           </tr>
  >           <tr>
  >               <td>-XX:G1NewSizePercent</td>
  >               <td>新生代最小值，默认值5%</td>
  >           </tr>
  >           <tr>
  >               <td>-XX:G1MaxNewSizePercent</td>
  >               <td>新生代最大值，默认值60%</td>
  >           </tr>
  >           <tr>
  >               <td>-XX:ParallelGCThreads</td>
  >               <td>STW期间，并行GC线程数</td>
  >           </tr>
  >           <tr>
  >               <td>-XX:ConcGCThreads=n</td>
  >               <td>并发标记阶段，并行执行的线程数</td>
  >           </tr>
  >           <tr>
  >               <td>-XX:InitiatingHeapOccupancyPercent</td>
  >               <td>设置触发标记周期的 Java 堆占用率阈值。默认值是45%。这里的java堆占比指的是non_young_capacity_bytes，包括old+humongous</td>
  >           </tr>
  >       </tbody>
  >   </table>

- `G1` `GC` 流程

  - `Young GC`

    > 选定所有年轻代里的 Region 个数，即年轻代内存大小，来控制 `Young GC` 的开销

    

  - global concurrent marking 

    - 初始标记（initial mark，`STW`）

      > 标记从 `GC root` 开始直接可达的对象

    - 并发标记（Concurrent Marking）

      > ​	从 `GC Root` 开始对 Heap 中的对象进行标记，标记线程与应用程序线程并行执行，并且收集各个 Region 的存活信息。

    - 最终标记（Remark，`STW`）

      > ​	标记那些在并发标记阶段发生变化的对象

    - 清除垃圾

      > 清除空的 Region（没有存活对象），加入 Free List。

  

  - `MixedGC`

    > ​	选定所有年轻代里的 Region，外加根据 global concurrent marking 统计得出收益高的若干老年代 Region。在用户指定的开销目标范围内尽可能地选择收益高的老年代 Region。

  

  -  `G1` 不提供 `Full GC`，当 `Mixed GC` 无法跟上程序分配内存额速度，导致老年代填满无法继续进行 `Mixed GC`，就会使用 `Serial Old GC`  （`Full GC`） 来回收整个堆区。



> `G1` 相比较于 `CMS`，有以下不同：
>
> 1. `G1` 的垃圾回收器基于标记—整理算法，因此得到的空间时连续的，避免了 `CMS` 由于不连续空间造成的问题。
> 2. `G1` 的内存结构与 `CMS` 有很大不同，`G1` 将 内存划分为固定大小的 Region （2 的整次幂），内存的回收以 Region 为单位。
> 3. `G1` 的 `STW` 可控，`G1` 在停顿时间上添加了预测机制，可以指定期望停顿时间。