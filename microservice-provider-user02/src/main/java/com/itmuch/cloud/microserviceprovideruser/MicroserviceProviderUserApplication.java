package com.itmuch.cloud.microserviceprovideruser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient // 声明这是一个Eureka Client
public class MicroserviceProviderUserApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(MicroserviceProviderUserApplication.class, args);
    }
}
