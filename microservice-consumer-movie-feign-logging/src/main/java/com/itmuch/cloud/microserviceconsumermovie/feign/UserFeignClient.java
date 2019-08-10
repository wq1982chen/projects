package com.itmuch.cloud.microserviceconsumermovie.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.itmuch.cloud.microserviceconsumermovie.model.User;

/**
 * 在这里指明日志配置类外,在application.yml指定Feign接口的日志级别
 * @author Administrator
 *
 */
@FeignClient(name="microservice-provider-user",configuration=FeignLogConfiguration.class)
public interface UserFeignClient {

	@GetMapping("/{id}")
	public User findById(@PathVariable("id") Long id);
}
