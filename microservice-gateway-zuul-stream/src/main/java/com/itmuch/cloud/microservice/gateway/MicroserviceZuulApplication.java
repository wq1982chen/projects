package com.itmuch.cloud.microservice.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/*
 * 因为Zuul集成了Hystrix,其实也可以通过Hystrix DashBoard监控Zuul工作
 * 那么通过自定义HystrixConfiguration类后，
 * 访问http://localhost:8040/hystrix.stream 或者
 * http://localhost:8040/actuator/hystrix.stream就可以看到hytrix流信息
 */
/**
 * 路由管理能力: 暴露一个路由管理端点/route
 * Get访问:返回zuul当前映射的路由列表
 * Post访问:强制刷新zuul当前映射的路由列表
 * 请求http://127.0.0.1:8040/actuator/routes 报404异常(书上在地址上还缺少/actuator,要注意)
 * 由于actuator插件默认未将routes endpoint暴露出来,
 * 在项目的yml或者properties属性文件中配置一下即可
 * management:
 *   endpoints:
 *      web:
 *         exposure:
 *           include: route
 */
@SpringBootApplication
@EnableZuulProxy
public class MicroserviceZuulApplication {
    public static void main( String[] args ){
    	SpringApplication.run(MicroserviceZuulApplication.class, args);
    }
  
}
