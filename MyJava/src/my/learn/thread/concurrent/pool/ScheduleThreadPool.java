package my.learn.thread.concurrent.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleThreadPool {

		public static void main(String[] args) {
			//
			ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
				/*
				 * ��ʱ�����񳡾�
				 * 500������������һ��,�п����߳̾͸���,û�о��������߳�
				 */
				service.scheduleAtFixedRate(()->{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName());
				}, 0, 500, TimeUnit.MILLISECONDS);
				
			System.out.println(service); //���Բ鿴�̵߳�״̬
			//service.shutdown();
			
		}
}
