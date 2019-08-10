package com.itmuch.cloud.microserviceconsumermovie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.itmuch.cloud.microserviceconsumermovie.model.User;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@RestController
public class MovieController {

    @Autowired
    private RestTemplate restTemeplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    
    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
    	return this.restTemeplate.getForObject("http://localhost:8000/"+id, User.class);
    }
    
    /**
     * http://localhost:8010/user-instance
     * @return
     */
	@GetMapping("/user-instance")
	public List<ServiceInstance> showInfo(){
		return discoveryClient.getInstances("microservice-provider-user");
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
