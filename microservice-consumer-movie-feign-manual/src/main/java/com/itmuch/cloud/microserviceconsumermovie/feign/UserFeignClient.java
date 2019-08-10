package com.itmuch.cloud.microserviceconsumermovie.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.itmuch.cloud.microserviceconsumermovie.model.User;

/**
 * 手工调用,要去掉@FeignClient
 * @author Administrator
 *
 */
//@FeignClient(name="microservice-provider-user")
public interface UserFeignClient {

	@GetMapping("/{id}")
	public User findById(@PathVariable("id") Long id);
}
