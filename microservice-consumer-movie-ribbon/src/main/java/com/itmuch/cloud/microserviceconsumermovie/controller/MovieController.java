package com.itmuch.cloud.microserviceconsumermovie.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.itmuch.cloud.microserviceconsumermovie.health.MyHealthChecker;
import com.itmuch.cloud.microserviceconsumermovie.model.User;

@RestController
public class MovieController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private RestTemplate restTemeplate;
    
    @Autowired
    private MyHealthChecker myHealthChecker; 
    
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    
    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
    	return this.restTemeplate.getForObject("http://microservice-provider-user/"+id, User.class);
    }
    
    @GetMapping("/log-instance")
    public void logUserInstance() {
    	//microservice-provider-user 是微服务的虚拟主机名
    	//当Ribbon与Eureka配合使用的时候,可以将虚拟主机名映射成微服务的网络地址
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("microservice-provider-user");
        // 打印当前选择的是哪个节点
        MovieController.LOGGER.info("{}:{}:{}", 
        			serviceInstance.getServiceId(), serviceInstance.getHost(),serviceInstance.getPort());
    }
    
    /**
     * 设置微服务状态为down
     * http://localhost:8200/up?up=false
     * @param up
     * @return
     */
    @GetMapping("/up")
    public String up(@RequestParam Boolean up) {
    	myHealthChecker.setUp(up);
    	return up.toString();
    }
    
//    /**
//     * 多个参数传递
//     * @param name
//     * @param password
//     * @return
//     */
//    @GetMapping("/feign/user/{name}/{password}")
//    public RegistUser findUserByNP(@PathVariable("name") String name,
//    																	@PathVariable("password") String password) {
//    	System.out.println("Feign Come in ... ... @PathVariable");
//        return this.userFeignClient.findUserByNameAndPassword(name,password);
//    }
    
//    @GetMapping("/feign/get")//最直观的方法，URL有几个参数，Feign接口就有几个参数
//    public RegistUser getUser(@RequestParam("name") String name,
//    													 @RequestParam("password") String password) {
//    	System.out.println("Feign Come in ... ... @RequestParam");
//        return this.userFeignClient.findUserByNameAndPassword(name,password);
//    }
//    
//    @GetMapping("/feign/get2")//目标URL参数非常多时，使用Map构建可简化Feign接口的编写
//    public void getUser2(@RequestParam Map<String,Object> map) {
//    	System.out.println("Feign Come in ... ... @RequestParam Map:" + map );
//    }
//    
//    @PostMapping("/post")
//    public RegistUser post(@RequestBody RegistUser user) {//@RequestBody 是对传入json使用的
//    	System.out.println("Consumer post : " + user.getUserName());
//    	return this.userFeignClient.save(user);
//    }
    
}
