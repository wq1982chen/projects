package com.itmuch.cloud.microservice.gateway.customerization;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MyRoute {

	@Bean
	public PreRequestLogFilter getPreRequestLogFilter() {
		System.out.println("======================我的过滤器========================");
		return new PreRequestLogFilter();
	}
}
