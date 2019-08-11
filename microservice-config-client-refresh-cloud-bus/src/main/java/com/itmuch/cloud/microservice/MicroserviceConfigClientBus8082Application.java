package com.itmuch.cloud.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 *  可以使用Config Server的端点来获取配置文件的内容
 *  端点与配置文件的映射规则如下:
 *  /{application}/{profile}[/{label}]
 *  /{application}-{profile}.yml
 *  /{application}-{profile}.properties
 *  /{label}/{application}-{profile}.yml
 *  /{label}/{application}-{profile}.properties
 *  本例可以如下:
 *  http://localhost:8080/microservice-foo/dev
 *  http://localhost:8080/microservice-foo-dev.yml
 *  http://localhost:8080/microservice-foo-dev.properties
 *  
 *  我在github上创建了新的分支,取名dev,即label=dev,通过以下地址可以访问到
 *  http://localhost:8080/dev/microservice-foo-dev.yml 
 *  其他的访问方式可以触类旁通
 *  
 */
/*
 *  运行期间动态调整配置。
 *  1. 增加actuator依赖，该依赖包含refresh端点用于刷新配置
 *  2. 在Controller上添加注解@RefreshScope , 添加这个注解的类会在配置更改时得到特殊处理
 *  3. 开放所有actuator节点  默认只开启了health、info两个节点.
 *  4. GET http://localhost:8081/profile   显示 dev-2.0
 *  5. POST http://localhost:8081/actuator/refresh 后,在GET http://localhost:8081/profile 显示 dev-2.0-changed
 */
/*
 *  测试bus
 *  1.x和2.x的差别 
 *  Spring boot 2.0的改动较大，/ bus / refresh全部整合到执行器里面了，变成了/ actuator / bus-refresh
 */
@SpringBootApplication
public class MicroserviceConfigClientBus8082Application {
    public static void main( String[] args ){
    	SpringApplication.run(MicroserviceConfigClientBus8082Application.class, "--server.port=8082");
    }
  
}
