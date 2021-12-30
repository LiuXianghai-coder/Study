package org.xhliu.kafkaexample.config;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.producer.ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class PropConfig {
    @Bean(name = "producerProp")
    public Properties producerProp() {
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

        return properties;
    }

    @Bean(name = "consumerProp")
    public Properties consumerProp() {
        Properties properties = new Properties();

        /*
            配置 Kafka 的 Broker 列表
         */
        properties.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092,127.0.0.1:9093");

        /*
            配置消费组
         */
        properties.put(GROUP_ID_CONFIG, "xhliu-group1");

        /*
            Offset 的重置策略，对于新创建的一个消费组，offset 是不存在的，这里定义了如何对 offset 进行赋值消费
            latest：默认值，只消费自己启动之后发送到主题的消息
            earliest：第一次从头开始消费，之后按照 offset 的记录继续进行消费
         */
        properties.put(AUTO_OFFSET_RESET_CONFIG, "earliest");

        /*
            设置 Consumer 给 Broker 发送心跳的时间间隔
         */
        properties.put(HEARTBEAT_INTERVAL_MS_CONFIG, 1000);

        /*
            如果超过 10s 没有收到消费者的心跳，则将消费者踢出消费组，然后重新 rebalance，将分区分配给其它消费者
         */
        properties.put(SESSION_TIMEOUT_MS_CONFIG, 10*1000);

        /*
            一次 poll 最大拉取的消息的条数，具体需要根据消息的消费速度来设置
         */
        properties.put(MAX_POLL_RECORDS_CONFIG, 500);

        /*
            如果两次 poll 的时间间隔超过了 30s，那么 Kafka 就会认为 Consumer 的消费能力太弱，
            将它踢出消费组，再将分区分配给其它消费组
         */
        properties.put(MAX_POLL_INTERVAL_MS_CONFIG, 30*1000);

        /*
            设置 Key 和 Value 的反序列化实现类
         */
        properties.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        return properties;
    }
}
