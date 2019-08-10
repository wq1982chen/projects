package com.test.springboot.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.springboot.dao.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class WebFluxController {

	@Autowired
	private UserServiceFlux userServiceFlux;
	/**
	 * 和之前 Spring Mvc 的模式差别不是很大，只是在方法的返回值上有所区别
	 * @return
	 */
	@GetMapping("/hellom")
	public Mono<String> hello(){
		//just() 方法可以指定序列中包含的全部元素
		//响应式编程的返回值必须是 Flux 或者 Mono ，两者之间可以相互转换。
		return Mono.just("Welcome to reactive world ~");
	}
	
	@GetMapping("")
	public Flux<User> list(){
		return userServiceFlux.list();
	}
	
	@GetMapping("/{id}")
	public Mono<User> getById(@PathVariable Integer id){
		return userServiceFlux.getById(id);
	}
}
