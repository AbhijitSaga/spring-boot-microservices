package com.user.service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Configuration
public class MyAppConfig {
    @Bean("myRestTemplateBean")
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
