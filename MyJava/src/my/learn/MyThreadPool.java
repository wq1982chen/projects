package my.learn;

import java.util.concurrent.Executors;

public class MyThreadPool {

	private Executors executor;
	
	public static void getExecutorService() {
		Executors.newCachedThreadPool();//创建一个可缓存线程池，线程的数量不固定
		Executors.newFixedThreadPool(10);	//创建一个定长的线程池，可控制线程最大并发数，超出的线程会在队列中等待
		Executors.newScheduledThreadPool(10);	//创建一个定长线程池，支持定时及周期性任务执行
		Executors.newSingleThreadExecutor();	//创建一个单线程化的线程池
	}

}
