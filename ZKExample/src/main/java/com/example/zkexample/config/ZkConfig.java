package com.example.zkexample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "curator")
public class ZkConfig {
    // 重试次数
    private int retryCount;

    // 重试的间隔时间（单位：ms）
    private int sleepBetweenRetries;

    // zk 的连接地址（多个 zk 的时候，使用 , 分隔）
    private String connect;

    // 会话超时时间（单位：ms）
    private int sessionTimeout;

    // 连接超时时间（单位：ms）
    private int connectionTimeout;

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getSleepBetweenRetries() {
        return sleepBetweenRetries;
    }

    public void setSleepBetweenRetries(int sleepBetweenRetries) {
        this.sleepBetweenRetries = sleepBetweenRetries;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public String toString() {
        return "CuratorConfig{" +
                "retryCount=" + retryCount +
                ", sleepBetweenRetries=" + sleepBetweenRetries +
                ", connect='" + connect + '\'' +
                ", sessionTimeout=" + sessionTimeout +
                ", connectionTimeout=" + connectionTimeout +
                '}';
    }
}
