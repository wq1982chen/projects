package com.test.springboot.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 
 * 默认加载application.properties
 * 例如非application.properties，需要通过@PropertySource指定配置文件位置
 * 例如: classpath:config/my.properties指的是src/main/resources目录下config目录下的my.properties文件,
 * 例如有多配置文件引用，若取两个配置文件中有相同属性名的值，则取值为最后一个配置文件中的值
 * 在application.properties中的文件，直接使用@Value读取即可，application的读取优先级最高
 * @author Administrator
 */
@Component
@PropertySource({"classpath:redis.properties"})
@ConfigurationProperties(prefix="spring.redis.pool")
public class RedisConfigDefined {

	
	private int maxIdle;
	private int minIdle;
	private int maxActive;
	private int timeout;
	
	@Value("${spring.redis.sentinel.master}")
	private String master;
	@Value("#{'${spring.redis.sentinels}'.split(',')}")
	private List<String> sentinels;
	
	public int getMaxIdle() {
		return maxIdle;
	}
	
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public List<String> getSentinels() {
		return sentinels;
	}

	public void setSentinels(List<String> sentinels) {
		this.sentinels = sentinels;
	}
	
}
