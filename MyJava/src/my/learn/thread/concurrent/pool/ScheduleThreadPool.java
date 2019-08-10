package my.learn.thread.concurrent.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleThreadPool {

		public static void main(String[] args) {
			//
			ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
				/*
				 * 定时多任务场景
				 * 500毫秒后调用任务一次,有空闲线程就复用,没有就生成新线程
				 */
				service.scheduleAtFixedRate(()->{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName());
				}, 0, 500, TimeUnit.MILLISECONDS);
				
			System.out.println(service); //可以查看线程的状态
			//service.shutdown();
			
		}
}
