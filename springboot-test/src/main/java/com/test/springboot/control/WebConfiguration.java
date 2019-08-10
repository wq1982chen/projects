package com.test.springboot.control;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  //标注在类上，相当于把该类作为spring的xml配置文件中的<beans>，作用为：配置spring容器(应用上下文)
public class WebConfiguration {

	/*
	(1)、@Bean注解在返回实例的方法上，如果未通过@Bean指定bean的名称，则默认与标注的方法名相同； 
	(2)、@Bean注解默认作用域为单例singleton作用域，可通过@Scope(“prototype”)设置为原型作用域； 
	(3)、既然@Bean的作用是注册bean对象，那么完全可以使用@Component、@Controller、@Service、@Repository等注解注册bean（在需要注册的类上加注解），
			 当然需要配置@ComponentScan注解进行自动扫描。
	*/
	@Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }
	
	@Bean
    public FilterRegistrationBean<MyFilter> testFilterRegistration() {

        FilterRegistrationBean<MyFilter> registration = new FilterRegistrationBean<MyFilter>();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }
	
	public class MyFilter implements Filter {
		
		public void destroy() {}

		public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain)
				throws IOException, ServletException {
			HttpServletRequest request = (HttpServletRequest) srequest;
			System.out.println("this is MyFilter,url :"+request.getRequestURI());
			filterChain.doFilter(srequest, sresponse);
		}

		public void init(FilterConfig arg0) throws ServletException {}
    }
}
