package my.learn.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 验证线程的可见行
 * 1. volatile 虽然jvm保证缓存会同步到主存,但参与计算时不是原子性，故还是会受多线程影响
 * 只应用于单线程写，多线程读的场景（不过还是有并发读的问题）在实际中，最好用sychronized 加锁同步
 * 
 * 
 * @author Administrator
 *
 */
public class MyThread {

	//private volatile int i=0;
	//private int i=0;
	private AtomicInteger i = new AtomicInteger(0);
	
	//为了保证原子性操作,对方法增加同步关键字
//	public synchronized int add() {
//		i = i+1;// 非原子操作,首先包括读取i的值, 执行i+1,赋值给i,共计三步
//		return i;
//	}
	
	public  void add() {
		i.getAndAdd(1);// 非原子操作,首先包括读取i的值, 执行i+1,赋值给i,共计三步
	}
	
	public int get() {
		return i.get();
	}
	
	
	public static void main(String[] args) {
		
			MyThread  mt = new MyThread();
			
			Thread t1 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					for(int c = 0 ; c < 100; c++) {
						mt.add();
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + "--------+1" );
					}
				}
			});
			
			Thread t2 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					for(int c = 0 ; c < 100; c++) {
						mt.add();
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + "--------+1" );
					}
				}
			});
			
			t1.start();
			t2.start();
			
			 //若当期依然有子线程没有执行完毕
	        while(Thread.activeCount() > 1){
	            Thread.yield();//使得当前线程（主线程）让出CPU时间片
	        }
			
//			try {
//				Thread.sleep(1000);
//				t1.join();
//				t2.join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
			System.out.println(Thread.currentThread().getName() + " ---" + mt.get());
	}
}
