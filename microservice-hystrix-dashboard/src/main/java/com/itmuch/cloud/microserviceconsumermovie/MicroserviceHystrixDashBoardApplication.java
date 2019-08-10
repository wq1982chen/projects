package com.itmuch.cloud.microserviceconsumermovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

/**
 * 
 * http://localhost:8010/actuator/health 查看断路器的状态
 * 
 * http://localhost:8030/hystrix 查看hystrix仪表盘
 * P102页有图中的解释
 * @author .KW.
 *
 */
@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine
public class MicroserviceHystrixDashBoardApplication {
	
    public static void main( String[] args ){
    	SpringApplication.run(MicroserviceHystrixDashBoardApplication.class, args);
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
