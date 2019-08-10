package com.itmuch.cloud.microservice.hystrix.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

/**
 * Turbine是一个聚合Hystrix监控数据的工具
 * 它可以将所有相关/hystrix.stream端点的数据聚合到一个组合的/turbine.stream,让监控更方便
 * 
 * http://localhost:8031/turbine.stream
 * @author Administrator
 *
 */
@SpringBootApplication
@EnableTurbineStream
public class MicroserviceHystrixTurbineMQApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(MicroserviceHystrixTurbineMQApplication.class, args);
    }
  
}
