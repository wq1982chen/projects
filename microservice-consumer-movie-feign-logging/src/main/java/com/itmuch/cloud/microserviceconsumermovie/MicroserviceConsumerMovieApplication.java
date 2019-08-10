package com.itmuch.cloud.microserviceconsumermovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.itmuch.cloud.microserviceconsumermovie.feign.ExcludeComponent;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@ComponentScan(excludeFilters =
					@ComponentScan.Filter(type = FilterType.ANNOTATION,
																	value = {ExcludeComponent.class})) 
public class MicroserviceConsumerMovieApplication {
	
    public static void main( String[] args ){
    	SpringApplication.run(MicroserviceConsumerMovieApplication.class, args);
    }
   
}
