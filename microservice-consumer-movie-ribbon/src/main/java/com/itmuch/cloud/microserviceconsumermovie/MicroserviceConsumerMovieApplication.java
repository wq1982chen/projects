package com.itmuch.cloud.microserviceconsumermovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class MicroserviceConsumerMovieApplication {
	
    public static void main( String[] args ){
    	SpringApplication.run(MicroserviceConsumerMovieApplication.class, args);
    }
   
    @Bean
    @LoadBalanced //只需增加这个注解,就可以为RestTemplate整合Ribbon,使其具备负载均衡能力
    public RestTemplate restTemplate() {
    	return new RestTemplate();
    }
}
