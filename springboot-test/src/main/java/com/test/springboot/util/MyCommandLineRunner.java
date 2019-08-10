package com.test.springboot.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		/*
		可以实现 ApplicationRunner 或者 CommandLineRunner 接口。
		这两个接口的工作方式是一样的
		它将在 SpringApplication.run(​...) 完成之前调用。
		如果定义了多个CLR , 可以通过
		注解 org.springframework.core.annotation.Order 或
		接口 org.springframework.core.Ordered 来解决排序问题
		*/
		System.out.println("-------------------------My command line runner is run -------------------------" + args.toString());
	}

}
