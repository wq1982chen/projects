package com.itmuch.cloud.microservicedescoveryeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Hello world!
 *
 */
@EnableEurekaServer // 声明这是一个Eureka Server
@SpringBootApplication
public class MicroserviceDescoveryEurekaApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(MicroserviceDescoveryEurekaApplication.class, args);
    }
}
