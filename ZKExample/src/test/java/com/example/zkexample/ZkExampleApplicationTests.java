package com.example.zkexample;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ZkExampleApplicationTests {

    static final Logger log = LoggerFactory.getLogger(ZkExampleApplicationTests.class);

    private final static String NODE_NAME = "/curator-node";

    private final static String EPHEMERAL_NODE_NAME = "/curator-ephemeral-node";

    private final static String PARENT_NODE_NAME = "/animal/dog/whiteDog";

    private final static byte[] VALUE_BYTES = "xhliu".getBytes();

    private final static byte[] NEW_VALUE_BYTES = "xhliu-new".getBytes();

    @Resource
    private CuratorFramework curatorFramework;

    @Test
    void createNode() throws Exception {
        String path = curatorFramework.create()
                .forPath(NODE_NAME, VALUE_BYTES);
        log.info("Create Node Success, znode={}", path);
    }

    /*
        创建临时节点
     */
    @Test
    void createEphemeralNode() throws Exception {
        String path = curatorFramework.create()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(EPHEMERAL_NODE_NAME, VALUE_BYTES);
        log.info("Create Ephemeral Node success, znode={}", path);
        Thread.sleep(10000);
    }

    @Test
    void createWithParent() throws Exception {
        String path = curatorFramework.create()
                .creatingParentsIfNeeded()
                .forPath(PARENT_NODE_NAME, VALUE_BYTES);
        log.info("Create Node Success, znode={}", path);
    }

    /**
     * 获取节点的值
     */
    @Test
    void getData() throws Throwable {
        byte[] valueByte = curatorFramework.getData()
                .forPath(NODE_NAME);

        log.info("getData()={}", new String(valueByte));
    }

    /**
     * 修改节点的值
     */
    @Test
    void setData() throws Throwable {
        curatorFramework.setData().forPath(NODE_NAME, NEW_VALUE_BYTES);
    }

    /**
     * 删除节点
     */
    @Test
    void deleteData() throws Throwable {
        curatorFramework.delete()
                .guaranteed()
                .deletingChildrenIfNeeded()
                .forPath(NODE_NAME);
    }

    @Test
    void contextLoads() {
    }

}
