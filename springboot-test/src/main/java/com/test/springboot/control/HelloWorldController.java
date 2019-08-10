package com.test.springboot.control;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.test.springboot.dao.model.User;

/**
 *  需要引用spring-boot-starter-web后才能使用RestController注解
 * @author Administrator
 */
@RestController
public class HelloWorldController {

	@RequestMapping("/hello")
	public String index() {
		return "Hello World";
	}
	
	@RequestMapping("/getUser")
	public User getUser() {
		User u = new User();
		u.setName("Kevin");
		u.setPasswd("mypassword");
		return u;
	}
	
	@RequestMapping("/get")
	public User getUserById(@RequestParam(name="id") Integer userid) {
		User u = new User();
		if( userid == 1 ) {
			u.setName("Kevin");
			u.setPasswd("KevinPasswd");
		}else {
			u.setName("HeHong");
			u.setPasswd("HeHongPasswd");
		}
		return u;
	}
	
	
	/**
	 * 在浏览器中运行
	 * @param session
	 * @return
	 */
	@RequestMapping("/uid")
	public String uid(HttpSession session) {
		
		 UUID uid = (UUID) session.getAttribute("uid");
		 if (uid == null) {
	            uid = UUID.randomUUID();
	        }
	     session.setAttribute("uid", uid);
	     return session.getId();
	}
	
	/**
	 *  功能描述: 测试restful 协议， 从路径中获取字段
	 * @param city_id
	 * @param user_id
	 * @return
	 */
	@GetMapping("/{city_id}/{user_id}")
	public String findObject(@PathVariable("city_id") String city_id, @PathVariable("user_id") String user_id) {
	     return "[CityID:" + city_id + ", UserID:" + user_id+"]";
	}
}
