# Redis

> ​	Redis是开源(BSD许可)的,数据结构存储于内存中,被用来作为数据库,缓存和消息代理。
> 它支持多种数据结构,例如字符串(string),哈希(hash),列表(list),集合(set),带范围查询的排序集合(zset),位图(bitmap),hyperloglog,带有半径查询和流的地理空间索引。 Redis具有内置的复制,Lua脚本,LRU逐出,事务和不同级别的磁盘持久性,并通过Redis Sentinel和Redis Cluster自动分区提供高可用性。



# 数据类型

### 存储类型：

![image.png](https://i.loli.net/2021/07/02/BZmQiEhIodklGOY.png)

### 编码关系：

<img src="https://i.loli.net/2021/07/02/QLGi5lcCeqTbspN.png" alt="image.png" style="zoom:150%;" />

### 对应关系：

![image.png](https://i.loli.net/2021/07/03/YzWusopBVlSmdx1.png)

### 数据类型

- String（字符串）

  - key 都是 String 类型

  - 可扩展性高

  - 底层存储是字符数组

  - 编码关系

    - `REDIS_ENCODING_INT`（int），`long`类型的整数，`encoding` 为 `int`

      设置为 `long` 类型的整数，那么 `encoding` 为 `int`。

      ![2021-07-03 08-57-02 的屏幕截图.png](https://i.loli.net/2021/07/03/kzIamliJnFEqY9d.png)

    - `REDIS_ENCODING_RAW`（raw），简单动态字符串

      <img src="https://i.loli.net/2021/07/03/1yjLldZYJXnmfQW.png" alt="2021-07-03 08-58-24 的屏幕截图.png" style="zoom:150%;" />

    - `REDIS_ENCODING_EMBSTR`（embstr），一般字符串

      ![image.png](https://i.loli.net/2021/07/03/uFrPsBoTbmOqMJy.png)

      1. 当保存的字符串的长度小于某个阈值时，使用 `embstr`，专门用于保存短字符串的一种方式。
      2. 相比较于 `Raw`，`embstr` 只需要通过一次内存分配函数来分配一块连续的空间，包含 `redisObject`、`sdshdr`；而 `raw`会调用两次 内存分配函数创建 `redisObject`和 `sdshdr`。
      3. `embstr` 是只读的，因此如果对它进行修改，将会首先转换为 `row`，再执行修改。

    - `SDS`

      > Simple Dynamic String 简单动态字符串
      >
      > 
      >
      > 1. 使用结构体成员变量得到字符串信息。
      > 2. `SDS`动态执行空间的扩充，API 自动进行空间的扩展
      > 3. 空间预分配
      >    * 如果对 `SDS` 进行修改后，`SDS` 长度 < 1 M，此时分配的 len = free
      >    * 如果对 `SDS` 进行修改后，`SDS` 长度 >= 1 M，只会按照 1 M 去分配。
      > 4. 惰性释放
      >    - 删除字符时，不即时释放，保留原有空间。
      > 5. `SDS` 通过 `len` 判断字符串是否已满，可以保存任意数据。

- Set（集合）

  - 元素无序，但是当元素为数字时，是有序的。

  - `REDIS_ENCODING_INTSET`（`intset` 编码）

    - 针对整形集合作为底层实现

      ![image.png](https://i.loli.net/2021/07/03/6DEHu9xkzywO3l8.png)

  - `REDIS_ENCODING_HT`（`hashTable`编码）

    - 底层为 `HashTable`，所有的 `Value` 都为 `NULL`

      ![image.png](https://i.loli.net/2021/07/03/jeod2MLuH9qvlnG.png)

  - `intset` 只能保存整数

  - `intset` 能够存储的数据元素个数有限（ <= 512）

    

- Hash（字典）

  - `REDIS_ENCODING_ZIPLIST`（`ZipList`）

    ![image.png](https://i.loli.net/2021/07/03/rspQjbwEW32BChm.png)

  - `REDIS_ENCODING_HT` （`HashTable`）

    ![image.png](https://i.loli.net/2021/07/03/SeW6c7qFDfRkBGN.png)

  - 转换规则

    > 同时满足以下两种情况，编码则为 ziplist，否则就是 HashTable
    >
    > 1. hash 存储的键中，key 和 value 的长度都小于 46 字节
    > 2. hash 里面的键值对中，个数小于 512 个

- List（列表）

  - `REDIS_ENCODING_ZIPLIST`（`ZipList`）

    ![image.png](https://i.loli.net/2021/07/03/FvrAhTGxuUykOCW.png)

  - `REDIS_ENCODING_LINKEDLIST`（`LinkedList`）双向链表

    ![image.png](https://i.loli.net/2021/07/03/rd5x9QKWzRvHTlA.png)

- ZSet（有序集合）

  - `REDIS_ENCODING_ZIPLIST`（`ZipList`）

    ![image.png](https://i.loli.net/2021/07/03/rspQjbwEW32BChm.png)

  - `REDIS_ENCODING_SKIPLIST`（`SkipList`）

    <img src="https://i.loli.net/2021/07/03/hixj3ldFyzgXkM2.png" alt="image.png" style="zoom:150%;" />



# 分布式锁

### 实现要点

1. 加锁和解锁的 `Key` 需要一致
   - 加锁 `setnx` + 过期时间；解锁 `del key` 需要原子性。
   - 可能出现的问题
     - 锁过期。此时可以考虑增加锁过期时间
     - 重叠解锁。设置 value 值为唯一标识
     - 单点问题。`RedLock` 解决
2. 不要永久加锁，需要设置过期时间
3. 一定保证加锁与设置过期时间的原子性
4. 要支持过期续租，可重入

# Redis 持久化

### RDB

- 支持手工执行和服务器定期执行。二进制数据文件，支持回滚。

![image.png](https://i.loli.net/2021/07/03/4uVNgfaDWmlk5nb.png)

### AOF(Append Only File)

> 通过记录 Redis 命令去记录数据库的变更

Client —> Redis 服务器 —>执行命令 —>保存被执行的命令—> AOF 文件	（默认不开启）

> aof_buf：打开 AOF 开关，每次执行完一个命令，都会把一个命令以请求协议格式写入到 aof_buf 中。
>
> - appendfsync always：将 aof_buf 内的内容写入并同步到 AOF 文件中。
>   - 优点：保证数据不丢失
>   - 缺点：效率低
> - appendfsync everysec：将 aof_buf 内的内容写入到 AOF 文件中，上次同步时间距现在 1 s，进行 AOF 同步。
> - appenfsync no：将 aof_buf 内的内容写入到 AOF 文件中，但是不对 AOF 文件进行同步操作，由操作系统决定。
>
> ![image.png](https://i.loli.net/2021/07/03/A8Hepv1sPLIJTXf.png)

缺点：

- AOF 会越来越大，数据加载也会越来越慢。
- 多条执行命令的执行都是多余的

##### AOF 重写

> auto-aof-rewrite-percentage 100 // 比上次重写后的体量增加了 100 % 
>
> auto-aof-rewrite-min-size 64 mb // 在 aof 文件中体积超过 64 MB
>
> 以上两个条件同时满足触发 AOF 重写：
>
> - `fork`  一个子进程去执行 AOF 重写，避免主进程阻塞。
> - 针对于数据不一致的问题，Redis 设置 AOF 的重写缓冲区，当子进程创建时使用。
>
> 缓冲区
>
> - AOF 缓冲区
> - AOF 重写缓冲区

##### 总体流程

![image.png](https://i.loli.net/2021/07/03/tDkV6wM7u81G5Yq.png)