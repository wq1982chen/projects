package com.test.springboot.redis;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;

@Configuration
@EnableCaching
@PropertySource({"classpath:redis.properties"})
public class RedisSentinelConnection {

	
	//@Value("#{'${spring.redis.sentinels}'.split(',')}")
	@Value("#{'${spring.redis.sentinels}'.split(',')}")
    private List<String> nodes;
	//@Value("#{'${spring.redis.sentinel.master}'}")
    @Value("${spring.redis.sentinel.master}")
	private String master;
    
//	@Bean
//    @ConfigurationProperties(prefix="spring.redis")
//    public JedisPoolConfig getRedisConfig(){
//        JedisPoolConfig config = new JedisPoolConfig();
//        return config;
//    }

	
	 /**
     * 配置LettuceClientConfiguration 包括线程池配置和安全项配置
     * @param genericObjectPoolConfig common-pool2线程池
     * @return lettuceClientConfiguration
     */
	@Bean
	public LettuceClientConfiguration lettuceClientConfiguration(GenericObjectPoolConfig genericObjectPoolConfig) {
		return LettucePoolingClientConfiguration.builder().poolConfig(genericObjectPoolConfig).build();
	}

	@Bean
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
		
	 @Bean
	 public GenericObjectPoolConfig genericObjectPoolConfig(RedisConfigDefined redisConfigDefined) {
		 GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
	     poolConfig.setMaxIdle(redisConfigDefined.getMaxIdle());
	     poolConfig.setMinIdle(redisConfigDefined.getMinIdle());
	     //poolConfig.setMaxTotal(redisConfigDefined.get);
//	     master = redisConfigDefined.getMaster();
//	     nodes  = redisConfigDefined.getSentinels();
	     //todo 其他配置
	     return poolConfig;
	 }

    
	@ConditionalOnBean(name="genericObjectPoolConfig")
    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration(){
    	RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
//    	if(Optional.of(nodes).isPresent()) {
//    		System.out.println("----------------------xx-----------------------");
//    	}
    	//配置redis的哨兵sentinel
        Set<RedisNode> redisNodeSet = new HashSet<>();
    	 nodes.forEach(x->{
             redisNodeSet.add(new RedisNode(x.split(":")[0],Integer.parseInt(x.split(":")[1])));
         });
    	configuration.setSentinels(redisNodeSet);
    	configuration.setMaster(master);
    	return configuration;
    }
    
	// 缓存管理器
	@Bean
	public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
		RedisCacheManager.RedisCacheManagerBuilder builder = 
									RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory);
		@SuppressWarnings("serial")
		Set<String> cacheNames = new HashSet<String>() {
			{
				add("users");
			}
		};
		builder.initialCacheNames(cacheNames);
		return builder.build();
	}
    
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(RedisSentinelConfiguration redisSentinelConfiguration, 
    																														 LettuceClientConfiguration  lettuceClientConfiguration) {
        return new LettuceConnectionFactory(redisSentinelConfiguration, lettuceClientConfiguration);
    }

    /**
	 * RedisTemplate配置
	 */
	@Bean
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
