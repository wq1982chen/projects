package my.learn.thread.concurrent.container;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class T09_SynchronusQueue {

	public static void main(String[] args) {
		//容量为0的队列，生产者的消息必须马上消费，否则阻塞
		BlockingQueue<String> strs = new SynchronousQueue<>(); 
			
		//strs.add("aaa"); //报错, java.lang.IllegalStateException: Queue full
		
		//消费者线程先启动
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
		} //阻塞
	}
}
