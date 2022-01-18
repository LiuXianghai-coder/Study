package com.zwedu.rac.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用入口类
 *
 * @author qingchuan
 * @date 2020/12/06
 */
@Slf4j
@EnableDubbo
@MapperScan("com.zwedu.rac.infrastructure.mapper")
@SpringBootApplication(scanBasePackages = "com.zwedu.rac.*")
public class RacManagementApplication {
    /**
     * 主函数
     *
     * @param args 应用启动入参
     */
    public static void main(String[] args) {
        SpringApplication.run(RacManagementApplication.class, args);
        log.info("rac management application started!");
    }

}

