package com.itmuch.cloud.microserviceconsumermovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 增加sleuth依赖
 * 增加DispatchServlet日志级别
 * 
 * @author Administrator
 *
 */
@SpringBootApplication
public class MicroserviceConsumerMovieApplication {
	
    public static void main( String[] args ){
    	SpringApplication.run(MicroserviceConsumerMovieApplication.class, args);
    }
   
    @Bean
    public RestTemplate restTemplate() {
    	return new RestTemplate();
    }
}
