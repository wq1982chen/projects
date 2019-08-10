package my.learn.thread.concurrent.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class T06_Future {

		public static void main(String[] args) throws InterruptedException, ExecutionException {
			FutureTask<Integer> task =new FutureTask<Integer>(()->{	//Callable方法
				TimeUnit.MILLISECONDS.sleep(500);
				return 1000;
			});
			
			new Thread(task).start();
			
			System.out.println(task.get()); // 会阻塞，等到线程的返回值
			
			// 线程执行完任务线程不会消失,减少线程启动的系统消耗
			ExecutorService service = Executors.newFixedThreadPool(5);
			Future<Integer> f = service.submit(()->{	//Callable 
					try {
						TimeUnit.MILLISECONDS.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//System.out.println(Thread.currentThread().getName());
					return 100;
			});
			System.out.println(f.get());
			System.out.println(f.isDone());
			
			service.shutdown();
		}
}
