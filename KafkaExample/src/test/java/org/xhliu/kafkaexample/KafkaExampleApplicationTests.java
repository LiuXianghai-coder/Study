package org.xhliu.kafkaexample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.TopicPartition;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.xhliu.kafkaexample.vo.Message;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG;

@SpringBootTest
class KafkaExampleApplicationTests {
    private final static Logger log = LoggerFactory.getLogger(KafkaExampleApplicationTests.class);

    @Resource(name = "producerProp")
    Properties producerProp;

    @Resource(name = "consumerProp")
    Properties consumerProp;

    static final String TOPIC_NAME = "xhliu";
    static final String BROKERS = "127.0.0.1:9092,127.0.0.1:9093";
    static final Integer PARTITION_ONE = 0;
    static final Integer PARTITION_TWO = 1;
    static final String CONSUMER_GROUP_NAME = "xhliu-group1";
    static final Gson gson = new GsonBuilder().create();

    @Test
    void syncSend() {
        Producer<String, String> producer = new KafkaProducer<>(producerProp);
        Message message;

        for (int i = 0; i < 5; ++i) {
            message = new Message(i, "BLOCK_MSG_" + i);

            ProducerRecord<String, String> record = new ProducerRecord<>(
                    TOPIC_NAME, PARTITION_TWO,
                    String.valueOf(message.getId()), gson.toJson(message)
            );

            try {
                Future<RecordMetadata> future = producer.send(record);
                RecordMetadata metadata = future.get();

                Thread.sleep(2500);
                log.info("[topic]={}, [position]={}, [offset]={}", metadata.topic(),
                        metadata.partition(), metadata.offset());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void asyncSend() throws InterruptedException {
        Producer<String, String> producer = new KafkaProducer<>(producerProp);
        Message message;

        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 5; ++i) {
            message = new Message(i, "NO_BLOCK_MSG_" + i);

            ProducerRecord<String, String> record = new ProducerRecord<>(
                    TOPIC_NAME, PARTITION_TWO,
                    String.valueOf(message.getId()), gson.toJson(message)
            );

            producer.send(record, (metadata, e) -> {
                if (e != null) {
                    log.error("异步发送消息失败，", e);
                    return;
                }

                if (metadata != null) {
                    log.info("[topic]={}, [position]={}, [offset]={}",
                            metadata.topic(), metadata.partition(), metadata.offset());
                }

                latch.countDown();
            });
        }

        log.info("wait......");
        latch.wait();
    }

    @Test
    void autoCommitOffset() throws InterruptedException {
        Properties properties = (Properties) consumerProp.clone();

        // 设置是否是自动提交，默认为 true
        properties.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        //  自动提交 offset 的时间间隔
        properties.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Lists.newArrayList(TOPIC_NAME));

        // 指定当前的消费者在 TOPIC_NAME 上的 PARTITION_ONE 的分区上进行消费
//        consumer.assign(Lists.newArrayList(new TopicPartition(TOPIC_NAME, PARTITION_ONE)));
        // 指定 consumer 从头开始消费
//        consumer.seekToBeginning(Lists.newArrayList(new TopicPartition(TOPIC_NAME, PARTITION_ONE)));
        // 指定分区和 offset 进行消费
//        consumer.seek(new TopicPartition(TOPIC_NAME, PARTITION_ONE), 10);

        ConsumerRecords<String, String> records;
        while (true) {
            /*
                通过长轮询的方式拉取消息
             */
            records = consumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String, String> record : records) {
                log.info("[topic]={}, [position]={}, [offset]={}, [key]={}, [value]={}",
                        record.topic(),record.partition(), record.offset(), record.key(), record.value());
            }

            Thread.sleep(10000);
        }
    }

    @Test
    void manualCommitOffset() throws InterruptedException {
        Properties properties = (Properties) consumerProp.clone();

        // 设置是否是自动提交，默认为 true
        properties.put(ENABLE_AUTO_COMMIT_CONFIG, "false");
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Lists.newArrayList(TOPIC_NAME));

        ConsumerRecords<String, String> records;
        while (true) {
            /*
                通过长轮询的方式拉取消息
             */
            records = consumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String, String> record : records) {
                log.info("[topic]={}, [position]={}, [offset]={}, [key]={}, [value]={}",
                        record.topic(),record.partition(), record.offset(), record.key(), record.value());
            }

            if (records.count() > 0) {
                /*
                    手动同步提交 offset，当前线程会阻塞，知道 offset 提交成功
                 */
                consumer.commitSync();

                /*
                    通过异步的方式来完成 offset 的提交
                 */
                /*
                consumer.commitAsync((offsets, e) -> {
                    log.error("异常 offset={}", gson.toJson(offsets));
                    if (e != null) {
                        log.error("提交 offset 发生异常，", e);
                    }
                });
                */
            }

            Thread.sleep(2000);
        }
    }
}
