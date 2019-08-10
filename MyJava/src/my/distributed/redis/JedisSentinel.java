package my.distributed.redis;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class JedisSentinel {

	//������IP��ַ
    //private static String ADDR = "127.0.0.1";
    //�˿�
    //private static int PORT = 6379;
    //����
    //private static String AUTH = "123456";
    //����ʵ�������������
    private static int MAX_ACTIVE = 1024;

    //����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ����Ĭ��ֵҲ��8��
    private static int MAX_IDLE = 200;

    //�ȴ��������ӵ����ʱ�䣬��λ���룬Ĭ��ֵΪ-1����ʾ������ʱ��
    //��������ȴ�ʱ�䣬��ֱ���׳�JedisConnectionException
    private static int MAX_WAIT = 10000;

    //���ӳ�ʱ��ʱ��
    private static int TIMEOUT = 10000;

    //��borrowһ��jedisʵ��ʱ���Ƿ���ǰ����validate���������Ϊtrue����õ���jedisʵ�����ǿ��õģ�
    private static boolean TEST_ON_BORROW = true;

    private static JedisSentinelPool jedisPool = null;

    /**
     * ��ʼ��Redis���ӳ�
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
        	
        	//mmaster �����ڱ��ڵ��ж�Redis���ڵ����ʱ���ȡ��һ������,�������ʱҪ����һ��
        	//�²����ڱ����Լ���������ڵ�,ͨ�����ֿ�������
        	jedisPool = new JedisSentinelPool("mmaster", sentinels, config, TIMEOUT);
        	
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    /**
     * ��ȡJedisʵ��
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
     * �ͷ���Դ
     */
    public static void close(final Jedis jedis) {
            if(jedis != null) {
                jedisPool.close();
            }
    }
    
}
