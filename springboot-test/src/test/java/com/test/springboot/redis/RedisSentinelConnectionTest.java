package com.test.springboot.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.springboot.dao.UserService;
import com.test.springboot.dao.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisSentinelConnectionTest {

	@Autowired
	private StringRedisTemplate template;
 
	@Autowired
	private UserService userService;

	
	@Test
	public void get() {
		//template.opsForValue().set("test", "我是地瓜");
		System.out.println(template.opsForValue().get("test"));
	}
	
//	@Test
//	public void contextLoads() {
//		User user = new User();
//		user.setId("1");
//		user.setAge(12);
//		user.setName("张三");
//		template.opsForValue().append("date", user.toString());
//		System.out.println(template.opsForValue().get("date"));
//	}
//	
//	@Test
//	public void getUser() {
//		for (int i = 0; i < 10; i++) {
//			User user = userService.getUser(String.valueOf(i));
//			System.out.println(user);
//		}
//	}
//	
	@Test
	public void find() {
		
		User user = userService.findUser("10000");
		System.out.println(user);
	}

}
