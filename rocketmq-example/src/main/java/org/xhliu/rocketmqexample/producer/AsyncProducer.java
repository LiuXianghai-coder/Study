package org.xhliu.rocketmqexample.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AsyncProducer {
    static final Logger log = LoggerFactory.getLogger(AsyncProducer.class);

    public static void main(String[] args) throws Throwable {
        DefaultMQProducer producer = new DefaultMQProducer("lxh_producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        Scanner sc = new Scanner(System.in);
        String line;
        while ((line = sc.nextLine()).length() > 0) {
            Message msg = new Message(
                    "TopicTest",
                    "TagA",
                    line.getBytes(StandardCharsets.UTF_8)
            );

            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("send result: " + sendResult.toString());
                }

                @Override
                public void onException(Throwable throwable) {
                    log.error(throwable.getMessage());
                    throwable.printStackTrace();
                }
            });
        }

        producer.shutdown();
    }
}
