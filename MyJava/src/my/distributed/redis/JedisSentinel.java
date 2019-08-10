package my.distributed.redis;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class JedisSentinel {

	//服务器IP地址
    //private static String ADDR = "127.0.0.1";
    //端口
    //private static int PORT = 6379;
    //密码
    //private static String AUTH = "123456";
    //连接实例的最大连接数
    private static int MAX_ACTIVE = 1024;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。
    //如果超过等待时间，则直接抛出JedisConnectionException
    private static int MAX_WAIT = 10000;

    //连接超时的时间
    private static int TIMEOUT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisSentinelPool jedisPool = null;

    /**
     * 初始化Redis连接池
     */
    static {

        try {

            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            //jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);

        	Set<String> sentinels = new HashSet<>(); 
        	sentinels.add("127.0.0.1:26379"); 
        	sentinels.add("127.0.0.1:26380"); 
        	sentinels.add("127.0.0.1:26381");
        	
        	//mmaster 是在哨兵节点中对Redis主节点监听时随便取的一个名字,程序调用时要保持一致
        	//猜测是哨兵可以监听多个主节点,通过名字可以区别
        	jedisPool = new JedisSentinelPool("mmaster", sentinels, config, TIMEOUT);
        	
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    /**
     * 获取Jedis实例
     */
    public synchronized static Jedis getJedis() {

        try {

            if (jedisPool != null) {
                return  jedisPool.getResource();
            } 
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /***
     * 
     * 释放资源
     */
    public static void close(final Jedis jedis) {
            if(jedis != null) {
                jedisPool.close();
            }
    }
    
}
