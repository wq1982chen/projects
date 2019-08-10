package com.test.springboot.webflux;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.test.springboot.dao.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class UserServiceFlux {

	private final Map<Integer, User> data = new ConcurrentHashMap<Integer, User>();
	
	@PostConstruct
	private void init() {
		
		User u1 = new User();
		u1.setId("1");
		u1.setName("张三");
		
		User u2 = new User();
		u1.setId("1");
		u1.setName("李四");
		
		User u3 = new User();
		u1.setId("1");
		u1.setName("王五");
		
		data.put(1, u1);
		data.put(2, u2);
		data.put(3, u3);
	}
	
	public Flux<User> list(){
		return Flux.fromIterable(data.values());
	}

	public Mono<User> getById(Integer id) {
		return Mono.justOrEmpty(this.data.get(id)).switchIfEmpty(Mono.error(new ResourceNotFoundException("User Not Found")));
	}
}
