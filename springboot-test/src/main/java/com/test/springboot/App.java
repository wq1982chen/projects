package com.test.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.test.springboot.mapper")	//M3
public class App 
{
	public static void main(String[] args) {
//		SpringApplication app = new SpringApplication();
//		app.setWebApplicationType(WebApplicationType.NONE);
		// 参数
		/*
		 	--spring.config.name 显示的指定配置文件名
		 	--spring.config.location 显示的指定配置文件路径,以逗号分隔的目录位置或文件路径列表
		 		例如--spring.config.location=classpath:/default.properties,classpath:/override.properties
		 */
		SpringApplication.run(App.class, args); // args 参数可以通过ApplicationArguments访问
	}
}
