package com.itmuch.cloud.microserviceprovideruser.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 所有的请求,都需要经过HTTP basic认证
		http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		//明文编码器,这是一个不做任何操作的密码编码器，是spring提供给我们做明文测试的
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder());
	}
}

@Component
class CustomUserDetailsService implements UserDetailsService {
	/**
	 * 模拟两个帐号:
	 *   帐号user,密码password1,角色是user-role
	 *   帐号admin,密码password2,角色是admin-role
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if("user".equals(username)) {
			System.out.println("user is come in ...");
			return new SecurityUser("user","password1","user-role");
		}else if("admin".equals(username)) {
			return new SecurityUser("admin","password2","admin-role");
		}
		return null;
	}
	
}

class SecurityUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String role;

	public SecurityUser(String username,String password,String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.role);
		authorities.add(authority);
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
