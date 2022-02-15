package org.xhliu.kafkaexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.xhliu.kafkaexample.repo.UserJpaRepo;

@SpringBootApplication
public class KafkaExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =
                SpringApplication.run(KafkaExampleApplication.class, args);

        UserJpaRepo bean = context.getBean(UserJpaRepo.class);
        System.out.println("UserJpaRepo: " + bean);
    }

}
