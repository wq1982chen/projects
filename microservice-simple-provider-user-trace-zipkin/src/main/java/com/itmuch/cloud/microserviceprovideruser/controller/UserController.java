package com.itmuch.cloud.microserviceprovideruser.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.itmuch.cloud.microserviceprovideruser.dao.UserRepository;
import com.itmuch.cloud.microserviceprovideruser.pojo.User;

@RestController
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/{id}")
	public User findById(@PathVariable Long id) {
		User usr = userRepository.findById(id).get();
		LOGGER.info("Retrive User Info : {},{}", usr.getName(),usr.getAge());
		return usr;
	}
	
//	@GetMapping("/userAuth")
//	public User findUserByNameAndPassword(String name,String password) {
//		LOGGER.info("User Info : {},{}", name,password);
//		User user = new User();
//		user.setId(-1L);
//		user.setUserName(name);
//		user.setPassWord(password);
//		return user;
//	}
//	
//	@PostMapping("/save")
//	public User save(@RequestBody User user) {
//		System.out.println("-------------Post User--------------" + user.getUserName());
//		return user;
//	}
}
