package com.test.springboot;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.springboot.mapper.UserMapper;
import com.test.springboot.mapper.entity.UserEntity;
import com.test.springboot.util.UserSexEnum;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest    extends TestCase {
	
	@Autowired
	 private UserMapper userMapper;
	
	/**
	 * 测试数据插入
	 * @throws Exception
	 */
	@Test
	public void testInsert() throws Exception {
		userMapper.insert(createUser());
		List<UserEntity> users = userMapper.getAll();
		Assert.assertEquals(1, users.size());
		for( UserEntity user : users ) {
			System.out.println(user.getUserSex().name());
			System.out.println(user.getUserSex().value());
		}
		
	}
	
	@Test
	public void testUpdate() throws Exception {
		UserEntity userEntity = createUser();
		userEntity.setUserName("Ronaldo");
		userEntity.setNickName("Goal");
		userMapper.update(userEntity);
	}
	
	@Test
	public void testDelete() throws Exception {
		userMapper.delete(1L);
	}
	
	private UserEntity createUser() {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(1L); //主键
		userEntity.setNickName("SX");
		userEntity.setUserName("Nice");
		userEntity.setUserSex(UserSexEnum.MAN);
		userEntity.setPassWord("Password");
		return userEntity;
	}
	
}
