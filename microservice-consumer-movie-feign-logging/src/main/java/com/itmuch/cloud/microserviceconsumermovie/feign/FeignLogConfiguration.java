package com.itmuch.cloud.microserviceconsumermovie.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;

@Configuration
@ExcludeComponent
public class FeignLogConfiguration {

	@Bean
	public Logger.Level feignLoggerLevel(){
		return Logger.Level.BASIC;
	}
	
	/**
	 * 有些服务需要验证后才能调用的
	 * @return
	 */
	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
		/*
		 *  在服务提供方配置了两个用户:
		 *  user/password1
		 *  admin/password2
		 *  
		 *  对应服务提供方项目是
		 *  microservice-provider-user-with-auth
		 */
		//return new BasicAuthRequestInterceptor("user2","password1");
		return new BasicAuthRequestInterceptor("user","password1");
	}
}
