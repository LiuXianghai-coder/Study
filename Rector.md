# Reactor 

1. Reactor 是反应式编程范式的实现

   > - 用于解决应用存在巨大的并发量的情况。尽管现代的计算机硬件的性能在不断改进，但是软件依旧是一个主要的解决层面。
   > - 通过并行化使用多个线程尽可能地使用硬件资源，但是这会导致资源的浪费
   > - 使用并行的方式实现过于复杂

2. 异步方式的解决方案

   > - `CallBacks`：异步方法不需要返回值，但是需要一个额外的 `callback` 参数（`lambda` 表达式或匿名内部类）去处理当结果可用时做相应的处理。`Swing` 的 `EventListener` 就是这样实现的。
   > - `Futures`：立即返回一个 `Future<T>` 的异步方法，这个异步处理不是处理 `T` 的值，而是通过 `Future` 对象包装去处理它。这个值不是立即可用的，只有当这个对象轮询到这个对象时才是可用的。例如，一个 `ExecutorService` 通过使用 `Future` 对象运行 `Callable<T>` 任务。

3. 存在的一些缺点

   > - `CallBacks` 很难去组合其它的处理，而且可能会导致代码难以阅读与维护（也被称为 `CallBack Hell`）。
   > - `Future` 要比 `callbacks` 好一些，但是对组合其它操作不是很好。它还存在以下问题
   >   - 它很容易在 `Future` 对象调用 `get` 方法的时候被其它阻塞语句阻塞
   >   - 不支持惰性消费
   >   - 缺少对高级错误的处理

4. Reactor 特性

   - 可组合性与可读性
     - 可组合性：编排多个异步任务的能力

   - 数据作为流来处理

   - 在 `Subscribe()` 方法调用之前，什么都不会发生

   - 背压或者消费者向生产者发送传输速率过高信号的能力

   - 与并发无关的高级但高价值的抽象

5. `Flux`

   ![Flux](https://projectreactor.io/docs/core/release/reference/images/flux.svg)

6. `Mono`

   ![Mono](https://projectreactor.io/docs/core/release/reference/images/mono.svg)

7. 流的创建与消费

   > - 创建
   >
   >   - Mono 与 Flux 的区别在于 Flux 可以处理 0 - N 个单元实体，而 Mono 只能处理 0 个或 1个实体对象，也就是说， Mono 相当于 Flux 的一个子集。
   >
   >   - Flux
   >
   >     - 使用简单的工厂方法即可创建对应的实例即可
   >
   >       ```java
   >       Flux<String> sequence = Flux.just("apple", "orange", "watermelon");
   >                               
   >       Flux<String> seq2 = Flux.fromIterable(Arrays.asList("apple", "pair", "strawberry"));
   >                               
   >       Flux<Integer> numbers = Flux.range(1, 5); // 从 1 开始取 5 个数作为处理元素
   >       ```
   >
   >       
   >
   >   - Mono
   >
   >     - 同样地，使用对应的工厂方法即可
   >
   >       ```java
   >       Mono<String> noData = Mono.empty(); // 创建的一个空的数据流
   >                               
   >       Mono<String> data = Mono.just("Foo");
   >       ```
   >
   >       
   >
   > - 消费
   >
   >   - 消费是通过调用 `subscribe()` 方法来实现的，具体的方法如下所示
   >
   >     - `subscribe()` 只是单纯地触发该流的订阅事件。
   >     - `subscribe(Consumer<? super T> consumer);  ` 对每个处理的元素进行相同的消费函数处理。
   >     - `subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer); ` 对每个处理的元素进行相同的消费函数的处理，同时i添加对应的错误处理函数。
   >     - `subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer, Runnable completeConsumer);` 与上面的类似，不过添加了额外的当元素全部处理完成时要进行操作的函数。
   >     - `subscribe(Consumer<? super T> consumer,Consumer<? super Throwable> errorConsumer, Runnable completeConsumer,Consumer<? super Subscription> subscriptionConsumer);` 与上文类似，但是在订阅时需要做一些额外的工作。（该方法自 3.5 开始被弃用，考虑使用 `subscribeWith`）
   >
   >   - 以下是具体的一些示例
   >
   >     ```java
   >     // 一般消费订阅
   >     Flux.range(3, 6)
   >         .subscribe(System.out::println);
   >     
   >     // 添加异常处理函数
   >     Flux.range(3, 6)
   >                 .map(i -> {
   >                     if (i <= 6) return i;
   >                     else
   >                         throw new RuntimeException("Go to 7");
   >                 })
   >                 .subscribe(
   >                         System.out::println,
   >                         error -> System.err.println("Error: " + error)
   >                 );
   >     
   >     // 添加完成时处理函数
   >      Flux.range(3, 6)
   >                 .subscribe(
   >                         System.out::println,
   >                         error -> System.err.println("Error: " + error),
   >                         () -> System.out.println("Done")
   >                 );
   >     
   >     // subscription
   >     Flux.range(1, 9)
   >                 .subscribe(
   >                         System.out::println,
   >                         error -> System.out.println("Error:" + error),
   >                         () -> System.out.println("Done"),
   >                         subscription -> subscription.request(5)
   >                 ); // 在订阅时，要求只请求得到 5 个元素，多余的将会被丢弃，同时也无法触发完成时的对应函数
   >     
   >     ```
   >
   >   - `BaseSubcriber` 提供一些扩展的类调用。`BaseSubscriber` 是一次性使用的，这意味着如果 `BaseSubscriber` 订阅了第二个发布者，它会取消对地一个发布者的订阅。可以通过继承 `BaseSubscriber` 实现自定义的 `Subscriber`
   >
   >     ```java
   >     public class SimpleSubscriber<T> extends BaseSubscriber<T> {
   >         @Override
   >         public void hookOnNext(T value) {
   >             if (value.getClass() != Integer.class) {
   >                 throw new IllegalArgumentException("Class Object not equal....");
   >             }
   >                     
   >             Integer val = (Integer) value;
   >             if (val > 20) {
   >                 throw new RuntimeException("Go to 20");
   >             }
   >                     
   >             System.out.println("Value=" + value);
   >             request(1);
   >         }
   >                     
   >         @Override
   >         protected void hookOnComplete() {
   >             System.out.println("Subscribe Finished.....");
   >         }
   >                     
   >         @Override
   >         protected void hookOnError(Throwable throwable) {
   >             throw new RuntimeException("Runtime Exception......." + throwable.getLocalizedMessage());
   >         }
   >                     
   >         @Override
   >         public void hookOnSubscribe(Subscription subscription) {
   >             System.out.println("Subscriber.......");
   >             request(2);
   >             System.out.println("Subscriber end.....");
   >         }
   >     }
   >     ```




# 基础

- 同步与异步

  > - 同步（Blocking）：需要等待某个阻塞操作完成之后才能完成之后的任务
  > - 异步（Non Blocking）：通过设置回调函数，处理当阻塞操作完成时要做的任务，使得后面的任务可以继续进行。

- Blocking 模式存在的问题

  - 每个线程所占用的资源会比较大
  - 上下文的切换会造成资源的浪费
  - 依赖同步机制
  - 涉及到多状态更新的代码逻辑不易维护

- JDK 异步

  - Future
    - 调用 `get()` 方法强迫改为同步模式
    - 多个异步任务协作过于困难
  - Callback
    - Callback Hell：多层的 Callback 调用导致代码难以维护
    - 代码难以阅读
    - 改变顺序特别困难
    - 难以表达并行的语义

- Reactive Stream 规范

  ![image.png](https://i.loli.net/2021/07/11/17JdYa2mvnopFcA.png)

  - Publisher：发布者，当有订阅者订阅时，触发 `onSubscribe` 一次性事件。
  - Subscriber：订阅者，通过 `request(n)` 设置 n 个请求，从而实现背压和流量控制。
  - Subscription：订阅，连接 Publisher 和 Subscriber 的对象



# Project Reactor 

1. 特性

   - 实现了 Reactive Stream 规范
   - 作为 Spring 5 Reactor 的默认实现
   - 使用 JDK 8 API
   - 支持背压
   - 代码的可组合性和可读性

2. Reactive 关键点

   - Synchronous 和 Asynchronous
   - Assembly Time 和 subscription time（订阅时间）
   - Pull（主动、拉取） 和 Push（被动、推、异步）
   - Hot 和 Cold

3. Publisher

   - Flux(0-n 个事件) & Mono(0 或 1 个事件)
     - empty() / error() 创建一个空的或者错误事件流
     - just() 自适应创建事件流
     - from Array / Iterable / Stream  从 Array / Iterable / Stream 创建事件流
     - generate() / create()（桥接异步任务） / push()
     - interval() / range()
   - Mono Only
     - from .......
     - deffer()

4. Subscriber

   - 所有的工作在 `subscriber` 之前都不会发生

     <img src="https://i.loli.net/2021/07/11/aouNzmMtS8s2ZBb.png" alt="image.png" style="zoom:80%;" />
     - `onSubscribe()`
     - `onNext()`
     - `onError`
     - `onComplete`

   - 自定义 `Subscriber` 可以通过继承 `reactor.core.publisher.BaseScriber` ，重写相关的方法即可

     > request()：
     >
     > - `Long.MAX_VALUE` 表示 unbounded，无限

5. 常用 API

   - 基本操作
     - Stream 的一些基本 API，如 filter、map、reduce、sort等
   - 响应事件
     - `doOnRequest`， `doOnNext`
   - Batch
     - buffer、window、`groupBy`

   - 多个 Flux 操作
     - merge、zip、`concat`、combine、join
   - 其它
     - index、log等

6. 背压

   - 背压策略
     - BUFFER —> `onBackpressureBuffer()` 默认
     - DROP —> `onBackpressureDrop()`  多余的会丢弃
     - ERROR —> `onBackpressureError()` 返回错误
     - LATEST —> `onBackpressureLatest()` 去最后一个

7. Hot Publisher & Cold Publisher

   - Clod
     - 只有 Subscriber 的时候才会运行，每个 Subscriber 都会运行一次
     - 对于每个 Subscriber，都能从头收到每个事件
   - Hot
     - 开始时，可能发生在每个 Subscriber 之前
     - 从订阅的时候开始接受后续的消息，因此可能无法看到之前的事件流
   - Cold 转换为 Hot
     - 通过调用 publish/relay转换为 `ConnectableFlux` ，随后调用 `connect`、`autoConnect`、`refCount` 即可。

8. 线程与调度

   - 线程相关
     - 默认情况下，subscribe 方法调用当前所在线程
     - `publishOn()` 方法调用之后都运行在指定的 scheduler
     - `subscribeOn`，从源头开始指定运行的 scheduler
   - Scheduler
     - `single/newSington`	—单线程
     - `parrallel` —根据 CPU 线程数创建固定的线程数
     - `boundedElastic` —根据需求创建/胡收线程的动态线程池
     - `fromExcutorService`

