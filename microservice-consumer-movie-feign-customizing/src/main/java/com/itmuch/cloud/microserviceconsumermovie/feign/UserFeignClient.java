package com.itmuch.cloud.microserviceconsumermovie.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.itmuch.cloud.microserviceconsumermovie.model.User;

import feign.Param;
import feign.RequestLine;

@FeignClient(name="microservice-provider-user",
	configuration=CustomizingFeignConfiguration.class)
public interface UserFeignClient {

	
	/**
	 * 修改了契约,这里就不再使用SpringMVC的注解了
	 * 使用Feign自带的注解@RequestLine
	 * @param id
	 * @return
	 */
	//@GetMapping("/{id}") 
	@RequestLine("GET /{id}")
	public User findById(@Param("id") Long id);
}
