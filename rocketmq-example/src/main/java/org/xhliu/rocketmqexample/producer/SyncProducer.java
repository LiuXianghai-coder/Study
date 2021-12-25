package org.xhliu.rocketmqexample.producer;

import ch.qos.logback.classic.BasicConfigurator;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SyncProducer {
    public static void main(String[] args) throws Throwable {
        DefaultMQProducer producer = new DefaultMQProducer("lxh_producer");
        producer.setNamesrvAddr("127.0.0.1:8848");
        producer.start();
        Scanner sc = new Scanner(System.in);
        String line;
        while ((line = sc.nextLine()).length() > 0) {
            Message msg = new Message(
                    "TopicTest",
                    "TagA",
                    line.getBytes(StandardCharsets.UTF_8)
            );
            SendResult result = producer.send(msg);
            System.out.println(result);
        }
        producer.shutdown();
    }
}
