package com.itmuch.cloud.microserviceprovideruser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 使用spring boot actuator来对web服务进行监控
 * spring boot项目集成actuator非常容易，
 * 只需要添加一个actuator的maven依赖就好了
 * http://localhost:8000/actuator
 * http://localhost:8000/actuator/health 
 * http://localhost:8000/actuator/info
 * actuator默认只暴露了几个简单的endpoint
 * 开启所有接口：
 * management.endpoints.web.exposure.include="*"
 * http://localhost:8000/actuator
 * http://localhost:8000/actuator/metrics
 * http://localhost:8000/actuator/metrics/http.server.requests
 * http://localhost:8000/actuator/metrics/http.server.requests?tag=uri:/actuator/metrics	
 */
@SpringBootApplication
public class MicroserviceProviderUserApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(MicroserviceProviderUserApplication.class, args);
    }
}
