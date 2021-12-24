package org.xhliu.rocketmqexample.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScheduledMessageProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("lxh_producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        final int total = 100;
        for (int i = 0; i < total; i++) {
            LocalDateTime date = LocalDateTime.now();
            Message msg = new Message(
                    "TopicTest",
                    date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                            .getBytes(StandardCharsets.UTF_8)
            );
            msg.setDelayTimeLevel(3);
            producer.send(msg);
        }
        producer.shutdown();
    }
}
