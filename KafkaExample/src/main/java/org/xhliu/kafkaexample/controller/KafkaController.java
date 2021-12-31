package org.xhliu.kafkaexample.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xhliu.kafkaexample.vo.Message;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path = "/")
public class KafkaController {
    private final static Logger log = LoggerFactory.getLogger(KafkaController.class);

    private final static String TOPIC_NAME = "xhliu";
    private final static int PARTITION_ONE = 0;
    private final static int PARTITION_TWO = 1;
    private final static String CONSUMER_GROUP = "xhliu-group1";

    @Resource
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Resource
    private ObjectMapper mapper;

    @GetMapping(path = "blockProducer")
    public String blockProducer() throws Throwable {
        List<Message> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Message message = new Message(i, "BLOCKING_MSG_SPRINGBOOT_" + i);
            ListenableFuture<SendResult<String, Message>> future =
                    kafkaTemplate.send(TOPIC_NAME, PARTITION_ONE, String.valueOf(message.getId()), message);

            SendResult<String, Message> result = future.get();
            RecordMetadata metadata = result.getRecordMetadata();
            log.info("---BLOCKING_MSG_SPRINGBOOT--- [topic]={}, [partition]={}, [offset]={}",
                    metadata.topic(), metadata.partition(), metadata.offset());
            list.add(message);
        }
        
        return mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(list);
    }

    @GetMapping(path = "noBlockProducer")
    public String noBlockProducer() throws Throwable {
        List<Message> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Message message = new Message(i, "NO_BLOCKING_MSG_SPRINGBOOT_" + i);
            ListenableFuture<SendResult<String, Message>> future =
                    kafkaTemplate.send(TOPIC_NAME, PARTITION_ONE, String.valueOf(message.getId()), message);

            future.addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onFailure(Throwable ex) {
                    log.error("消息发送失败! ",  ex);
                }

                @Override
                public void onSuccess(SendResult<String, Message> result) {
                    RecordMetadata metadata = result.getRecordMetadata();
                    log.info("---BLOCKING_MSG_SPRINGBOOT--- [topic]={}, [partition]={}, [offset]={}",
                            metadata.topic(), metadata.partition(), metadata.offset());
                }
            });

            list.add(message);
        }

        return mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(list);
    }

    @KafkaListener(topics = {TOPIC_NAME}, groupId = CONSUMER_GROUP)
    public void listenGroup(ConsumerRecord<String, Message> record) {
        log.info("[topic]={}, [position]={}, [offset]={}, [key]={}, [value]={}",
                record.topic(),record.partition(), record.offset(), record.key(), record.value());

        // ack.acknowledge(); 需要enable-auto-commit: false才可以
    }

    @KafkaListener(
            groupId = "xhliu-group1", // 消费组名称
            concurrency = "3",  // 每个消费组中创建 3 个 Consumer
            topicPartitions = {
                    @TopicPartition(topic = "xhliu", partitions = {"0", "1"}),
                    @TopicPartition(
                            topic = "xhliu", partitions = {"0"},
                            partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100")
                    )
            }
    )
    public void listenGroupPro(ConsumerRecord<String, Message> record) {
        Message message = record.value();

        log.info("msgId={}, content={}", message.getId(), message.getBody());
    }
}
