package com.itmuch.cloud.microservice.gateway.customerization;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MyRoute {

//	@Value("${pattern.route.enabled}")
//	private String isEnabled; 
	
	@Bean
	@ConditionalOnProperty(value="pattern.route.enabled",havingValue="true",matchIfMissing=false)
	public PatternServiceRouteMapper serviceRouteMapper() {
		/*
		 * 调用构造函数PatternServiceRouteMapper(String servicePattern,String routePattern)
		 * servicePattern 指定微服务的正则
		 * routePattern 指定路由的正则
		 * microservice-provider-user-v1 映射到 /v1/microservice-provider-user/**
		 */
		System.out.println("=========================================================");
		//return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)", "${version}/${name}")  ;
		return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)", "${name}/${version}")  ;
	}
}
