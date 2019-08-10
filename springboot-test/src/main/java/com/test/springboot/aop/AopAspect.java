package com.test.springboot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Aspect
public class AopAspect {
	
	@Before("execution(* com.test.springboot.dao.*.*(..))")
	@Order(value=2) //多个注解存在时的执行排序
	public void sayBegin() throws Throwable {
		System.out.println("======= AOP @Before Aspect Begin =======");
	}

	@Around("execution(* com.test.springboot.dao.*.*(..))")
	public Object sayRound(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("======= AOP @Around Aspect Begin =======");
		Object[] args = joinPoint.getArgs();
		System.out.println("======= AOP @Around=======ProceedingJoinPoint是 " + joinPoint.toString());
		System.out.println("======= AOP @Around=======目标方法的传入参数是 " + args);
		Object rvt = joinPoint.proceed(args); //让下一个advise或方法调用可以执行
		System.out.println("======= AOP @Around Complete =======");
		return rvt;
	}
}
