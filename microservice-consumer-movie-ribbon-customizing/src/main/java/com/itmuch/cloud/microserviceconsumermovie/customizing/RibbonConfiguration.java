package com.itmuch.cloud.microserviceconsumermovie.customizing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

/**
 * 该类为Ribbon的自定义配置类
 * 注意: 该类不应该在主应用程序上下文的的ComponentScan中,否则该类中的配置信息将被所有的RibbonClient共享
 * 共二步,第一步:创建一个自定义的配置类
 */
@Configuration
@ExcludeComponent
public class RibbonConfiguration {

	@Bean
	public IRule ribbonRule() {
		return new RandomRule();
	}
}
