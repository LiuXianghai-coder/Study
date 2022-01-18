package com.example.uruleexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = {"classpath:urule-console-context.xml"})
public class URuleExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(URuleExampleApplication.class, args);
    }

}
