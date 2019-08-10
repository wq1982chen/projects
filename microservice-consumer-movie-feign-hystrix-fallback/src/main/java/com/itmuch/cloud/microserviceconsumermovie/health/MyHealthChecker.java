package com.itmuch.cloud.microserviceconsumermovie.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 参考
 * https://blog.csdn.net/zhizhuodewo6/article/details/81985787
 */
@Component
public class MyHealthChecker implements HealthIndicator {

	private boolean up = true;
	
	@Override
	public Health health() {
		 if (up) {
	            return new Health.Builder().withDetail("aaa_cnt", 10) //自定义监控内容
	                    .withDetail("bbb_status", "up").up().build();
	        } else {
	            return new Health.Builder().withDetail("error", "client is down").down().build();
	        }
	}
	
	public boolean isUp() {
        return up;
    }
 
    public void setUp(boolean up) {
        this.up = up;
    }


}
