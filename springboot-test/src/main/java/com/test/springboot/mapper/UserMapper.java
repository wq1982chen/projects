package com.test.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.test.springboot.mapper.entity.UserEntity;
import com.test.springboot.util.UserSexEnum;

public interface UserMapper {

	/*
	@Select 是查询类的注解，所有的查询均使用这个
		@Result 修饰返回的结果集，关联实体类属性和数据库字段一一对应，如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰。
		@Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值
	@Update 负责修改，也可以直接传入对象
	@delete 负责删除
	 */
	 //org.apache.ibatis.type.EnumTypeHandler 默认,取枚举的名称
	//org.apache.ibatis.type.EnumOrdinalTypeHandler 取枚举的位置
	// 如果想取枚举的值存入数据库,则需要自己实现类型处理器,创建一个自定义的类型处理器,只需要继承BaseTypeHandler
	@Select("SELECT id,user_name,nick_name,pass_word,user_sex FROM users")
	@Results({
		@Result(property = "userName", column = "user_name"),
		@Result(property = "nickName", column = "nick_name"),
		@Result(property = "passWord", column = "pass_word"),
		@Result(property = "userSex",  column = "user_sex", 
		javaType = UserSexEnum.class,
		typeHandler=com.test.springboot.util.MyEnumTypeHandler.class)
		//typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler.class)
	})
	List<UserEntity> getAll();
	
	@Insert("INSERT INTO users(id,user_name,nick_name,pass_word,user_sex) "
			+ "VALUES(#{id},#{userName},#{nickName}, #{passWord}, "
			+ "#{userSex,typeHandler=com.test.springboot.util.MyEnumTypeHandler})")
			//+ "#{userSex,typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler})")
	void insert(UserEntity user);
	
	@Update("UPDATE users SET user_name=#{userName},nick_name=#{nickName} WHERE id =#{id}")
	void update(UserEntity user);

	@Delete("DELETE FROM users WHERE id =#{id}")
	void delete(Long id);

}
