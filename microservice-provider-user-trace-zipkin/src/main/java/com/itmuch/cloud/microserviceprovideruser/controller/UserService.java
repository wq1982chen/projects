package com.itmuch.cloud.microserviceprovideruser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.itmuch.cloud.microserviceprovideruser.dao.UserRepository;
import com.itmuch.cloud.microserviceprovideruser.pojo.User;

@RestController
public class UserService {

	//private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/{id}")
	public User findById(@PathVariable Long id) {
		return userRepository.findById(id).get();
	}
}
