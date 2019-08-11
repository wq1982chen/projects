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
 */
@SpringBootApplication
public class MicroserviceConfigClientApplication {
    public static void main( String[] args ){
    	SpringApplication.run(MicroserviceConfigClientApplication.class, args);
    }
  
}
