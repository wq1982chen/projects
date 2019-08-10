package my.learn.thread.concurrent.pool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WorkStealThreadPool {

		public static void main(String[] args) throws IOException {
			//deamon线程，即主函数退出后它会在后台运行，要想看到输出必须把主线程阻塞
			ExecutorService service = Executors.newWorkStealingPool();
			// CPU是4核，默认就会启动4个线程
			System.out.println(Runtime.getRuntime().availableProcessors());
			
			service.execute(new R(1000));
			service.execute(new R(2000));
			service.execute(new R(2000));
			service.execute(new R(2000));
			service.execute(new R(2000));
			
			System.out.println(service); //可以查看线程的状态
			//service.shutdown();
			System.in.read();
		}
		
		static class R implements Runnable {
			
			int time;
			
			R(int t){
				this.time = t ;
			}
			
			@Override
			public void run() {
				try {
					TimeUnit.MILLISECONDS.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			}
			
		}
}
