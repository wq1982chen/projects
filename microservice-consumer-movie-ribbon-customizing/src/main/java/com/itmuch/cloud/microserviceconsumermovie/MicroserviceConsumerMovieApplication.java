package com.itmuch.cloud.microserviceconsumermovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

import com.itmuch.cloud.microserviceconsumermovie.customizing.ExcludeComponent;

/**
 * 
 * 使用Ribbon配置类来改变对负载均衡的策略
 * @author Administrator
 *  使用excludeFilters是为了确保不加载指定的Ribbon自定义配置类,
 *  从而保证了只有对指定微服务调用的客户端,客户化的Ribbon策略才有效
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
value = {ExcludeComponent.class})) 
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
