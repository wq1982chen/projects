package com.itmuch.cloud.microserviceconsumermovie.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.itmuch.cloud.microserviceconsumermovie.model.User;

@FeignClient(name="microservice-provider-user")
public interface UserFeignClient {

	@GetMapping("/{id}")
	public User findById(@PathVariable("id") Long id);
	
	/**
	 * 多参数示例
	 * @param id
	 * @param username
	 * @return
	 */
	@GetMapping("/get")
	public User get1(@RequestParam("id") Long id , @RequestParam("username") String username);
	
	/**
	 * 多参数使用map传递示例
	 * @param id
	 * @param username
	 * @return
	 */
	@GetMapping("/get2")
	public User get2(@RequestParam Map<String,Object> map);
	
	/**
	 * 多参数Post传递
	 * @param id
	 * @param username
	 * @return
	 */
	@PostMapping("/post")
	public User post(@RequestBody User usr);
}
