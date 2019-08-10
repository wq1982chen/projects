package com.itmuch.cloud.microserviceconsumermovie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.itmuch.cloud.microserviceconsumermovie.pojo.User;

/**
 * 测试服务发现中的微服务信息显示
 *
 */
@RestController
public class MovieServiceInstanceController {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	@GetMapping("user-instance")
	public List<ServiceInstance> showInfo(){
		return discoveryClient.getInstances("microservice-provider-user");
	}
	

	@GetMapping("/moiveuser/{id}")
	public User getMoiveUser(@PathVariable("id") Long id) {
		User moiveUser = new User();
		moiveUser.setId(id);
		moiveUser.setName("Moive User");
		return moiveUser;
	}
    
}
