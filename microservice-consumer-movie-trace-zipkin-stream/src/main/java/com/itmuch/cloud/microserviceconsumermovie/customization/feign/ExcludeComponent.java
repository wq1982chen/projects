package com.itmuch.cloud.microserviceconsumermovie.customization.feign;

/**
 * 在Feign的自定义配置上申明注解
 * 目的:被此注解注解的类不会被@ComponentScan扫描到
 * @author .WQ.
 *
 */
public @interface ExcludeComponent {}
