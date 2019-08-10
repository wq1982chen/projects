package com.test.springboot.dao;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.test.springboot.dao.model.RegistUser;

public interface UserRepository extends JpaRepository<RegistUser, Long> {

	//自定义的简单查询就是根据方法名来自动生成SQL
	//主要的语法是findXXBy,readAXXBy,queryXXBy,countXXBy, getXXBy后面跟属性名称
	RegistUser findByUserName(String userName);
	RegistUser findByUserNameOrEmail(String username, String email);
	
	Page<RegistUser> findAll(Pageable pageable);
    
	@Transactional
	@Modifying
	@Query("update RegistUser u set u.userName = ?2 where u.id = ?1")
	void updateUserById(Long id,String userName);
}
