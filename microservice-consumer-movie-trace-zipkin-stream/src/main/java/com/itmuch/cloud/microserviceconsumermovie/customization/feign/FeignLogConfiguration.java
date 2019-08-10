package com.itmuch.cloud.microserviceconsumermovie.customization.feign;

import org.springframework.context.annotation.Configuration;

import feign.Logger;

/**
 * 为了Feign客户端配置各自的Logger.Level对象，告诉Feign记录哪些日志
 * Logger.Level的值有以下选择:
 * NONE 不记录任何日志(默认值)
 * BASIC 仅记录方法、URL、响应状态码和执行时间
 * HEADERS:在BASIC的基础上，记录请求和响应的HEADER
 * FULL:记录请求和响应的head,body和元数据
 * 
 */
/**
 * 该类为Feign的配置类
 * 注意：该类不应该在主应用程序上下文的@CompantScan中
 */
//@ExcludeComponent
@Configuration
public class FeignLogConfiguration {

	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}
}
