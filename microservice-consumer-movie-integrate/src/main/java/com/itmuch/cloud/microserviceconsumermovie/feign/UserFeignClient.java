package com.itmuch.cloud.microserviceconsumermovie.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itmuch.cloud.microserviceconsumermovie.customization.feign.FeignLogConfiguration;
import com.itmuch.cloud.microserviceconsumermovie.pojo.RegistUser;

import feign.hystrix.FallbackFactory;

/*
 * @FeignClient注解中的microservice-provider-user是一个任意的客户端名称，用于创建Ribbon负载均衡器。
 * 在本例中，由于使用了Eureka，所以Ribbon会把microservice-provider-user解析
 * 成Eureka Server服务注册表中的服务
 * 如果不想使用Eureka，也可使用service.ribbon.listOfServers属性配置服务器列表
 * 也可使用url属性指定请求的URL（URL可以是完整的URL或主机名），
 * 例如@FeignClient(name = "microservice-provider-user", url = "http://localhost:8000/")
 * 
 *  事实上，Spring Cloud默认已为Feign整合了Hystrix，
 * 只要Hystrix在项目的classpath中，Feign默认就会用断路器包裹所有方法
 * 同时,yml 中要声明 Feign 的 Hystrix 支持
 */
/*
 * fallback: 配置回退方法
 *	fallbackFactory: 除配置回退方法外,还可打印熔断原因
 * 使用上面两个annotation 都需要在配置文件中增加 feign.hystrix.enabled: true 的配置
 * 两者只能同时配置一个
 */
@FeignClient(name = "microservice-provider-user",
				//fallback = FeignClientFallback.class, //默认整合了Hystrix
				fallbackFactory = FeignClientFallbackFactory.class,
				configuration=FeignLogConfiguration.class) // 增加日志
public interface UserFeignClient {

	@GetMapping(value="/userAuth") //服务提供方的路径映射
	public RegistUser findUserByNameAndPassword(
			@RequestParam("name") String name,
			@RequestParam("password")String password);

	@PostMapping(value="/save")
	public RegistUser save(RegistUser user);

}


/**
 * 回退类FeignClientFallback需实现Feign Client接口
 * FeignClientFallback也可以是public class，没有区别
 */
@Component
class FeignClientFallback implements UserFeignClient {

	@Override
	public RegistUser findUserByNameAndPassword(String name, String password) {
		System.out.println("调用了FeignClientFallback");
    	RegistUser user = new RegistUser();
        user.setId(-1L);
        user.setUserName(name);
        user.setPassWord(password);
        user.setNickName("默认用户:被熔断");
        return user;
	}

	@Override
	public RegistUser save(RegistUser user) {
		System.out.println("调用了FeignClientFallback");
		 user.setNickName("默认用户:被熔断");
		return user;
	}
}


/**
 * 回退类FeignClientFallback需实现Feign Client接口
 * FeignClientFallback也可以是public class，没有区别
 */
@Component
class FeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientFallbackFactory.class);

//	@Override
//	public UserFeignClient create(Throwable cause) {
//		return  (id)-> {
//				FeignClientFallbackFactory.LOGGER.info("fallback; reason was: ", cause);
//		    	RegistUser user = new RegistUser();
//		        user.setId(-1L);
//		        user.setUserName("默认用户");
//		        return user;
//			};
//		};
	@Override
	public UserFeignClient create(Throwable cause) {
		
		return new UserFeignClient() {
			@Override
			public RegistUser save(RegistUser user) {
				FeignClientFallbackFactory.LOGGER.info("fallback; reason was: ", cause);
				user.setNickName("默认用户:被熔断");
				return user;
			}
			
			@Override
			public RegistUser findUserByNameAndPassword(String name, String password) {
				System.out.println("调用了FallbackFactory");
				FeignClientFallbackFactory.LOGGER.info("fallback; reason was: ", cause);
		    	RegistUser user = new RegistUser();
		        user.setId(-1L);
		        user.setUserName(name);
		        user.setPassWord(password);
		        user.setNickName("默认用户:被熔断");
		        return user;
			}
		};
	}
	}
	