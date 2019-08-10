package com.itmuch.cloud.microserviceconsumermovie.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.itmuch.cloud.microserviceconsumermovie.feign.UserFeignClient;
import com.itmuch.cloud.microserviceconsumermovie.pojo.RegistUser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class MovieHystrixController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieHystrixController.class);
	
    @Autowired
    private UserFeignClient userFeignClient;
    
    // //当使用Feign自身整合的Hystri方法时,这个注解被屏蔽
    
    /**
     * 多个参数传递
     * @param name
     * @param password
     * @return
     */
    @HystrixCommand(fallbackMethod = "findByIdFallback")
    @GetMapping("/hystrix/user/{name}/{password}")
    public RegistUser findUserByNP(@PathVariable("name") String name,
    																	@PathVariable("password") String password) {
    	System.out.println("Feign Come in ... ... @PathVariable");
        return this.userFeignClient.findUserByNameAndPassword(name,password);
    }
    
  
    /**
     * https://www.cnblogs.com/java-synchronized/p/7927726.html
     * 解释了熔断的隔离机制:线程池模式和信号量模式
     * @param name
     * @param password
     * @return
     */
    public RegistUser findByIdFallback(String name,String password) {
        LOGGER.warn("请求异常，执行回退方式");
        RegistUser user = new RegistUser();
        user.setId(-1L);
        user.setUserName(name);
        user.setPassWord(password);
        user.setNickName("回退用户");
        return user;
    }

    
}
