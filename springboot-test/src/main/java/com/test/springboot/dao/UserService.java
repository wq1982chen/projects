package com.test.springboot.dao;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.test.springboot.dao.model.User;

/**
 * 注解说明
 * https://www.cnblogs.com/fashflying/p/6908028.html
 * @author Administrator
 *
 */
public interface UserService {

		/**
		 *  Spring在缓存方法的返回值时是以键值对进行缓存的，
		 *  值就是方法的返回结果，至于键的话，Spring又支持两种策略，默认策略和自定义策略，
		 *  需要注意的是当一个支持缓存的方法在对象内部被调用时是不会触发缓存功能的。@Cacheable可以指定三个属性，value、key和condition。
		 *  value属性是必须指定的，其表示当前方法的返回值是会被缓存在哪个Cache上的，对应Cache的名称,其可以是一个Cache也可以是多个Cache，
		 *  当需要指定多个Cache时其是一个数组@Cacheable({"cache1", "cache2"})
		 * @param id
		 * @return
		 */
		@Cacheable(key="#id",value="users") //先查缓存,没命中再执行方法
		User getUser(String id);
		
		@CachePut(key="#p0",value="users") //执行方法,并把结果放到缓存
		User findUser(String id);
		
		/**
		 *  清除操作默认是在对应方法成功执行之后触发的，即方法如果因为抛出异常而未能成功返回时也不会触发清除操作
		 *  allEntries是boolean类型,默认为false，当指定了allEntries为true时,Spring Cache将忽略指定的key,并清除所有的元素
		 *  beforeInvocation 可以改变触发清除操作的时间,指定该属性值为true时，Spring会在调用该方法之前清除缓存中的指定元素
		 * @param id
		 */
		@CacheEvict(key="#id",value="users",condition="#id!=1")
		void deleteUser(String id);
}
