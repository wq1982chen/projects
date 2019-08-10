package my.learn.thread.concurrent.container;

import java.util.ArrayList;

/**
 * 实现一个容器
 * 容量最大不超过10
 * 
 * @author Administrator
 *
 */
public class MyContainer {
	
	private ArrayList<String> data = new ArrayList<String>();
	private final int MAX = 10;
	private volatile int count = 0;
	
	public synchronized int getCount() {
		return count;
	}
	
	public synchronized void add(String value) {
		
		while( data.size() == MAX ) {
				try {
					System.out.println(Thread.currentThread().getName() + " is wait");
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		data.add(value);
		++count;
		notifyAll();
	}
	
	public synchronized String get() {
		
		while( data.size() == 0 ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String value =	data.remove(0);
		count--;
		notifyAll();
		return value;
	}
	
	public static void main(String[] args) {
		
		MyContainer trl = new MyContainer();
		
		/*for( int i=0 ; i < 2; i++) {	//消费者
			new Thread(()-> {
				while(true) {
					System.out.println(trl.get());
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start(); 
		}*/
		
		for( int i=0 ; i < 2; i++) {	//启动两个生产者线程
			new Thread(()-> {	
				while(true) {
					trl.add(Thread.currentThread().getName()+ "-" + Math.random()*1000);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		
	}
}
