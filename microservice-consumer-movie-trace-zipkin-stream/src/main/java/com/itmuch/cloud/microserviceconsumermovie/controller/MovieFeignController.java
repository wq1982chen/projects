package com.itmuch.cloud.microserviceconsumermovie.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itmuch.cloud.microserviceconsumermovie.feign.UserFeignClient;
import com.itmuch.cloud.microserviceconsumermovie.pojo.RegistUser;

@RestController
public class MovieFeignController {

    @Autowired
    private UserFeignClient userFeignClient;
    
    /**
     * 多个参数传递
     * @param name
     * @param password
     * @return
     */
    @GetMapping("/feign/user/{name}/{password}")
    public RegistUser findUserByNP(@PathVariable("name") String name,
    																	@PathVariable("password") String password) {
    	System.out.println("Feign Come in ... ... @PathVariable");
        return this.userFeignClient.findUserByNameAndPassword(name,password);
    }
    
    @GetMapping("/feign/get")//最直观的方法，URL有几个参数，Feign接口就有几个参数
    public RegistUser getUser(@RequestParam("name") String name,
    													 @RequestParam("password") String password) {
    	System.out.println("Feign Come in ... ... @RequestParam");
        return this.userFeignClient.findUserByNameAndPassword(name,password);
    }
    
    @GetMapping("/feign/get2")//目标URL参数非常多时，使用Map构建可简化Feign接口的编写
    public void getUser2(@RequestParam Map<String,Object> map) {
    	System.out.println("Feign Come in ... ... @RequestParam Map:" + map );
    }
    
    @PostMapping("/post")
    public RegistUser post(@RequestBody RegistUser user) {//@RequestBody 是对传入json使用的
    	System.out.println("Consumer post : " + user.getUserName());
    	return this.userFeignClient.save(user);
    }
    
}
