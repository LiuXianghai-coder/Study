package org.xhliu.rocketmqexample.producer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.*;

public class TransactionProducer {
    public static void main(String[] args) throws Throwable {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("lxh_consumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe(
                "TopicTest",
                "*"
        );

        TransactionListener listener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer("lxh_consumer");

        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                5,
                100,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2000),
                r -> {
                    Thread thread = new Thread(r);
                    thread.setName("client-transaction-msg-check-thread");
                    return thread;
                }
        );

        producer.setExecutorService(executorService);
        producer.setTransactionListener(listener);

        producer.start();
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};

        for (int i = 0; i < 10; i++) {
            Message msg = new Message(
                    "TopicTest",
                    tags[i % tags.length],
                    "KEY" + i,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
            System.out.printf("%s%n", sendResult);
            Thread.sleep(10);
        }

        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1000);
        }
        producer.shutdown();
    }
}
