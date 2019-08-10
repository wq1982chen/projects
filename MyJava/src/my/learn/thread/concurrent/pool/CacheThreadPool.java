package my.learn.thread.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CacheThreadPool {

		public static void main(String[] args) {
			//初始时没有线程,扔任务后启动新线程,60s后线程没有被使用将被移除出线程池
			ExecutorService service = Executors.newCachedThreadPool();
			
			for(int i = 0; i < 6; i++) {
				service.execute(()->{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName());
				});
			}
			
			System.out.println(service); //可以查看线程的状态
			service.shutdown();
			
		}
}
