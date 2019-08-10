package com.itmuch.cloud.microserviceconsumermovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.FilterType;

import com.itmuch.cloud.microserviceconsumermovie.customization.feign.ExcludeComponent;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

/**
 * 如果使用自定义的Ribbon负载策略,配置类不应包括在主应用程序上下文的@ComponentScan中
 * 否则,该配置类中的配置信息就会被所有的@RibbonClient共享
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
value = {ExcludeComponent.class}))
public class MicroserviceConsumerMovieApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(MicroserviceConsumerMovieApplication.class, args);
    }
    
    @Bean
    @LoadBalanced	// Ribbon 负载均衡只需增加这个注解
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    /**
     * 低版本直接启动即可使用 http://ip:port/hystrix.stream 查看监控信息
     * 高版本需要添加本方法方可使用 http://ip:port/hystix.stream 查看监控信息
     * Hystrix还提供了近乎实时的监控。
     * HystrixCommand和HystrixObservableCommand在执行时，会生成执行结果和运行指标，
     * 比如每秒执行的请求数、成功数等，这些监控数据对分析应用系统的状态很有用
     * @return
     */
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
