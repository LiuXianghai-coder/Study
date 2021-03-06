package org.xhliu.kafkaexample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
                    log.error("???????????????????????????", e);
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

        // ??????????????????????????????????????? true
        properties.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        //  ???????????? offset ???????????????
        properties.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Lists.newArrayList(TOPIC_NAME));

        // ??????????????????????????? TOPIC_NAME ?????? PARTITION_ONE ????????????????????????
//        consumer.assign(Lists.newArrayList(new TopicPartition(TOPIC_NAME, PARTITION_ONE)));
        // ?????? consumer ??????????????????
//        consumer.seekToBeginning(Lists.newArrayList(new TopicPartition(TOPIC_NAME, PARTITION_ONE)));
        // ??????????????? offset ????????????
//        consumer.seek(new TopicPartition(TOPIC_NAME, PARTITION_ONE), 10);

        ConsumerRecords<String, String> records;
        while (true) {
            /*
                ????????????????????????????????????
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

        // ??????????????????????????????????????? true
        properties.put(ENABLE_AUTO_COMMIT_CONFIG, "false");
        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Lists.newArrayList(TOPIC_NAME));

        ConsumerRecords<String, String> records;
        while (true) {
            /*
                ????????????????????????????????????
             */
            records = consumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String, String> record : records) {
                log.info("[topic]={}, [position]={}, [offset]={}, [key]={}, [value]={}",
                        record.topic(),record.partition(), record.offset(), record.key(), record.value());
            }

            if (records.count() > 0) {
                /*
                    ?????????????????? offset????????????????????????????????? offset ????????????
                 */
                consumer.commitSync();

                /*
                    ?????????????????????????????? offset ?????????
                 */
                /*
                consumer.commitAsync((offsets, e) -> {
                    log.error("?????? offset={}", gson.toJson(offsets));
                    if (e != null) {
                        log.error("?????? offset ???????????????", e);
                    }
                });
                */
            }

            Thread.sleep(2000);
        }
    }

    @Resource
    private ObjectMapper mapper;

    @Test
    void testDeSerializer() throws JsonProcessingException {
        Message message = new Message(1, "This is a Message");
        String json = mapper.writer().writeValueAsString(message);
        System.out.println(json);

        Message value = mapper.readValue(json, Message.class);
        log.info("result={}", value);
    }
}
