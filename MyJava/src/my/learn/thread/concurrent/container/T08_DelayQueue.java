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
		//队列
			// 非同步 ： ArrayList ,LinkedList
			// 同步：		Vector , 并发量比较低的情况下使用
		//ConcurrentLinkedQueue 高并发情况下的队列
		//也可以使用BlockQueue
		//	LinkedBlockingQueue 阻塞式队列,以链表实现,无界队列,可直至内存满
		//	ArrayBlockingQueue 阻塞式队列,有界队列,数组实现
		//	DelayQueue 无界队列,队列中的元素需要等一段时间才能被消费线程拿走
		//	TransferQueue 可以将消息直接交消费线程使用的队列,(游戏服务器转换消息)
		//	SynchronousQueue ,一种特殊的TransferQueue
		//CopyOnWriteList , 写的时候特别少,读的情况特别多（场景:监听器队列）
		
		// 执行定时任务
		BlockingQueue<MyTask> queue = new DelayQueue<MyTask>();
		//put 往队列里放，放不下了就会阻塞
		//add往队列里放，放不下报异常
		//offer往队列里放，不报异常，通过返回值判断是否加成功
		//take 往外拿，拿不到了就会阻塞
		long now = System.currentTimeMillis();
		
		MyTask t1 = new MyTask(1000 + now); // 延迟执行的任务
		MyTask t2 = new MyTask(5500 + now);	
		MyTask t3 = new MyTask(2500 + now);
		MyTask t4 = new MyTask(1500 + now);
		MyTask t5 = new MyTask(2000 + now);
		
		queue.add(t1);	// 分几种方式的分别放入队列
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
