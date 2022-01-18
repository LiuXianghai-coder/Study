package com.example.uruleexample.config;

import com.bstek.urule.KnowledgePackageReceiverServlet;
import com.bstek.urule.console.URuleServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class URuleConfig {
    @Bean
    public ServletRegistrationBean<URuleServlet> registerUruleServlet() {
        return new ServletRegistrationBean<>(
                new URuleServlet(), "/urule/*");
    }

    @Bean
    public ServletRegistrationBean<KnowledgePackageReceiverServlet> registerKnowledgeServlet() {
        return new ServletRegistrationBean<>(
                new KnowledgePackageReceiverServlet(),
                "/knowledgepackagereceiver"
        );
    }
}
