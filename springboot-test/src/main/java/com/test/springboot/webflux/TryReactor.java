package com.test.springboot.webflux;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import reactor.core.publisher.Mono;

/**
 * 添加 
 * <dependency>
 * 	<groupId>org.springframework.boot</groupId>
 * 	<artifactId>spring-boot-starter-webflux</artifactId>
 * </dependency>
 * @author Administrator
 *
 */
public class TryReactor {

	//显示当前时间
	static void displayCurrTime(int point) {
		System.out.println(point + " : " + LocalTime.now());
	}
	
	//显示当前线程Id
	static void displayCurrThreadId(int point) {
		System.out.println(point + " : " + Thread.currentThread().getId());
	}
	
	//显示当前的数值
	static void displayValue(int n) {
		System.out.println("input : " + n);
	}
	
	//延迟若干秒
	static void delaySeconds(int seconds) {
		 try {
			 TimeUnit.SECONDS.sleep(seconds);
		 } catch (InterruptedException e) {
			 e.printStackTrace();
		 }
	}
	
	//主线程暂停
	static void pause() {
		 try {
			 System.out.println("main Thread over, paused.");
			 System.in.read();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	}
	
	public static void main(String[] args) {
		
		displayCurrTime(1);
		displayCurrThreadId(1);
		//创建一个数据
		Mono.just(10)
		.delayElement(Duration.ofSeconds(5)) //延迟5秒
		.map( n -> {
			 displayCurrTime(2);
			 displayCurrThreadId(2);
			 displayValue(n);
			 delaySeconds(2);
			 return n + 1;
		})
		//在数据上执行一个过滤
		 .filter(n -> {
		 displayCurrTime(3);
		 displayCurrThreadId(3);
		 displayValue(n);
		 delaySeconds(3);
		 return n % 2 == 0;
		 })
		//如果数据没了就用默认值
		 .defaultIfEmpty(9)
		 //订阅一个消费者把数据消费了
		 .subscribe(n -> {
			 displayCurrTime(4);
			 displayCurrThreadId(4);
			 displayValue(n);
			 delaySeconds(2);
			 System.out.println(n + " consumed, worker Thread over, exit.");
			 });
		
		displayCurrTime(5);
		displayCurrThreadId(5);
		pause();
	}
}
