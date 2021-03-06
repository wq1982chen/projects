package com.test.springboot.redis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigDefinedTest {

	@Autowired
	private RedisConfigDefined redisConfigDefined;
	
	@Test
	public void test() {
		
		assertNotNull(redisConfigDefined);
		assertEquals(8,redisConfigDefined.getMaxIdle());
		assertEquals(3,redisConfigDefined.getSentinels().size());
		
	}

}
