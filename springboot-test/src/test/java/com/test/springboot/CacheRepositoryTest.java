package com.test.springboot;

import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.util.Date;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.test.springboot.dao.PersonService;
import com.test.springboot.dao.UserRepository;
import com.test.springboot.dao.model.Person;
import com.test.springboot.dao.model.RegistUser;

@RunWith(SpringJUnit4ClassRunner.class)
//较新版的Spring Boot取消了@SpringApplicationConfiguration这个注解，用@SpringBootTest
@SpringBootTest
public class CacheRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * 测试JPA方式的MySQL存储
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);        
		String formattedDate = dateFormat.format(date);
		//System.out.println(formattedDate);
		userRepository.save(new RegistUser("dd1", "dd1@126.com", "dd", "dd123456",formattedDate));
		userRepository.save(new RegistUser("ee1", "ee1@126.com", "ee", "ee123456",formattedDate));
		userRepository.save(new RegistUser("ff1", "ff1@126.com", "ff", "ff123456",formattedDate));
		userRepository.save(new RegistUser("gg1", "gg1@126.com", "gg", "gg123456",formattedDate));
		userRepository.save(new RegistUser("hh1", "hh1@126.com", "hh", "hh123456",formattedDate));
		userRepository.save(new RegistUser("ii1", "ii1@126.com", "ii", "ii123456",formattedDate));
		Assert.assertEquals(9, userRepository.findAll().size());
		Assert.assertEquals("bb", userRepository.findByUserNameOrEmail("bb", "cc@126.com").getNickName());
		//userRepository.delete(userRepository.findByUserName("aa1"));
	}
	
	/**
	 * 测试数据库方式的分页
	 * 先得造点测试数据
	 */
	@Test
	public void testPageQuery() {
		
		int page=0,size=5;  //第一页的页数为0，这是个坑
		Sort sort = new Sort(Direction.DESC, "id");
	    Pageable pageable =PageRequest.of(page, size, sort);
	    Page<RegistUser> userPage = userRepository.findAll(pageable);
	    System.out.println("Page Row size :" + userPage.getTotalPages());
	    
	    // method 1
	    userPage.get().forEach(new Consumer<RegistUser>() {

			@Override
			public void accept(RegistUser user) {
				//System.out.println("User: Name[" +user.getUserName() + "],EMIAL[" + user.getEmail() + "]"); 
			}
		});
	    // method 2
//		  Iterator<RegistUser> it = userPage.iterator() ; 
//		  while(it.hasNext()) {
//			  RegistUser user = it.next(); 
//			  System.out.println("User: Name["+user.getUserName() + "],EMIAL[" + user.getEmail() + "]"); 
//		  }
	    // method 3
	    for( RegistUser user : userPage.getContent()) {
	    	System.out.println("User: Name[" + user.getUserName() + "],EMIAL[" + user.getEmail() + "]");
	    }
	    System.out.println("Output Over .");
	  //  userRepository.findByUserName("testName", pageable);
	}
	
	/**
	 * 测试数据库删除
	 */
	@Test
	public void testJPAUpdate() {
		userRepository.updateUserById(10L, "Kevin");
	}
	
	@Autowired
	private PersonService personService;
	//private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 测试将数据同时放MySQL和Redis
	 */
	@Test
	public void testDiffRepository() {
		// 先存数据库,再存缓存
		Person person = new Person();
		person.setName("Kevin");
		person.setAge(36);
		person.setWeight(85.5);
		//System.out.println("Id : " + person.getId());
		//System.out.println("Name : " + person.getName());
		personService.savePerson(person);
	}
	
	/**
	 * 测试先取缓存再查数据库的例子
	 */
	@Test
	public void testCacheData() {
		Person p = personService.getPerson(16L);
		assertNotNull(p);
	}
}
