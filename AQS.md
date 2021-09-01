# `AQS`

> `AbstractQueuedSynchronizer` 抽象队列同步器，一个抽象类，内部定义了一些在设计同步器时涉及到的细节问题的实现方法。

> 如果一个类想成为状态依赖的类，那么它必须拥有一组状态。`AQS`负责管理同步器类中的状态，通过一个整形变量 `state` 来维护状态信息。可以通过 `getState()`、`setState(int newState)` 以及 `compareAndSetState(int expect, int update)` 等 `protect` 类型的方法进行操作。
>
> 这个 `state` 整数可以用来表示任意状态。例如，对于 `ReentranLok` 用来表示所有者线程已经重复获取该锁的次数；`Semaphore` 用来表示剩余的许可数量；`FutureTask` 用它来表示任务的状态（尚未开始、正在运行、已完成以及已取消）。
>
> 在同步器类中还可以自行管理一些额外的状态变量，例如，`ReentranLok` 保存了锁的当前所有者的信息，这样就能呢个区分某个获取操作是可重入的还是竞争的。

- `AQS` 中获取操作和释放操作的标准形式

  ```java
  boolean acquire() throws InterruptedException {
      while (当前状态不允许获取操作) {
          if (需要阻塞获取请求) {
              如果当前线程不在队列中，则将其插入队列
              阻塞当前线程
          } else {
              返回失败
          }
      }
      
      可能更新同步器的状态
      如果线程位于队列中，则将其移出队列
      返回成功
  }
  
  void release () {
      更新同步器状态
      if (新的状态允许某个阻塞的线程获取成功) {
          解除队列中一个或多个线程的阻塞状态
      }
  }
  ```

  > 对于支持独占式的同步器，需要实现一些 `protected` 修饰的方法，包括 `tryAcquire`、`tryRelease`、`isHeldExclusively`等；
  >
  > 对于支持共享式的同步器，应该实现的方法有 `tryAcquireShared`、`tryReleaseShared` 等
  >
  > `AQS`的 `acquire`、`acquireShared` 和 `release`、`releaseShared` 等方法都将调用这些方法在子类中带有的前缀 `try` 的版本来判断某个操作能否被执行。
  >
  > 在同步器的子类中，可以根据其获取操作和释放操作的语义，使用 `getState`、`setState`以及 `compareAndSetState` 来检查和更新状态，并根据返回的状态值来告知基类 “获取” 和 “释放” 同步的操作是否是成功的。



### `AQS` 实践

1. 简单闭锁的实现

   ```java
   import java.util.concurrent.locks.AbstractQueuedSynchronizer;
   
   /* 
   * 二元闭锁
   * 
   * 在此处 AQS 的状态被用来表示闭锁的状态，关闭（0） 或打开（1）
   */
   public class OneShotLatch { 
       private final Sync sync = new Sync();
   
       public void signal() {sync.releaseShared(0);}
   
       private class Sync extends AbstractQueuedSynchronizer {
           @Override
           protected boolean tryReleaseShared(int arg) {
               setState(1); //打开闭锁
               return true; // 其它线程现在可以获得此闭锁
           }
   
           @Override
           protected int tryAcquireShared(int arg) {
               return (getState() == 1) ? 1 : -1;
           }
       }
   }
   ```

2. 互斥锁

   ```java
   import java.util.concurrent.TimeUnit;
   import java.util.concurrent.locks.AbstractQueuedSynchronizer;
   import java.util.concurrent.locks.Condition;
   import java.util.concurrent.locks.Lock;
   
   public class Mutex implements Lock {
       // 仅需要将操作代理到Sync上即可
       private final Sync sync = new Sync();
   
       // 静态内部类，自定义同步器
       private static class Sync extends AbstractQueuedSynchronizer {
           // 是否处于占用状态
           protected boolean isHeldExclusively() {
               return getState() == 1;
           }
   
           // 当状态为0的时候获取锁
           public boolean tryAcquire(int acquires) {
               if (compareAndSetState(0, 1)) {
                   setExclusiveOwnerThread(Thread.currentThread());
                   return true;
               }
               return false;
           }
   
           // 释放锁，将状态设置为0
           protected boolean tryRelease(int releases) {
               if (getState() == 0) throw new IllegalMonitorStateException();
               setExclusiveOwnerThread(null);
               setState(0);
               return true;
           }
   
           // 返回一个Condition，每个condition都包含了一个condition队列
           Condition newCondition() {
               return new ConditionObject();
           }
       }
   
       @Override
       public void lock() {
           sync.acquire(1);
       }
   
       @Override
       public void lockInterruptibly() throws InterruptedException {
           sync.acquireInterruptibly(1);
       }
   
       @Override
       public boolean tryLock() {
           return sync.tryAcquire(1);
       }
   
       @Override
       public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
           return sync.tryAcquireNanos(1, unit.toNanos(time));
       }
   
       @Override
       public void unlock() {
           sync.release(0);
       }
   
       @Override
       public Condition newCondition() {
           return sync.newCondition();
       }
   }
   ```