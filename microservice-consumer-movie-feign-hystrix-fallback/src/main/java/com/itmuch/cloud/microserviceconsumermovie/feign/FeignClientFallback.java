package com.itmuch.cloud.microserviceconsumermovie.feign;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.itmuch.cloud.microserviceconsumermovie.model.User;

@Component
public class FeignClientFallback implements UserFeignClient{

	@Override
	public User findById(Long id) {
		User usr = new User();
    	usr.setId(-1L);
    	usr.setName("默认用户");
    	return usr;
	}

	@Override
	public User get1(Long id, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User get2(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User post(User usr) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
