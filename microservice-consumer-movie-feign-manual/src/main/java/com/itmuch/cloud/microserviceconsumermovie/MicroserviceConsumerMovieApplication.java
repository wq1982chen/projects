package com.itmuch.cloud.microserviceconsumermovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 去掉 @EnableFeignClients 注解
 * 手工调用Feign的Api来实现调用服务
 * @author Administrator
 *
 */
@SpringBootApplication
@EnableEurekaClient
//@EnableFeignClients
public class MicroserviceConsumerMovieApplication {
	
    public static void main( String[] args ){
    	SpringApplication.run(MicroserviceConsumerMovieApplication.class, args);
    }
   
}
