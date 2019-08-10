package com.test.springboot.services;

import org.springframework.stereotype.Service;

import com.test.springboot.dao.UserService;
import com.test.springboot.dao.model.User;

@Service
public class UserServiceImpl  implements UserService {

	@Override
	public User getUser(String id) {
		System.out.println(id+"进入实现类获取数据！");
		User user = new User();
		user.setId(id);
		user.setName("香菇,难受");
		user.setAge(18);
		return user;
	}

	@Override
	public void deleteUser(String id) {
		System.out.println(id+"进入实现类删除数据！");
	}

	@Override
	public User findUser(String id) {
		User user = new User();
		user.setId(id);
		user.setName("Kevin");
		user.setAge(37);
		System.out.println(id+"进入实现类获取数据！");
		return user;
	}

}
