package com.test.springboot.event;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 需要在META-INF/spring.factories中配置监听
 * @author Administrator
 *
 */
public class ApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {

	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
			System.out.println("-------------------ApplicationPreparedEvent---------------------");
	}

}
