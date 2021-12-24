package org.xhliu.rocketmqexample.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class SyncConsumer {
    public static void main(String[] args) throws Throwable {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("lxh_consumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe(
                "TopicTest",
                "*"
        );

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> list,
                    ConsumeConcurrentlyContext context
            ) {
                for (MessageExt ext : list) {
                    System.out.println("Get Body: " + new String(ext.getBody(), StandardCharsets.UTF_8));
                }
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), list);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.println("Consumer Start.....");
    }
}
