package com.project.orderService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.http.WebSocket;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(){

        return WebClient.builder().build();
    }
}
