package my.learn.thread.concurrent.container;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class T08_DelayQueue {
	
	static class MyTask implements Delayed {

		private long runtime;
		
		public MyTask(long delay) {
			this.runtime = delay;
		}
		@Override
		public int compareTo(Delayed o) {
			if( this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS))
				return -1;
			else if( this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS))
				return 1;
			else
				return 0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(runtime - System.currentTimeMillis() , TimeUnit.MILLISECONDS);
		}
		
		@Override
		public String toString() {
			return Long.toString(runtime);
		}
		
	}
	
	public static void main(String[] args) {
		//����
			// ��ͬ�� �� ArrayList ,LinkedList
			// ͬ����		Vector , �������Ƚϵ͵������ʹ��
		//ConcurrentLinkedQueue �߲�������µĶ���
		//Ҳ����ʹ��BlockQueue
		//	LinkedBlockingQueue ����ʽ����,������ʵ��,�޽����,��ֱ���ڴ���
		//	ArrayBlockingQueue ����ʽ����,�н����,����ʵ��
		//	DelayQueue �޽����,�����е�Ԫ����Ҫ��һ��ʱ����ܱ������߳�����
		//	TransferQueue ���Խ���Ϣֱ�ӽ������߳�ʹ�õĶ���,(��Ϸ������ת����Ϣ)
		//	SynchronousQueue ,һ�������TransferQueue
		//CopyOnWriteList , д��ʱ���ر���,��������ر�ࣨ����:���������У�
		
		// ִ�ж�ʱ����
		BlockingQueue<MyTask> queue = new DelayQueue<MyTask>();
		//put ��������ţ��Ų����˾ͻ�����
		//add��������ţ��Ų��±��쳣
		//offer��������ţ������쳣��ͨ������ֵ�ж��Ƿ�ӳɹ�
		//take �����ã��ò����˾ͻ�����
		long now = System.currentTimeMillis();
		
		MyTask t1 = new MyTask(1000 + now); // �ӳ�ִ�е�����
		MyTask t2 = new MyTask(5500 + now);	
		MyTask t3 = new MyTask(2500 + now);
		MyTask t4 = new MyTask(1500 + now);
		MyTask t5 = new MyTask(2000 + now);
		
		queue.add(t1);	// �ּ��ַ�ʽ�ķֱ�������
		try {
			queue.put(t2);
		} catch (InterruptedException q) {
			q.printStackTrace();
		}
		queue.offer(t3);
		queue.add(t4);
		queue.add(t5);  
		
		System.out.println(queue);
		
		new Thread(()->{
				try {
						System.out.println(queue.take());
				} catch (InterruptedException e) {
						e.printStackTrace();
				}
		}).start();
	}
}
