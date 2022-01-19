package com.zwedu.rac.domain.common.concurrent;

import com.google.common.base.Stopwatch;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * callable 包装器
 *
 * @author qingchuan
 * @date 2020/12/10
 */
public class CallableWrapper<V> implements Callable {
    /**
     * 名称
     */
    private final String name;
    /**
     * 业务逻辑方法
     */
    private final Callable<V> callable;

    /**
     * 构造函数
     *
     * @param callable 构造函数
     */
    public CallableWrapper(String name, Callable<V> callable) {
        this.name = name;
        this.callable = callable;
    }

    @Override
    public V call() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            return this.callable.call();
        } finally {
            stopwatch.stop();
            LoggerFactory.getLogger(name).info("invoke {} method ,time-consuming(ms)={}", name,
                    stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }
}
