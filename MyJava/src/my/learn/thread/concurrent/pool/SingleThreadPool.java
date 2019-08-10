package my.learn.thread.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadPool {

		public static void main(String[] args) {
			//保证单线程运行,保证任务按顺序执行
			ExecutorService service = Executors.newSingleThreadExecutor();
			
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
