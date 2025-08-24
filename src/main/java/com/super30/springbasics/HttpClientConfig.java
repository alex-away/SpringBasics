package com.super30.springbasics;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientConfig {

    // we'll be configuring here so we can use this project can be used as a microservice
    // i.e, it'll be used by other services
    @Bean // now the lifecycle of this object will be managed by spring boot due to this annotation
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
