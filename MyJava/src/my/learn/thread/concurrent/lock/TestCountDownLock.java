package my.learn.thread.concurrent.lock;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 门闩
 * @author Administrator
 *
 */
public class TestCountDownLock {

	private ArrayList<String> data = new ArrayList<String>();
	
	public void add(String value) {
		data.add(value);
	}
	
	public int size() {
		return data.size();
	}
	
	public static void main(String[] args) {
		
		TestCountDownLock tcdl = new TestCountDownLock();
		
		CountDownLatch cdl = new CountDownLatch(1); //参数是指定到计数几次
		
		new Thread(()->{
			System.out.println("thread1 start ------");
			try {
				if(tcdl.size() != 5) {
					System.out.println("thread1 wait ------");
					cdl.await(); // 线程等待
					System.out.println("thread1  wake------");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("thread1 over ------");
		},"t1").start();
		
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		new Thread(()->{
			System.out.println("thread2 start ------");
			for(int i=0 ; i < 10; i++) {
				String val = Thread.currentThread().getName() + "-" + i;
				tcdl.add(val);
				System.out.printf("put[%s] ------\n", val);
				if(  tcdl.size() == 5) {
					cdl.countDown(); //唤醒t1线程
					System.out.println("thread2 release count  ------");
				}
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("thread2 stop ------");
		},"t2").start();
	}
	
}
