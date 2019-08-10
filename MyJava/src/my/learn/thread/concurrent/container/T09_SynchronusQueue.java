package my.learn.thread.concurrent.container;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class T09_SynchronusQueue {

	public static void main(String[] args) {
		//����Ϊ0�Ķ��У������ߵ���Ϣ�����������ѣ���������
		BlockingQueue<String> strs = new SynchronousQueue<>(); 
			
		//strs.add("aaa"); //����, java.lang.IllegalStateException: Queue full
		
		//�������߳�������
		new Thread(()->{
				try {
					Thread.sleep(1000);
					System.out.println(strs.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}).start();
		
		try {
			strs.put("bbb");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} //����
	}
}
