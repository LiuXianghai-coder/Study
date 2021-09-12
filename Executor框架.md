# Executor 框架

`Executor` 基于  “生产者—消费者” 模式，如果要在程序中实现一个生产者—消费者的设计，最简单的方式便是采用 Executor。

### 执行策略

在执行策略中定义任务执行的 “What”、“Where”、“When”、“How"等

- 在什么线程中执行任务
- 任务按照什么顺序执行
- 有多少个任务能够并发地执行
- 在队列中有多少个任务在等待执行
- 如果系统由于过载而需要拒绝一个任务，那么应该选择哪一个任务？另外，如何通知应用程序有任务被拒绝
- 在执行一个任务之前或之后，应该进行哪些动作



### 线程池

​	管理一组同构工作线程的资源池。可以通过 `Executors` 中的静态工厂方法来创建一个线程池。

- `newFixedThreadPool(int nThreads)`：创建一个固定长度的线程池，每提交一个任务时创建一个线程，直到到达线程池的最大数量，这时线程池的规模将不再变化。（如果某个线程由于发生了未预期的异常而结束，那么线程池将会补充一个新的线程）

- `newCachedThreadPool()`：创建一个可缓存的线程池，如果线程池的当前规模超过了处理需求时，那么将回收空闲的线程；当需求增加时，可以添加新的线程，线程池的规模不存在任何限制

- `newSingleThreadExecutor()`：创建单个工作线程来执行任务，如果这个线程异常结束，则会创建一个新的线程来替代。使用 `newSingleThreadExecutor` 可以确保任务按照在队列中的顺序来串行执行。

- `newSingleThreadScheduledExecutor()`：创建一个固定长度的线程池，而以延迟或者定时的方式来执行任务。

  其中，`newFixedThreadPool` 和 `newSingleThreadExecutor` 都是返回通用的 `ThreadPoolExecutor` 实例对象



### Executor 生命周期

​	由于 `Executor` 是以异步的方式来执行任务，因此如果想要关闭 `Executor` 不是一件容易的事。为此，`ExecutorService` 扩展了 `Executor`，添加了一些用于管理生命周期的方法。

​	

​	`ExecutorService` 有三种状态：运行、关闭和已终止。

- `ExecutorService` 在创建时处于运行状态。

- `shutdown()` 方法将执行平缓的关闭过程，具体过程为：不再接受新的任务，同时等待已经提交的任务执行完成（包括那些还未开始执行的任务）

- `shutdownNow()` 方法是以一种粗暴的方式关闭 `ExecutorService`，具体过程为：尝试取消所有运行中的任务，并且不再启动队列中尚未开始执行的任务

  

​	在 `ExecutorService` 关闭提交的任务将由 ”拒绝执行处理器（Rejected Execution Handler）“ 来处理，它会抛弃任务，或者使得 `execute` 方法抛出一个未检查的 `RejectedExecutionException`。等待所有的任务都执行完毕之后，`ExecutorService` 将转入终止状态。可以通过 `awaitTermination` 方法来等待 `ExecutorService` 到达终止状态，或者通过 `isTerminated` 来轮询 `ExecutorService` 是否已经到达终止状态。通常在调用 `awaitTermination` 方法后会调用 `shutdown` 方法，从而产生同步地关闭 `ExecutorService` 的效果。

​	拒绝执行处理器（`RejectedExecutionHandler`）：JDK 提供了几种实现，分别为 `AbortPolicy`、`CallerRunsPolicy`、`DiscardOldestPolicy`、`DiscardPolicy`。

- `AbortPolicy`：抛出 `RejectedExecutionException`，调用者可以捕获这个异常，然后执行相应的操作。
- `DiscardPolicy`：当任务无法放入到队列中时，将会抛弃该任务
- `DiscardOldestPolicy`：抛弃下一个将要执行的任务，然后重新提交新的的任务（不要和优先级队列一起使用）
- `CallerRunsPolicy`：将某些任务退回到调用者，从而降低新任务的流量。它不会在线程池的某个线程中执行新提交的任务，而是在一个调用了 `execute` 的线程中执行该任务。