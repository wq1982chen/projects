package com.itmuch.cloud.microserviceconsumermovie.feign;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.itmuch.cloud.microserviceconsumermovie.model.User;

import feign.hystrix.FallbackFactory;

/**
* 回退类FeignClientFallback需实现Feign Client接口
* FeignClientFallback也可以是public class，没有区别
*/
@Component
public class FeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
	
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
			public User findById(Long id) {
				FeignClientFallbackFactory.LOGGER.info("fallback; reason was: ", cause);
				User user = new User();
		        user.setId(-1L);
		        user.setUsername("默认用户");
		        user.setName("被熔断");
		        return user;
			}

			@Override
			public User get1(Long id, String username) {
				return null;
			}

			@Override
			public User get2(Map<String, Object> map) {
				return null;
			}

			@Override
			public User post(User usr) {
				return null;
			}
		};
	}
	}