package com.test.springboot.dao;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.test.springboot.dao.model.Person;

/**
 * 注解说明
 * https://www.cnblogs.com/fashflying/p/6908028.html
 * @author Administrator
 *
 */
public interface PersonService {

		
	    @CachePut(key="#result.id +''",value="person")
		Person savePerson(Person person);
	    
	    @Cacheable(key="#id +''",value="person")
	    Person getPerson(Long id);

}
