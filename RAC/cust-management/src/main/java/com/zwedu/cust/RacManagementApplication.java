package com.zwedu.cust;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用入口类
 *
 * @author qingchuan
 * @date 2020/12/06
 */
@EnableDubbo
@MapperScan("com.zwedu.cust.mapper")
@SpringBootApplication(scanBasePackages = "com.zwedu.cust.*")
public class RacManagementApplication {
    /**
     * 主函数
     *
     * @param args 应用启动入参
     */
    public static void main(String[] args) {
        SpringApplication.run(RacManagementApplication.class, args);
    }

}

