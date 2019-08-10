package com.itmuch.cloud.microserviceconsumermovie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.itmuch.cloud.microserviceconsumermovie.feign.UserFeignClient;
import com.itmuch.cloud.microserviceconsumermovie.model.User;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;

@Import(FeignClientsConfiguration.class)
@RestController
public class MovieController {

	private UserFeignClient userFeignClient;
	
	@Autowired
	public MovieController(Decoder decoder,
													 Encoder encoder,
													 Client client,
													 Contract contract) {
		this.userFeignClient = Feign.builder().client(client).encoder(encoder)
				.decoder(decoder).contract(contract)
				.requestInterceptor( new BasicAuthRequestInterceptor("user", "password1"))
				.target(UserFeignClient.class,"http://microservice-provider-user/");
	}
    
    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id) {
    	
    	return userFeignClient.findById(id);
    }
    
}
