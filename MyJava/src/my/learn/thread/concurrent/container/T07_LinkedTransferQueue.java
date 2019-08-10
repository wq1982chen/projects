package my.learn.thread.concurrent.container;

import java.util.concurrent.LinkedTransferQueue;

/**
 *  预占模式
 *  消费者线程取元素时，如果队列不为空，则直接取走数据,若队列为空，
 *  那就生成一个节点（节点元素为null）入队然后消费者线程被等待在这个节点上，
 *  后面生产者线程入队时发现有一个元素为null的节点，生产者线程就不入队了，
 *  直接就将元素填充到该节点，并唤醒该节点等待的线程，
 *  被唤醒的消费者线程取走元素，从调用的方法返回
 *  
 * @author Administrator
 *
 */
public class T07_LinkedTransferQueue {

	public static void main(String[] args) {
		
		LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();
			
		//消费者线程先启动
		new Thread(()->{
				try {
					Thread.sleep(1000);
					System.out.println(strs.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}).start();
		
		strs.add("aaa");//不会阻塞
		strs.put("bbb");//有边界队列放不下了会阻塞
		try {
				strs.transfer("ccc");//会阻塞，不执行后续的代码,
				//所以生产不能早于消费者启动,要求消费者实时消费
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
			
	}
}
