package my.distributed.redis;

import java.util.HashMap;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * 支持Window docker 的Redis Cluster
 * 
 * @author Administrator
 *
 */
public class JedisClusterConnector {

	private static  HashMap<String,JedisPool> hostsMap;
	private static final String dockerHost = "192.168.99.100";
	private static final int defaultPort = 7010;
	private static JedisPoolConfig config = null;
	
	static {
		
		config = new JedisPoolConfig();
        config.setMaxTotal(1024);
        config.setMaxIdle(200);
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        
        JedisPool jedisPool = new JedisPool(config,dockerHost,defaultPort);
        hostsMap = new HashMap<String,JedisPool>();
        hostsMap.put(Integer.toString(defaultPort), jedisPool);
	}
	
	
	public static Jedis getJedis(HostAndPort hostAndPort) {
		JedisPool jedisPool = hostsMap.get(Integer.toString(hostAndPort.getPort()));
		if( jedisPool == null ) {
			synchronized(hostsMap) {
				jedisPool = hostsMap.get(Integer.toString(hostAndPort.getPort()));
				if( jedisPool == null ) {
					jedisPool = new JedisPool(config,dockerHost,hostAndPort.getPort());
					hostsMap.put(Integer.toString(hostAndPort.getPort()), jedisPool);
				}
			}
		}
		return jedisPool.getResource();
	}
	
	public static String setValue(HostAndPort hostAndPort, String key, String value) {
		Jedis jedis = getJedis(hostAndPort);
		return jedis.set(key, value);
	}
	
	 /***
     * 
     * 释放资源
     */
    public static void close() {
    	
    	for(JedisPool jedisPool : hostsMap.values()) {
    		jedisPool.close();
    	}
    }

	public static Jedis getDefaultJedis() {
		return hostsMap.get(Integer.toString(defaultPort)).getResource();
	}

	public static String setValueAfterAsk(HostAndPort hostAndPort, String key, String value) {
		Jedis jedis = getJedis(hostAndPort);
		jedis.asking();
		return jedis.set(key, value);
	}
}
