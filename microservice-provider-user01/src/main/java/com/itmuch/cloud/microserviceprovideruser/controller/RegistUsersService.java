package com.itmuch.cloud.microserviceprovideruser.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.itmuch.cloud.microserviceprovideruser.dao.RegistUsersDAO;
import com.itmuch.cloud.microserviceprovideruser.pojo.RegistUsers;

@RestController
public class RegistUsersService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistUsersService.class);
	
	@Autowired
	RegistUsersDAO dao;
	
	@GetMapping("/{id}")
	public RegistUsers findById(@PathVariable Long id) {
		return dao.findById(id).get();
	}
	
	@GetMapping("/userAuth")
	public RegistUsers findUserByNameAndPassword(String name,String password) {
		LOGGER.info("User Info : {},{}", name,password);
		RegistUsers user = new RegistUsers();
		user.setId(-1L);
		user.setUserName(name);
		user.setPassWord(password);
		return user;
	}
	
	@PostMapping("/save")
	public RegistUsers save(@RequestBody RegistUsers user) {
		System.out.println("-------------Post User--------------" + user.getUserName());
		return user;
	}
}
