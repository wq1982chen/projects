package com.test.springboot.mapper.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.test.springboot.util.UserSexEnum;

@Entity
@Table(name="users")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	private Long id;
	@Column
	private String passWord;
	@Column
	private String userName;
	@Column
	private String nickName;
	@Column
	private UserSexEnum userSex;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public UserSexEnum getUserSex() {
		return userSex;
	}
	
	public void setUserSex(UserSexEnum userSex) {
		this.userSex = userSex;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	
}
