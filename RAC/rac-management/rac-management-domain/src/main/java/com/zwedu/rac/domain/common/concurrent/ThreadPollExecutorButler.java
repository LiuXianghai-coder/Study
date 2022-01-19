package com.zwedu.rac.domain.common.concurrent;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * 线程池管家，持有系统中所有的线程池
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public interface ThreadPollExecutorButler<T> {

    /**
     * 并行查询线程池
     */
    ExecutorService PARALLEL_QUERY_THREAD_POOL = new ThreadPoolExecutor(100, 100,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(10000), new BasicThreadFactory.Builder()
            .daemon(false).namingPattern("PARALLEL-QUERY-THREAD").build());

    /**
     * 线程提交
     * @param callable 方法体
     */
    default T call(String name, Callable<T> callable) {
        return (T) PARALLEL_QUERY_THREAD_POOL.submit(new CallableWrapper<T>(name, () -> callable.call()));
    }
}
