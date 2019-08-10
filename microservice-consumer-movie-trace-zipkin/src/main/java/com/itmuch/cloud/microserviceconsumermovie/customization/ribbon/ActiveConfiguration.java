package com.itmuch.cloud.microserviceconsumermovie.customization.ribbon;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

/**
 * 使自定义配置生效,只需要声明一个空类,配置注解即可
 * 打开@Configuration后生效
 */
//@Configuration
@RibbonClient(name="microservice-provider-user",configuration=RibbonConfiguration.class)
public class ActiveConfiguration {}
