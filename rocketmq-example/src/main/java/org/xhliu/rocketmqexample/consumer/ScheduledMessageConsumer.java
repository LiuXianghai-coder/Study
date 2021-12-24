package org.xhliu.rocketmqexample.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

public class ScheduledMessageConsumer {
    public static void main(String[] args) throws Throwable {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("lxh_consumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe(
                "TopicTest",
                "*"
        );

        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, ctx) -> {
            for (MessageExt ext : msgs) {
                System.out.println("Receive message[msgId=" + ext.getMsgId() + "] "
                        + (System.currentTimeMillis() - ext.getBornTimestamp()) + "ms later");
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
        System.out.println("ScheduledMessageConsumer Start....");
    }
}
