package org.xhliu.rocketmqexample.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class OrderConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("lxh_consumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TopicTest", "TagA || TagC || TagD");

        ThreadLocalRandom random = ThreadLocalRandom.current();

        consumer.registerMessageListener((MessageListenerOrderly) (msgs, context) -> {
            context.setAutoCommit(true);
            for (MessageExt msg : msgs) {
                // 可以看到每个queue有唯一的consume线程来消费, 订单对每个queue(分区)有序
                System.out.println(
                        "consumeThread=" + Thread.currentThread().getName()
                                + "queueId=" + msg.getQueueId() + ", content:" +
                                new String(msg.getBody(), StandardCharsets.UTF_8)
                );
            }

            try {
                //模拟业务逻辑处理中...
                TimeUnit.SECONDS.sleep(random.nextInt(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });

        consumer.start();
        System.out.println("Consumer Started.");
    }
}
