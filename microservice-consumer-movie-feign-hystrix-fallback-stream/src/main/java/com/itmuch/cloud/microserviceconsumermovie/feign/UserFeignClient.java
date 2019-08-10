package com.itmuch.cloud.microserviceconsumermovie.feign;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.itmuch.cloud.microserviceconsumermovie.model.User;

/**
 * SpringCloud已经默认为Feign整合了Hystrix
 * 
 *  测试Feign的Fallback指定的回退类
 *  通过 http://localhost:8010/actuator/health 查看hystrix.status状态UP/CIRCUIT_OPEN..
 *  补充: 在引入Actuator后,还需要在Application.yml配置management.endpoint.health.show-details: always,这样才能查看到断路器状态
 * @author Administrator
 *
 */
/*
 * fallback: 配置回退方法
 *	fallbackFactory: 除配置回退方法外,还可打印熔断原因
 * 使用上面两个annotation 都需要在配置文件中增加 feign.hystrix.enabled: true 的配置
 * 两者只能同时配置一个
 */
@FeignClient(name="microservice-provider-user",fallback = FeignClientFallback.class)
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
