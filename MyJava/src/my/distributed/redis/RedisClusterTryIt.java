package my.distributed.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisAskDataException;
import redis.clients.jedis.exceptions.JedisMovedDataException;

public class RedisClusterTryIt {

	public void setValue(String key , String value) {
		Jedis jedis = JedisClusterConnector.getDefaultJedis();
		try {
			jedis.set(key,value);
		}catch(JedisMovedDataException m) {
			System.out.printf("MOVED HOST: %s , PORT: %d \n", 
					m.getTargetNode().getHost(),m.getTargetNode().getPort());
			try {
				String result = JedisClusterConnector.setValue(m.getTargetNode(), key,value);
				System.out.println(result);
			}catch(JedisAskDataException a) {
				System.out.printf("ASK HOST: %s , PORT: %d \n", 
						a.getTargetNode().getHost(),a.getTargetNode().getPort());
				String result = JedisClusterConnector.setValueAfterAsk(a.getTargetNode(), key,value);
				System.out.println(result);
			}
		}catch(JedisAskDataException a) {
			System.out.printf("ASK HOST: %s , PORT: %d \n", 
					a.getTargetNode().getHost(),a.getTargetNode().getPort());
			String result = JedisClusterConnector.setValueAfterAsk(a.getTargetNode(), key,value);
			System.out.println(result);
		}
		
		JedisClusterConnector.close();
	}
	
	public static void main(String[] args) {
		RedisClusterTryIt redis = new RedisClusterTryIt();
		redis.setValue("nccdb{CLUSTER_ASK}", "172.16.128.52");
	}
}
