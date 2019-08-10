package com.test.springboot.event;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 需要在META-INF/spring.factories中配置监听
 * @author Administrator
 *
 */
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
			System.out.println("-------------------ApplicationStartedEvent---------------------");
	}

}
