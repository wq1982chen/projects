package my.learn.thread.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

		public static void main(String[] args) {
			// 线程执行完任务线程不会消失,减少线程启动的系统消耗
			ExecutorService service = Executors.newFixedThreadPool(5);
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
			service.shutdown(); // 正常的关闭，等待任务执行完关闭
			/*
			service.shutdownNow();//暴力关闭
			service.isShutdown(); //是不是关闭了，不代表关闭执行完，可能在关闭的执行过程中
			service.isTerminated();//代表线程是不是都执行完了
			*/
			
			
		}
}
