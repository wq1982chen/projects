package com.itmuch.cloud.microserviceconsumermovie.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.itmuch.cloud.microserviceconsumermovie.pojo.RegistUser;
import com.itmuch.cloud.microserviceconsumermovie.pojo.User;

/**
 * 测试Ribbon
 * @author Administrator
 *
 */
@RestController
public class MovieRibbonController {

   private static final Logger LOGGER = LoggerFactory.getLogger(MovieRibbonController.class);
	 
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/user/{id}") //负载均衡
    public RegistUser findById(@PathVariable Long id) {
    	//不能将loadBalancerClient.choose和restTemplate.getForObject写在一个方法内
    	//两者会有冲突,因为此时的restTemplate实际是一个客户端,本身已经包含了"choose"行为
        return this.restTemplate.getForObject("http://microservice-provider-user/" + id, RegistUser.class);
    }
    
    @GetMapping("/{id}") //负载均衡
    public User findUserById(@PathVariable Long id) {
    	//不能将loadBalancerClient.choose和restTemplate.getForObject写在一个方法内
    	//两者会有冲突,因为此时的restTemplate实际是一个客户端,本身已经包含了"choose"行为
        return this.restTemplate.getForObject("http://microservice-provider-user/" + id, User.class);
    }
    
    /**
     * 演示多参数传递,代码看起比较冗余,引入Feign
     * @param name
     * @param age
     * @return
     */
    public RegistUser findByAttributes(String name,String age) {
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("name",name);
    	paramMap.put("age",age);
        return this.restTemplate.getForObject("http://microservice-provider-user/search?name={name}&age={age}",
        																				RegistUser.class,paramMap);
    }
    
    @GetMapping("/log-instance")
    public void logUserInstance() {
    	//microservice-provider-user 是微服务的虚拟主机名
    	//当Ribbon与Eureka配合使用的时候,可以将虚拟主机名映射成微服务的网络地址
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("microservice-provider-user");
        // 打印当前选择的是哪个节点
        MovieRibbonController.LOGGER.info("{}:{}:{}", 
        			serviceInstance.getServiceId(), serviceInstance.getHost(),serviceInstance.getPort());
    }
    
}
