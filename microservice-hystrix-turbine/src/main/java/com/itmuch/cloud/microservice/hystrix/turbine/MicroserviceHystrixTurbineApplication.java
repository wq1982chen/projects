package com.itmuch.cloud.microservice.hystrix.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * Turbine是一个聚合Hystrix监控数据的工具
 * 它可以将所有相关/hystrix.stream端点的数据聚合到一个组合的/turbine.stream,让监控更方便
 * 
 * 下面这个网址讲述了使用仪表盘所遇到的坑,当时遇到了下面这个错误:
 *  InstanceMonitor$MisconfiguredHostException, No message available","path":"/actuator/hystrix.stream:
 *  参考了下面网址中解决这个办法的第2种方式,即在application.yml中配置
 *  https://www.cnblogs.com/houzheng/p/9906800.html
 * 
 * http://localhost:8031/turbine.stream
 * @author Administrator
 *
 */
@SpringBootApplication
@EnableTurbine
public class MicroserviceHystrixTurbineApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(MicroserviceHystrixTurbineApplication.class, args);
    }
  
}
