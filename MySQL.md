# MySQL

### Page—页

- `InnoDB` 为了减少与磁盘之间的交互次数，采用 Page 的数据结构作为磁盘和内存之间交互的基本单位

- 一般情况下，一个 Page 的大小为 16 KB

- `InnoDB` 为不同的目的设计了不同的页，比如：存放表空间头部信息的页；存放 undo 日志的页等。

- 页的数据结构如下图所示：

  ![page.png](https://i.loli.net/2021/08/14/967EzBwgVUFNafI.png)

- 记录（Row）头信息

  - 一条记录的格式

    ![record.png](https://i.loli.net/2021/08/14/jCWYNhSelJ6GyHA.png)

  - `row_id` 在未指定主键和唯一约束的列的情况下，`row_id` 将作为该记录的主键来保证数据的唯一性（对应主键列 6 字节）。

  - `trx_id`事务 ID（6字节）、`roller_pointer` （7字节）

  - `deleted_flag` 

    - 物理删除：delete 语句删除
    - 逻辑删除：0 ：未删除，1：已删除
    - 对于删除操作：
      1. 将 delete_flag 置为 1
      2. 把删除掉的记录，组成一个垃圾链表，提高空间的可重用

  - `min_rec_flag`

    - B+ 树每层非叶子节点中标识最小的目录项记录

  - `n_owner` 

    - 把一个页划分为若干个组，组内主键值最大的记录会保存该值，表示改组中存在的记录数

  - `heap_no`

    - 堆号：每条插入进来的记录都会分配一个堆号，从 heap_no = 2开始
    - `InnoDB` 默认`Infimum` 的 heap_no = 0，`Supermum` 的 heap_no = 1

  - record_type

    - 记录的类型：
      - 0：普通记录
      - 1：`B+` 树非叶子节点目录项记录
      - 2：`Infimum` 记录
      - 3：`Supremum` 记录

  - `next_record`

    - 下一条记录
    - 从当前记录真实数据到下一条 **记录的真实数据** 的开始位置地址

- `Page Directory`

  - 特点

    > 1. 按照主键从小到大依次排序
    > 2. 单向链表

  - 分组规则

    > 1. 对于 `Infimum`  记录，组里只能有一条记录，就是它自己
    > 2. 对于 `Supremum` 记录，组里只能有 1～8条记录
    > 3. 对于一般的其它记录，组里只能有 4～8 条数据

  - 分组步骤

    > 1. 首先，初始状态下，没有记录，因此一个新的数据页只有两个组，一个是 `Infimum`组，一条记录；另一个是 `Supremum` ，一条记录
    > 2. 插入数据时，首先向 `Supremum` 组插入数据，更新 `n_owner`
    > 3. 当 `Supremum` 组满后，进行拆分，一个组拆分成 (4 + 5) 条数据，申请新的槽位

- 删除记录的执行流程

  > 1. 首先，将 `deleted_flag` 置为 1，表示该记录在逻辑上已经被删除了
  > 2. 更新该记录的上一条记录的 `next_record` 指向下一条记录
  > 3. 更新该记录所在组内的 `n_owner`

- 插入记录的执行流程

  > 1. 如果当前组内存在之前逻辑删除的记录，即`deleted_flag` 置为 1，且此时在垃圾链表中没有被占用。
  >    1. 那么就会将当前的记录的 `deleted_flag` 置为 0, 同时将数据更新
  >    2. 将本身这条记录的 `next_record` 指向上一条记录的 `next_record`，再修改上一条记录的 `next_record` 指向当前记录；
  >    3. 更新当前组内的 `n_owner`
  > 2. 页满之后的插入操作
  >    1. 申请一个新的空白页
  >    2. 为了保证主键有序，`InnoDB` 会将记录进行排序，将相关的数据进行迁移
  >    3. 因此一般建议主键是自增的，可以有效避免数据在页之间的迁移

- 单页数据查询过程

  > 1. 通过二分查找到对应的槽位
  > 2. 遍历槽位内的单链表，找到相对应的数据

  

### 索引

- B 树与 B+ 树

  - 相同点

    1. 一个叶子节点可以存储多个元素
    2. 两者的叶子节点都是有序的
    3. 每个叶子节点到根节点的长度都相同

  - 不同点

    1. B+ 树的叶子节点带有指针，在 MySQL 的实现中则是双向指针，这样可以有效提高查找速度，比如说，对于 

       ```sql
       SELECT * FROM student WHERE id BETWEEN 5 AND 10
       ```

       可以极大地提高查找速度

    2. B+ 树的叶子节点带有非叶子节点的冗余节点

- 聚簇索引（主键）

  - `record_type` 为 1,表示该记录是一个目录项。记录通过 “主键 + 页号” 的方式存储对应的信息

- 二级索引（非主键）

  - 非叶子节点也是通过 “主键 + 页号” 的方式组成的
  - 叶子节点由 “索引列 + 主键” 组成
  - 查找数据时，通过主键再 “回表” 进行查询

- 联合索引（多列）

  - 最左原则，多列同时创建索引时，会优先按照左边的列建立索引
  - 因此只有最左边的列在查询时才能享受到索引带来的性能的提升
  - 对于非最左列的查询不会建立索引（因为首先按照最左列已经创建了索引），因此使用非最左列进行查询时将会带来巨大的性能损失

- 目录项记录的唯一性

  - 每个目录项对于二级索引，按照 “索引列的值 + 主键值 + 页号” 来作为目录项记录的唯一标识



### Buffer Pool（缓冲池）

- 概述

  ![bufferPool.png](https://i.loli.net/2021/08/14/wcjfz6Sqox32eid.png)

  - 每个控制块一一对应这缓冲页
  - 整个区间的内存是连续的
  - `innodb_buffer_pool_size` 不包含控制块区域的大小，因此一般情况下申请的空间会大于 `innodb_buffer_pool_size` 指定的大小
  - `innodb_buffer_poll_size` 默认大小为 128 MB
  - 通过 Hash 表来判断数据库中的页是否加载到缓冲区，其中，Hash 表的 key 为 “表空间 + 页号”，value 为缓冲区中的控制块

- free 链表（空闲链表）

  ![freeList.png](https://i.loli.net/2021/08/14/3lvwdeyMxpQakIS.png)

  - 遍历 free 链表，填充对应的缓冲页，移除该元素

- flush 链表![flush.png](https://i.loli.net/2021/08/14/WzFd2IPLtSRV6lo.png)

  - 修改后的缓冲页（脏页）放入该链表，在某一时刻将数据刷到磁盘上

    > 1. 从 flush 链表中刷新一部分页面到磁盘，这是通过后台线程来实现的，
    >    - 根据系统的繁忙程度来确定刷新的速度（BUFFER_FLUSH_LIST）。
    >    - 系统很繁忙的情况下，会使得刷新脏页到磁盘的速度会很慢，可能会导致这么一种情况：当读取不在缓冲区中的数据页时，由于当前缓冲区的所有页都是脏页，将会导致无法将磁盘中的页读到缓冲区中。在这种情况下，将会去查看 `LRU` 链表的尾部，检测是否存在可以直接释放的未修改的缓冲页；如果没有，将不得不将 `LRU` 链表尾部的一个脏页同步刷新到磁盘中。（BUFFER_FLUSH_SINGLE_PAGE）
    > 2. 从 `LRU` 链表的了冷数据汇总刷新一部分页到磁盘（BUFFER_FLUSH_LRU）

- LRU 链表（Last Recently Used）

  <img src="https://i.loli.net/2021/08/14/9muDVjxXisqcJ8y.png" alt="LRU.png" style="zoom:200%;" />

  - 在访问某个在缓冲区的页时，将它作为最近使用的页，放到 LRU 列表的头部，此时这个页是一个热数据。

  - 使用热数据可以提高命中率，从而提高数据的访问速度。

  - 存在的问题

    1. 预读问题

       > - `InnoDB` 认为对于当前页的读取，对于当前读取页之后的页，认为在查询该页周围的页，也是你将要读取的页。因此它会把当前读取页周围的一些页一并加载到缓冲池中，导致一部分缓冲池内的页失效。
       > - 预读分为两种：线性预读和随机预读
       >   - 线性预读：表空间—> 区（64个页） —> 页，当线性访问超过一定的阈值时，就会执行线性预读
       >   - 随机预读：默认不开启，只要连续读取超过一定阈值，就会读取整个区的页。
       > - 解决方案
       >   - 当页从磁盘中加载时，`InnoDB`会将加载的页放入冷数据区域。通过适当增大冷数据区的大小可以有效解决该问题。
       >   - 设置冷数据区比例的参数：`innodb_old_blocks_pct`（默认 37）

    2. 全表查询导致缓冲页失效

       > - 由于未加上查询条件，或者未命中索引，将会导致全表扫描，直接将整个表的页全部读取到缓冲池中，使得整个缓冲池中的页失效
       > - 解决方案
       >   - 在从磁盘加载页到冷数据区之后，通过设置一个时间阈值，只有在该页上访问超过这个时间阈值时才能进入热数据区。
       >   - 设置时间阈值的参数：`innodb_old_blocks_time` （默认为 1000 ms）

- Chunk 和 Buffer Pool

  - 由于多个线程的访问，可能会由于加锁的原因导致性能的下降，因此可以将 Buffer Pool 设置为多个实例
  - Chunk ：细化 Buffer Pool，优化 Buffer Pool（因为较小连续的内存空间要比较大的连续内存空间更加容易分配）



### redo 日志

- 在 Buffer Pool中修改了页，如果在将 Buffer Pool 中的内容冲洗到磁盘上的这一过程出现了问题，导致内存中的数据失效，那么这个已经提交的事务在数据库中所做的修改就丢失了。这时需要通过 redo 日志文件来恢复

- redo 简单日志类型

  <img src="https://i.loli.net/2021/08/14/t1ksBbZqrLh2M7Y.png" alt="image.png" style="zoom:80%;" />

  - MLOG_1BYTE（type = 1）

  - MLOG_2BYTE（type = 2）

  - MLOG_4BYTE（type = 4）

  - MLOG_8BYTE（type = 8）

    

    <img src="https://i.loli.net/2021/08/14/XSjpTJYBD5wRAiz.png" alt="image.png" style="zoom:80%;" />

  - MLOG_WRITE_STRING（type = 30）

- redo 复杂日志类型

  ​	<img src="https://i.loli.net/2021/08/14/xCEgv8XVjc1nTMo.png" alt="image.png" style="zoom:80%;" />

  - 一般 SQL 执行时使用的都是复杂日志类型
  - 类型
    - MLOG_REC_INSERT（type = 9）（非紧凑型）
    - MLOG_COMP_REC_INSERT（type = 38）（紧凑型）
    - MLOG_COMP_PAGE_CREATE（type = 58）
    - MLOG_COMP_REC_DELETE（type = 42）
    - MLOG_COMP_LIST_START_DELETE（type = 44）
    - MLOG_COMP_LIST_END_DELETE（type = 43）

- redo 日志组

  - 日志组，保证事务的一致性

    ![image.png](https://i.loli.net/2021/08/15/H2BimIJdn6abPfk.png)

    通过 `MLOG_MULTI_REC_CORD` 判断 redo 日志的组别。通过组来执行 redo 日志，从而保证某个事务的原子性。

  - 日志组内 redo 日志的类型属性

    ![image.png](https://i.loli.net/2021/08/15/ySt6BW3pDPoUVJv.png)

    - flag 位为 1，表示该 redo 日志是一个单条原子性的操作，为 0 则表示一般日志
    - 通过该 flag 位，同样可以保证事务的一致性，因此当该 flag 位为 1 时，它将不属于一个 redo 日志组

- MTR （Mini Transaction）

  - 对底层的页进行一次原子访问的过程被称为一个 MTR，与 redo 日志组类似
  - 一个事务可以包含多个 SQL 语句；一条 SQL 语句可以包含多个 MTR；一个 MTR 包含多个 redo 日志。

- redo log block

  - redo log block 结构

    <img src="https://i.loli.net/2021/08/15/WQSpoBPslT7h6Iq.png" alt="image.png" style="zoom:80%;" />

- redo 日志缓冲区（log buffer）

  - 内冲中多个连续的 redo log block （连续的内存空间）

  - 对于多个事务，由于不同的事务之间有可能是并发执行的，因此多个事务的 MTR 可能是交替执行的

  - 对于一般的的事务，它的执行过程一般如下

    > 1. 执行相关的 SQL 语句
    > 2. 得到相关的 redo 日志
    > 3. 根据 redo 日志的原子性，将它们聚集到 MTR 的维度
    > 4. 最后将 MTR 写到 redo log block 中（此时在内存）

- redo 日志刷盘和日志文件组

  - 刷盘的条件

    > 1. log buffer 中可用空间不足 50% 的情况下
    > 2. 事务提交的时候
    > 3. 后台的线程每隔一定时间间隔将 redo buffer 中的 redo log 写入磁盘中
    > 4. 正常关闭服务器时
    > 5. checkout 时

  - 日志文件组

    ![image.png](https://i.loli.net/2021/08/15/tvPW1d2Cm6wVcN7.png)

    - MySQL 的数据目录文件中，默认有 lib_logfile2 和 lib_logfile1，可以通过 `innodb_log_files_in_group` 进行设置
    - 当一个 log file 满了之后，将会再将之后的内容写到下一个文件；当最后一个文件满了之后，将会回到第一个 log file

- redo 日志文件格式

  <img src="https://i.loli.net/2021/08/15/cXqnRZiTBusfUek.png" alt="image.png" style="zoom:150%;" />

  - LSN（log sequence number）
    - 用于记录当前 redo 日志文件已经写入的总量
    - 初始值为 8704。而 `fileOffset` 为 2048，即 redo 日志开始写入的偏移位置



### undo log

> 由于事务需要保证原子性，然而有时由于异常的原因导致事务需要回滚，因此此时需要解决一致性的问题，此时就需要 undo log 来完成。
>
> 对于一般的读数据操作，不会记录相应的 undo log

- 事务 id（与 row_id 类似）

- INSERT 对应的 undo log

  - 日志结构

    ![image.png](https://i.loli.net/2021/08/15/9sbnD2Ig6Oq8m5K.png)

    - 主键各列信息：以 <len, value> 组成的映射关系，其中 len 表示主键字段类型的长度，value 表示实际值

- DELETE 对应的 undo log

  - 日志结构

    <img src="https://i.loli.net/2021/08/15/PHLaf9rYSgBpz2Q.png" alt="image.png" style="zoom:200%;" />

    - info bits：记录头信息比特位
    - index_col_info：索引列信息综合（主键索引、二级索引）
    - 索引的各列信息：pos 表示在记录中相对于真实记录数据的开始位置，比如，`trx_id` 为 1，`roller_pointer` 为 2

  - 删除流程

    - 初始阶段：PAGE_FREE 块指向垃圾链表，此时Page 中没有逻辑上删除的记录
    - 标记阶段：将对应的记录的 `delete_flag` 置为 1 ，此记录在逻辑上已经被删除，但并未进入垃圾链表
    - 清除过程：修改 Page 中对应记录的父节点以及其对应的 `next_record`，同时将该记录放入垃圾链表

- UPDATE 对应的 undo log

  - 日志结构

  ![image.png](https://i.loli.net/2021/08/15/q2BvDce8jWxYETR.png)



### 事务

- ACID

  > 1. 原子性（Atomicity）：对于一个事务来讲，要么全部执行，要么全部不执行
  > 2. 一致性（Consitency）：在事务开始前和开始后，数据库的完整性没有被破坏
  > 3. 隔离性（Isolation）：多个事务的处理互相独立，互不干扰
  > 4. 持久性（Durability）：事务结束后，对数据的修改是永久的

- 事务并发执行时可能带来的问题

  - 脏读

    > 一个事务读取了另一个事务未提交的数据

  - 脏写

    > 一个事务对于数据的修改覆盖了另一个事务对于此数据的写

  - 不可重复读

    > 一个事务修改了另一个未提交的事务读取的数据

  - 幻读

    > 当一个事务涉及修改表中的全部字段时，另一个事务修改了表内的数据，使得第一个事务发现还存在没有修改的数据行，就好像发生了幻觉一样

- 事务隔离级别

  | 隔离级别 |   脏读   | 不可重复读 |  幻影读  |
  | :------: | :------: | :--------: | :------: |
  | 未提交读 | 可能发生 |  可能发生  | 可能发生 |
  |  提交读  |    -     |  可能发生  | 可能发生 |
  | 可重复读 |    -     |     -      | 可能发生 |
  | 可序列化 |    -     |     -      |    -     |

- MVCC（Mut- Version Concurency Controller 多版本并发控制）

  > 利用记录的版本链和 ReadView 来控制并发事务访问相同记录时的行为

  - ReadView 

    > 一致性视图，用来判断版本链中哪个版本是当前事务可见的

    - m_ids：生成 ReadView 时，当前系统中活跃的读写事务
    - min_trx_id：在生成 ReadView 时，当前系统中活跃的事务中最小的事务 id
    - max_trx_id：在生成 ReadView 时，系统应当分配给下一事务的 id
    - creator_trx_id：生成该 ReadView 的事务的事务 id

  - 每次更新该记录后，都会将旧值放到一条 undo 日志中。随着更新次数的增多，所有的版本都会i被 `roll_pointer` 属性链接成一条链表，被称为版本链

  - ReadView 判断版本的可见性

    > 1. 如果 trx_id == creator_id， 说明当前事务修改的是自己的修改记录，所以当前版本可以被该事务访问
    > 2. 如果 trx_id < min_trx_id，则说明生成该版本的事务在当前事务生成 ReadView 之前就已经被提交了，所以当前版本可以被当前事务访问
    > 3. 如果 trx_id >= max_trx_id。则说明生成该版本的事务在当前事务生成 ReadView 之后才开始的，，所以该版本对于当前事务来讲不可以访问
    > 4. 如果 trx_id in m_ids，则说明创建 ReadView 时生成的该版本的事务还是活跃的，该版本不可以被当前事务访问
    > 5. 如果 trx_id not in m_ids，则说明创建 ReadView 时生成该版本的事务已经被提交，该版本可以被访问
    > 6. 如果某个版本的数据对当前事务不可见，那么将会顺着版本链找到下一个版本，并继续执行上面的步骤判断可见性，直到找到版本链中的最后一个版本

  - 提交读和可重复读的最大区别在于 ReadView 的生成时机不同

    > 对于提交读来讲，每次读取数据前都会生成一个 ReadView
    >
    > 对于可重复读来讲，在同一个事务中，只有在第一次读取数据时生成一个 ReadView


