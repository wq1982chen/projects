package com.acca.dubbo.action;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.acca.dubbo.service.TestService;

public class ConsumerAction {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext(new String[] { "application.xml" });
		context.start();
		System.out.println("consumer start");
		TestService testService = (TestService) context.getBean("testService");
		
		System.out.println(testService.say("dubbo"));
		System.out.println(testService.getPermissions(1L));
		
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
