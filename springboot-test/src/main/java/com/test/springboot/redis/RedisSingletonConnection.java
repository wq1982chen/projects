package com.test.springboot.redis;

import java.lang.reflect.Method;
//import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

/**
 * Redis单机版
 * 因Redis从单机版改为了哨兵模式，同时存在工程中有影响,
 * 故屏蔽了此类中的
 * Configuration和EnableCaching注解
 * 以及类中所有属性和方法上的注解
 * 
 * @author Administrator
 */
//@Configuration
//@EnableCaching
public class RedisSingletonConnection extends CachingConfigurerSupport {

	/**
	 * @Resource 默认按 byName自动注入
	 * @Autowired按byType自动注入
	 */
	//@Resource
	private LettuceConnectionFactory lettuceConnectionFactory;

	//@Bean
	public  KeyGenerator keyGenerator() {
		
			return new KeyGenerator() {

				public Object generate(Object target, Method method, Object... params) {
					
						StringBuilder sb = new StringBuilder();
		                sb.append(target.getClass().getName());
		                sb.append(method.getName());
		                for (Object obj : params) {
		                    sb.append(obj.toString());
		                }
		                return sb.toString();
				}
			};
	}
	
//	@Bean springboot 1.x的写法,2.x已经不用这种方式了
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
//        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
//                					.cacheDefaults(redisCacheConfiguration).build();
//    }
	
		// 缓存管理器
		//@Bean
		public CacheManager cacheManager() {
			RedisCacheManager.RedisCacheManagerBuilder builder = 
										RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory);
			@SuppressWarnings("serial")
			Set<String> cacheNames = new HashSet<String>() {
				{
					add("codeNameCache");
				}
			};
			builder.initialCacheNames(cacheNames);
			return builder.build();
		}

		/**
		 * RedisTemplate配置
		 */
		//@Bean
		public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
			// 设置序列化
			Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
			ObjectMapper om = new ObjectMapper();
			om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		    om.enableDefaultTyping(DefaultTyping.NON_FINAL);
			jackson2JsonRedisSerializer.setObjectMapper(om);
			// 配置redisTemplate
			RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
			redisTemplate.setConnectionFactory(lettuceConnectionFactory);
			RedisSerializer<?> stringSerializer = new StringRedisSerializer();
			redisTemplate.setKeySerializer(stringSerializer);// key序列化
			redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化
			redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
			redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化
			redisTemplate.afterPropertiesSet();
			return redisTemplate;
		}
}
