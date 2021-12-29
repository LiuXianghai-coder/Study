package com.example.zkexample;

import com.google.gson.Gson;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
public class ReadWriteLockTest {
    final static Logger log = LoggerFactory.getLogger(ReadWriteLockTest.class);

    @Resource
    private CuratorFramework curatorClient;

    @Test
    void getReadLock() throws Exception {
        InterProcessReadWriteLock lock =
                new InterProcessReadWriteLock(curatorClient, "/lock");
        InterProcessMutex readLock = lock.readLock();

        log.info("等待获取读锁.......");
        readLock.acquire();
        log.info("获取读锁成功，进入下一步的操作");

        Thread.sleep(10000);
        readLock.release();
        log.info("任务完成，释放读锁");
    }

    @Test
    void getWriteLock() throws Exception {
        InterProcessReadWriteLock lock =
                new InterProcessReadWriteLock(curatorClient, "/lock");
        InterProcessMutex writeLock = lock.writeLock();
        log.info("等待获取写锁.......");
        writeLock.acquire();
        log.info("获取写锁成功，执行后续的操作");
        Thread.sleep(10000);
        writeLock.release();

        log.info("写入的任务执行完毕.....");
    }

    final static String WATCH_NODE_NAME = "/watchNode";

    @Test
    void watch() throws IOException {
        Gson gson = new Gson().newBuilder().create();

        CuratorCache cache = CuratorCache.build(curatorClient, WATCH_NODE_NAME);
        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forNodeCache(() -> log.info("--- for node cache ---- {} node is changed!", WATCH_NODE_NAME))
                .forAll((type, oldData, data) -> log.info("----forAll----- {} node is changed, type={} oldData={} data={}",
                        WATCH_NODE_NAME, gson.toJson(type), gson.toJson(oldData), gson.toJson(data))
                )
                .build();
        cache.listenable().addListener(listener);
        cache.start();

        int read = System.in.read();
    }
}
