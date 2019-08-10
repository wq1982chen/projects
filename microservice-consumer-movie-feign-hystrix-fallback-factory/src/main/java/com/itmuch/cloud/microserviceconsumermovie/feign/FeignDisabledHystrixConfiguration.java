package com.itmuch.cloud.microserviceconsumermovie.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import feign.Feign;

/**
 * 
 * Hystrix只要在ClassPath中,Feign就会使用断路器包裹Feign客户端的所有方法
 * 虽然这样很方便,但是在有些场景下可能不需要该功能,
 * 通过自定义配置类，可以轻松为指定名称的Feign客户端禁用Hystrix
 * 下面这个类定义好后,在FeignClient的Configuration中指定该类就可以达到目的
 * 或在application.yml中增加配置:
 * feign.hystrix.enabled = false
 * 
 * @author Administrator
 *
 */
@Configuration
public class FeignDisabledHystrixConfiguration {

	@Bean
	@Scope("prototype")
	public Feign.Builder feignBuild(){
		return Feign.builder();
	}
}
