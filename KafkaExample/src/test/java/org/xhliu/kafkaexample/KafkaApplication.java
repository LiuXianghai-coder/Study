package org.xhliu.kafkaexample;

import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class KafkaApplication {
    public static void main(String[] args) {
        Properties properties = new Properties();
        // 设置 Kafka 的 Broker 列表
        properties.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092,127.0.0.1:9093");

        /*
            设置消息的持久化机制参数：
            0 表示不需要等待任何 Broker 的确认；
            1 表示至少等待 Leader 的数据同步成功；
            -1 则表示需要等待所有的 min.insync.replicas 配置的副本的数量都成功写入
         */
        properties.put(ACKS_CONFIG, "1");

        /*
            配置失败重试机制
         */
        properties.put(RETRIES_CONFIG, 3); // 失败重试 3 次
        properties.put(RECONNECT_BACKOFF_MS_CONFIG, 300); // 每次重试的时间间隔为 300 ms


        /*
            配置缓存相关的信息
         */
        properties.put(BUFFER_MEMORY_CONFIG, 32*1024*1024); // 设置发送消息的本地缓冲区大小，这里设置为 32 MB
        properties.put(BATCH_SIZE_CONFIG, 16*1024); // 设置批量发送消息的大小，这里设置为 16 KB
        /*
            batch 的等待时间，默认值为 0, 表示消息必须被立即发送，这里设置为 10 表示消息发送之后的 10 ms 内，
            如果 Batch 已经满了，那么这个消息就会随着 Bathh 一起发送出去
         */
        properties.put(LINGER_MS_CONFIG, 10);

        /*
            配置 key 和 value 的序列化实现类
         */
        properties.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    }
}
