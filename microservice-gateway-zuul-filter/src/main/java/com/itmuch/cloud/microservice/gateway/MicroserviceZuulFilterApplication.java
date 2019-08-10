package com.itmuch.cloud.microservice.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * 执行过滤器的测试.
 * 
 * Zuul默认实现了一些过滤器,例如DebugFilter\FormBodyWrapperFilter\PreDecorationFilter等
 * 存放在spring-cloud-netflix-core这个jar包的org.framework.cloud.netflix.zuul.filter包中
 * 如果要禁用这些默认的过滤器,只需设置:
 * zuul.<SimpleClassName>.<filterType>.disable=true , 即可禁用SimpleClassName对应的过滤器
 * 例如 zuul.SendResponseFilter.post.disable=true
 * 禁用本例的过滤器可配置如下:
 * zuul.PreRequestLogFilter.pre.disable=true
 * 
 * 
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
public class MicroserviceZuulFilterApplication {
    public static void main( String[] args ){
    	SpringApplication.run(MicroserviceZuulFilterApplication.class, args);
    }
  
}
