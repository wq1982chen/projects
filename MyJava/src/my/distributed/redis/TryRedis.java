package my.distributed.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import my.distributed.redis.model.User;
import redis.clients.jedis.Jedis;

//windows版redis下载
//https://github.com/MicrosoftArchive/redis/releases
//来源于
//https://blog.csdn.net/chensizheng/article/details/78309888
public class TryRedis {

	
	private Jedis jedis = null;
	
	public TryRedis(){
		
		//单机版
		//jedis = new Jedis("127.0.0.1",6379);  //默认端口6379 可以省略
		//无密码此步可省略
		//jedis.auth("redis");
		//哨兵版
		jedis = JedisSentinel.getJedis();
		
	}
	
	public void tryString(){
		
		jedis.set("user", "Kevin is  a VIP");
		System.out.println("key: user ; value: " + jedis.get("user"));
	}
	
	public void close(){
		
		jedis.close();
	}
	
	public void tryList(){
		
		//先移除key为list1的值
		jedis.del("listLanguage");
		
		//存储数据到列表中
		jedis.lpush("listLanguage", "Java");
		jedis.lpush("listLanguage", "Html5");
		jedis.lpush("listLanguage", "Python");
		// 获取存储的数据并输出
		List<String> list = jedis.lrange("listLanguage", 0 ,-1);
		for(int i=0; i<list.size(); i++) {
			System.out.println("列表项["+i+"]为: "+list.get(i));
		}
	}
	
	public void trace(){
		//生产环境不可用，会导致Redis服务阻塞
		Set<String> keys = jedis.keys("*");
		Iterator<String> it = keys.iterator();
		while(it.hasNext()) {
			String key = it.next();
			System.out.println("Redis has a key :" + key);
		}
	}
	
	public void cacheUsersData(){
		
		User u1 = new User(UUID.randomUUID().toString(),"jack1",21,"m");
		User u2 = new User(UUID.randomUUID().toString(),"jack2",22,"f");
		User u3 = new User(UUID.randomUUID().toString(),"jack3",23,"m");
		User u4 = new User(UUID.randomUUID().toString(),"jack4",24,"f");
		User u5 = new User(UUID.randomUUID().toString(),"jack5",25,"m");
		
		Map<String,String> userMap = new HashMap<String,String>();
		userMap.put("u1",JSON.toJSONString(u1));
		userMap.put("u2",JSON.toJSONString(u2));
		userMap.put("u3",JSON.toJSONString(u3));
		userMap.put("u4",JSON.toJSONString(u4));
		userMap.put("u5",JSON.toJSONString(u5));
		
		jedis.hmset("t_user",userMap);
		
		 //预设查询结果集
        final String user_list_by_age_25 = "user_list_by_age_25";
        final String user_list_by_sex_m = "user_list_by_sex_m";
        final String user_list_by_sex_f = "user_list_by_sex_f";

		
        jedis.sadd(user_list_by_sex_m,"u1");
        jedis.sadd(user_list_by_sex_m,"u3");
        jedis.sadd(user_list_by_sex_m,"u5");
        jedis.sadd(user_list_by_sex_f,"u2");
        jedis.sadd(user_list_by_sex_f,"u4");
        jedis.sadd(user_list_by_age_25,"u5");
	}
	
	public void getUserFromCache(){
		
		Set<String> userlist = jedis.smembers("user_list_by_sex_m");
		for(String u : userlist){
			//拿到对对应user的json字符串
			String userJson = jedis.hget("t_user",u);
			System.out.println("User json string : " + userJson);
			//这里可以再使用json转object方法
			User user = JSON.parseObject(userJson,User.class);
			System.out.println(user.toString());
		}
		
	}
	
	public static void main(String[] args) {
		
		TryRedis tr = new TryRedis();
		
		tr.tryString();
		tr.tryList();
		tr.cacheUsersData();
		tr.trace();
		tr.getUserFromCache();
		
		tr.close();
	}
	
}
