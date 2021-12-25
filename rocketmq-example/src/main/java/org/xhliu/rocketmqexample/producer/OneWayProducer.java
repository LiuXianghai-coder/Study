package org.xhliu.rocketmqexample.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class OneWayProducer {
    private final static Logger log = LoggerFactory.getLogger(OneWayProducer.class);

    public static void main(String[] args)  throws Throwable{
        DefaultMQProducer producer = new DefaultMQProducer("lxh_producer");
        producer.setNamesrvAddr("127.0.0.1:8848");
        producer.start();

        for (int i = 0; i < 10; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message(
                    "TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(StandardCharsets.UTF_8) /* Message body */
            );
            // 发送单向消息，没有任何返回结果
            producer.sendOneway(msg);

        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
