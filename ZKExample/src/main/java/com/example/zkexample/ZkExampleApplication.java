package com.example.zkexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class ZkExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkExampleApplication.class, args);
    }

}
