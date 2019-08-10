package com.itmuch.cloud.microserviceconsumermovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

/**
 * 
 * http://localhost:8010/actuator/health 查看断路器的状态
 *  创建mq容器,这里指定了默认的mq用户和密码,就没有再使用guest/guest
 *  docker run -d --hostname my-rabbit --name rabbitmq -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin -p 15672:15672 -p 5672:5672 -p 25672:25672 -p 61613:61613 -p 1883:1883 rabbitmq:management
 *  扩展知识,在rabbitmq中新建用户,首先要进入容器内,使用容器中rabbitmq的命令，以下为创建mqu/123的用户
 	rabbitmqctl add_user mqu 123
	rabbitmqctl set_user_tags mqu administrator
	rabbitmqctl set_permissions -p "/" mqu ".*" ".*" ".*"
 * @author .KW.
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
public class MicroserviceConsumerMovieTurbineMqApplication {
	
    public static void main( String[] args ){
    	SpringApplication.run(MicroserviceConsumerMovieTurbineMqApplication.class, args);
    }
   
    @Bean
    @LoadBalanced //只需增加这个注解,就可以为RestTemplate整合Ribbon,使其具备负载均衡能力
    public RestTemplate restTemplate() {
    	return new RestTemplate();
    }
    
    /**
     * 低版本直接启动即可使用 http://ip:port/hystrix.stream 查看监控信息
     * 高版本需要添加本方法方可使用 http://ip:port/hystix.stream 查看监控信息
     * Hystrix还提供了近乎实时的监控。
     * HystrixCommand和HystrixObservableCommand在执行时，会生成执行结果和运行指标，
     * 比如每秒执行的请求数、成功数等，这些监控数据对分析应用系统的状态很有用
     * 
     * 同时,请在浏览器上运行可以看到效果,在Postman上会一直处于阻塞状态,原因不明
     * @return
     */
    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = 
        		new ServletRegistrationBean<HystrixMetricsStreamServlet>(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
