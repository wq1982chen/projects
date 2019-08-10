package my.learn.thread.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

		public static void main(String[] args) {
			// �߳�ִ���������̲߳�����ʧ,�����߳�������ϵͳ����
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
			
			System.out.println(service); //���Բ鿴�̵߳�״̬
			service.shutdown(); // �����Ĺرգ��ȴ�����ִ����ر�
			/*
			service.shutdownNow();//�����ر�
			service.isShutdown(); //�ǲ��ǹر��ˣ�������ر�ִ���꣬�����ڹرյ�ִ�й�����
			service.isTerminated();//�����߳��ǲ��Ƕ�ִ������
			*/
			
			
		}
}
