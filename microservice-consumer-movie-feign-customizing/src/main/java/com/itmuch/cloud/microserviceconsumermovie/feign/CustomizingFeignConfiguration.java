package com.itmuch.cloud.microserviceconsumermovie.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Contract;
import feign.auth.BasicAuthRequestInterceptor;

/**
 *  该类为Feign的配置类
 *  注意:不能被@ComponentScan给扫描到,否则将作用于整个应用
 * @author .KW.
 *
 */
@Configuration
@ExcludeComponent
public class CustomizingFeignConfiguration {

	/**
	 * 将契约改为feign原生的默认契约,这样就可以使用feign自带的注解了
	 * 下一步,将UserFeignClient指定该配置类
	 */
	@Bean
	public Contract feignContract() {
		return new feign.Contract.Default();
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
