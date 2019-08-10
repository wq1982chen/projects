package my.learn.thread.concurrent.pool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WorkStealThreadPool {

		public static void main(String[] args) throws IOException {
			//deamon�̣߳����������˳��������ں�̨���У�Ҫ�뿴�������������߳�����
			ExecutorService service = Executors.newWorkStealingPool();
			// CPU��4�ˣ�Ĭ�Ͼͻ�����4���߳�
			System.out.println(Runtime.getRuntime().availableProcessors());
			
			service.execute(new R(1000));
			service.execute(new R(2000));
			service.execute(new R(2000));
			service.execute(new R(2000));
			service.execute(new R(2000));
			
			System.out.println(service); //���Բ鿴�̵߳�״̬
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
