# Redis 分布式锁

**本文翻译于： https://redis.io/topics/distlock**

<br />



分布式锁在不同的进程必须互斥地访问共享资源的情况下是十分有用的。

已经有许多的库和博客描述了如何使用 `Redis` 来实现 `DLM(Distrubtion Lock Manage)`。但是这些库都使用了不同的方法，并且与使用稍微复杂的设计相比，许多库更加倾向于使用较低保证的简单方式来实现。

在这个页面，我们尝试去提供一种更加规范的算法来使用 `Redis` 实现分布式锁。我们提出了一种称为 `RedLock` 的算法，它实现了 `DLM` 并且我们认为它要比 vanilla 单实例方法更加安全。

我们希望社区能够分析它，提供反馈，把它作为实施或者更加复杂的设计的起点。



## 实现

在描述这个算法之前，这里有一些已经实现了这个算法的链接，你可以把他们作为参考。

- `Redlock-rb`（`Ruby` 实现）：https://github.com/antirez/redlock-rb
- `Redlock-py`（`Python` 实现）：https://github.com/SPSCommerce/redlock-py
- `Pottery`（`Python` 实现）：https://github.com/brainix/pottery#redlock
- `Aioredlock`（异步 `Python` 实现）：https://github.com/joanvila/aioredlock
- `Redlock-php`（`PHP` 实现）：https://github.com/ronnylt/redlock-php
- `PHPRedisMutex`（进一步的 `PHP` 实现）：https://github.com/malkusch/lock#phpredismutex
- `cheprasov/php-redis-lock`（`PHP` 锁库）：https://github.com/cheprasov/php-redis-lock
- `rtckit/react-redlock`（异步`PHP` 实现）：https://github.com/rtckit/reactphp-redlock
- `Redsync`（`Go`语言实现）：https://github.com/go-redsync/redsync
- `Redisson`（`Java` 实现）：https://github.com/mrniko/redisson
- `Redis::DistLock`（`Perl` 实现）：https://github.com/sbertrang/redis-distlock
- `Redlock-cpp`（`C++` 实现）：https://github.com/jacket-code/redlock-cpp
- `Redlock-cs`（`C#/.NET` 实现）：https://github.com/kidfashion/redlock-cs
- `RedLock.net`（`C#/.NET` 实现，包括异步和锁扩展支持）：https://github.com/samcook/RedLock.net
- `ScarletLock`（`C#/.NET` 实现）：https://github.com/psibernetic/scarletlock
- `Redlock4Net`（`C#/.NET` 实现）：https://github.com/LiZhenNet/Redlock4Net
- `node-redlock`（`node.js` 实现）：https://github.com/mike-marcacci/node-redlock



## 安全性和活跃性保证

我们将仅使用三个属性对我们的设计进行建模，从我们的视角来看，这是以有效方式使用分布式锁所需的最低保证。

1. 安全属性：互斥。在任意时刻，只有一个客户端持有锁
2. 活跃性属性 A：无死锁。最终总是有可能获取到锁，即使获取锁的客户端持有的资源崩溃或者被分区
3. 活跃属性 B：故障容忍。只要大多数的 `Redis` 节点启动了，客户端都能够获取和释放锁



## 为什么基于故障转移的实现不够

要了解我们想要改进的地方，让我们分析当前的大部分基于 `Redis` 的分布式事务锁库的情况。

使用 `Redis` 去锁住资源的最简单方式是为一个实例创建一个 `key`，这个 `key` 创建时通常都会指定一个最大存活时间。由于 `Redis` 的过期策略，这个 `key` 最终会被释放（我们的三个属性中的第二个）。当这个客户端需要去释放资源，它将删除这个 `key`

看起来这么做将会很好地工作，但是这样会有一个问题：这是我们架构中的单点故障。当 `Redis` 主节点挂掉的话会发生什么呢？好吧，让我们来添加副节点来避免当主节点挂掉时不可用的问题。很不幸，这么做是不可行的。通过添加副节点我们不能实现我们的安全属性—互斥。因为 `Redis` 的复制过程是异步的。

在这个模型中有一个明显的竞态条件：

1. 客户端 A 请求主节点的锁
2. 主节点在将写入的 `key` 传输到副节点的过程中崩溃了
3. 副节点升级到主节点
4. 客户端 B 请求客户端 A 已经持有的资源锁。此时违反了模型的第一条—互斥

有时，在特殊的情况下（如失败期间），多个客户端在同一时间可以持有锁是完全没有问题的。那么在这种情况下，你可以使用基于副本的的解决方案，否则的话，我们建议使用在我们这个文档中描述的解决方案。



## 使用单实例的正确实现

在尝试克服上文中描述的单实例设置的限制之前，让我们检查如何在这种简单的情形下做的正确。因为在不时地出现竞态条件的应用程序中，这实际上是一个可行的解决方案，并且由于锁定到单个实例是我们将于此处描述的分布式算法的基础。

尝试获取锁，按照以下的方式来进行：

```bash
SET resource_name my_random_value NX PX 30000
```

这个命令只有在指定的 `key` （这里是 `resource_name`）不存在的情况下（`NX` 选项） 才会设置这个 `key`，该 `key` 会在 30000 毫秒后过期（`PX` 选项）

这个 `key` 被设置为 `my_random_value`，这个值必须在所有的客户端和所有的锁请求中都是唯一的。

基本上随机值被用于一安全的方式释放锁，以下的脚本告诉 `Redis`：只有在这个键存在并且键中存储的值是我们预期的情况下删除这个 `key`。以下是使用`Lua`实现的脚本内容

```lua
if redis.call("get",KEYS[1]) == ARGV[1] then
    return redis.call("del",KEYS[1])
else
    return 0
end
```

这对于避免移除由其它客户端创建的锁来讲是重要的。例如，一个客户端可能请求锁，在某些操作中被阻塞超过锁的有效时间（`key` 的过期时间），并且之后移除已经被其它客户端请求的锁。只是使用 `DEL`移除锁不是安全的，对于客户端来讲可能会删除其它客户端的锁。使用上面的脚本，每个锁都会用一个随机字符串签名，因此只有客户端试图删除它的客户端设置的锁时，才会删除它。

这个随机字符串应该是什么？我假设它是来自 `/dev/urandom` 的 20个字节，但是你可以找到一个更加节省的方式来使得它在你的任务中是唯一的。例如，一个安全的选择是使用 `/dev/urandom` 的 `RC4` 作为种子，产生伪随机数流。一个简单的解决方式是使用 `Unix` 的微秒数，组合客户端 ID， 这不是一种安全的方式，但是它能够胜任大部分的工作环境。

我们用于作为 `key` 存活时间的时间，被称为 “锁有效时间”。它既是自动释放时间，也是客户端可能能够再次获取锁之前执行操作的时间，而不会在技术上违反互斥保证，互斥保证仅限于给定窗口从获得锁的那一刻起的时间。	

所以现在我们有一个很好的方法去请求和释放锁。这个系统，推断出由单个始终可用的实例组成的非分布式系统是安全的。让我们扩展这个概念到没有这些保证的分布式系统



## `RedLock` 算法

在算法的分布式版本中，我们假设我们有 N 个 `Redis`主节点。这些节点之间都是完全独立的，所以我们不需要使用副本或者其它的隐式一致性系统。我们已经描述了在单实例的情况下如何安全地去获取和释放锁。我们理所当然地认为算法会在单个实例中使用这种方法来获取和释放锁。在我们的例子中，我们设置 N = 5，这是一个合理的值。所以我们需要运行 5 个 `Redis`  节点在不同的计算机或者虚拟机上，以便使得它们以近乎独立的方式失败。

为了去获取锁，客户端施行了以下操作：

1. 获取当前系统的毫秒时间
2. 它尝试顺序地获取所有 N 个实例中的锁，在所有的实例中使用相同的键名和随机值。在第二步中，当在每个实例中设置锁的时候，客户端使用与主节点k锁自动释放时间相比较小的超时来获取它。例如，如果自动释放时间是 10s，那么超时时间可以在 ～5～50 毫秒的范围内。这可以防止客户端长时间处于阻塞状态，试图与已经关闭的 `Redis` 节点进行通信：如果一个实例变得不可用，我们应该尝试与下一个实例通信
3. 客户端通过从当前的时间中减去从步骤一中获取的时间戳来计算获取锁使用的时间。当且仅当客户端能够在大多数实例（至少三个）中获取锁，并且获取锁所用的总时间小于锁的有效时间，则认为该锁已经被获取。
4. 如果获得了这个锁，则其有效时间被视为初始有效时间减去经过的时间，如同步骤 3 中计算的那样
5. 如果客户端处于某些原因（要么无法锁定 N / 2 + 1个实例，要么有效时间为负数）未能获取锁，它将尝试解锁所有的实例（尽管是它认为无法锁定的实例）



## 这个算法是异步的吗？

这个算法依赖于以下的假设：尽管在每个进程之间每个进程之间没有同步时间，但是每个进程的本地时间都在大致相等的速度下流动，与所释放的时间相比误差很小。这个假设与真实世界的计算机很像：每个计算机都有自己的本地时间并且我们通常可以依靠不同的计算机来实现很下的时间漂移。

在这一点我们需要更好的指定我们的互斥规则：只有当持有锁的客户端在锁的有效时间内（如步骤 3 中获得的）减去一些时间（用于补偿在进程之间的时钟漂移）能够终止它的工作，才能保证这个它。

更多的有关限制时钟漂移的信息，这篇论文是一个有趣的参考：http://dl.acm.org/citation.cfm?id=74870



## 重试和失败

当一个客户端不能获取一个锁的时候，它应该在随机延迟的时间后再次尝试，以使得多个客户端能够获取相同资源的锁（在没有客户端获胜的情况下可能会产生脑裂问题）。除此之外，客户端在获取 `Redis` 主实例的锁的速度越快，发生脑裂的可能行就越小。因此在理想的情况下，客户端应该尝试使用多路的方式在相同的时间点发送 `SET` 命令命令到 `N` 个实例

值得强调的是，对于未能从 `Redis` 主节点获取锁的大部分客户端来说，尽快释放获得的锁对于它们来讲是很重要的，这么做会使得这些没有获取到锁的客户端不需要等待直到 `key` 到达有效期才能获取这个锁（然而，如果发生了网络分区并且不能够和 `Redis` 实例进行通信，那么它将损失可用性来等待 `key` 到期）。



## 释放锁

释放锁很简单，只需要在所有的实例中释放锁即可，客户端无需关系它在这个实例上是否获取到了锁。



## 安全参数

这个算法是安全的吗？我们可以尝试去理解在不同的场景下发生的情况。

开始，让我们假设客户端能够在 `Redis` 主节点上获取锁。所有的节点都将包含相同生命周期的这个 `key`。然而，这个 `key` 在不同的时间被设置，所以这个 `key` 在不同的节点中间都会有不同的到期时间。但是如果第一个 `key` 在一个糟糕的时间点 `T1` 被设置了（这个时间我们设置为在连接到第一个节点之前）并且最后一个 `key` 在另一个糟糕的时间点 `T2` 设置了（这个时间我们设置为得到最后一个服务的回复时间），我们确保第一个 `key`在集合中的过期时间将会存在关系：`MIN_VALIDITY=TTL-(T2-T1)-CLOCK_DRIFT`，其它的 `key` 将会在之后过期，因此我们确信至少这次 `key` 将会同时设置。

在主节点设置 `keys` 的时间段内，其它的客户端将不能获取这个锁，因为 `N/2 + 1` `SET NX` 操作在 `N/2 + 1` 个 `key`已经存在的情况下不会成功。所以一个锁如果已经被获取了，那么同时再被获取（违反了互斥属性）

然而，我们想要确保多个客户端同时获取锁不能同时成功。

如果客户端持有主节点的锁时间已经接近或者大于锁的有效时间，它将这个锁视为无效的并且将会释放这个锁，所以我们只需要考虑客户端能够在小于锁的有效时间内的主节点锁的情况。

在这种情况下，对于上面给出的参数 `MIN_VALIDITY`，在这个时间段内应该没有其它的客户端能够获取这个锁。所以只有当主节点的锁时间大于 `TTL` 时，多个客户端才能同时获取 `N/2 + 1` 个实例的锁，使得锁无效。

你是否能够提供正式的安全证明或者指出现有的相似算法或者找到错误？这将不胜感激



## 活跃性参数

系统的活跃性主要基于以下特征：

1. 锁的的自动释放（由于`key` 到期）：最终 `key` 可以再次被获取锁
2. 事实上，通常客户端会以协作的方式移除当前没有获取到的锁，或者当锁被获取时工作已经快要结束时释放锁。这使得我们可能不必等待 `key` 到期再去获取锁。
3. 事实上，当客户端需要重试以获取锁时，它等待的时间比获取获取主节点锁的时间要多的多，这是为了使得在资源竞争期间不太可能出现脑姴情况。

然而，在网络分区的情况下我们牺牲了可用性，所以如果存在一些连续的分区，我们将可能使得这个系统是一直不可用的。每次客户端在获取锁并在能够移除锁之前被分区都会发生这种情况。

如果无限的连续分区，系统将会是一直不可用的。



## 性能、灾难恢复和同步

许多用户使用 `Redis` 作为锁服务的客户端在获取和释放锁的延迟以及每秒能够执行的获取/释放锁操作的数量都需要消耗很多的时间。为了解决这些问题，与 `N` 个 `Redis` 节点进行通信以减少延迟的策略肯定是多路复用（或者将 `socket` 设置为非阻塞模式，发送所有的命令，在读取所有的命令之后，假设客户端和每个实例之间的 `RTT` 相似）

然而，如果我们想要针对灾难恢复模型，还有另一个关于持久性的考虑。

首先看到这里的问题，我们先假设 `Redis` 没有配置持久性。一个客户端请求5个实例中的3个节点锁。其中一个客户端能够获取到被重启的节点锁，此时对于同一个资源又可以锁定三个实例节点，另一个客户端可以再次锁定它，这违反了排他性的属性。

如果配置的 `Redis` 使用了 `AOF` 持久化，事情会好转很多。例如，我们可以通过发送 `SHUTDOWN` 并且重启服务器来完成升级。由于 `Redis` 的过期是在语义上实现的，所以当服务器关闭时，实际上的时间依旧在流逝，所以我们需要的现在都很好。而且，一切都很好，只要它是平缓地关机。

如果遇到停电这种强制关机的情况，在默认情况下，`Redis`会每秒同步数据到磁盘中，在我们重新启动之后我们的 `key` 将会丢失。理论上，如果我们想在面对任何形式的实例重启是都能保证锁安全，我们需要在持久化设置中设置 `fsync=always`。这反过来将完全破坏传统上以安全的方式实现分布式锁的 `CP` 系统相同级别的性能。

然而，事情总比乍一看的样子要好。基本上只要实例在崩溃后重新启动时，算法的安全行就被保留，它不再参与任何当前活动的锁，因此实例重新启动时当前活动的锁集，都是通过锁定其它实例获得的。

为了保证这一点，我们只需要让一个节点在崩溃之后，不可用的时间比我们的最大`TTL` 要大一些就可以了。即实例崩溃时存在的所有锁的 `key`所需的时间，将会变得无效并且会自动释放。

即使没有使用任何可用的 `Redis` 持久性，使用延迟重启基本上也可以实现安全性，但是请注意，这可能会转化为可用性损失。例如，如果大多数实例崩溃，系统将在 `TTL` 中全局不可用（这里的全局意味着在此期间根本没有资源可以锁定）



## 使这个算法更加可靠：扩展锁

如果客户端执行的工作由小步骤组成，那么可以默认使用较小的锁有效时间，并扩展实现锁扩展机制的算法。上对于客户端，如果在计算过程中，当锁的有效性接近一个较低的值时，如果键存在并且它的值依旧存在，则可以通过向所有扩展键的 `TTL` 的实例发送 `lua` 脚本来扩展锁。

如果客户端能够将锁扩展到大多数实例，并且在有效的时间内（基本上使用的算法与获取锁时使用的算法非常相似），客户端应该只考虑重新获取锁。

然而，这在技术上不会改变算法，因此限制锁重新获取尝试的最大次数，否则会违反活跃行属性。



原文链接：https://redis.io/topics/distlock