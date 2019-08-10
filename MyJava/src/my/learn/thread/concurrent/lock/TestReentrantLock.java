package my.learn.thread.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入
 * @author Administrator
 * ReentrantLock 与 synchronized的区别
 *  1. 可以尝试锁定，tryLock，锁得不到就另外处理 , 后者将一直等
 *  2. 公平锁和非公平锁
 */
public class TestReentrantLock {
	
	Lock lock = new ReentrantLock();
	
		void m1() {
			lock.lock(); // 相当于 synchronized(this)
			try {
				for(int i=0 ; i < 10;i++) {
					Thread.sleep(1000);
					System.out.println("m1 .... ...." + i);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
		}
		
		void m2() {
			lock.lock();
			System.out.println("m2 .... ....");
			lock.unlock();
		}
		
		void m3() {
			//lock.tryLock() //尝试加锁,不能获取则马上返回,lock()会一直等待 
			try {
				if(lock.tryLock(5,TimeUnit.SECONDS)) { //尝试5秒内获得锁
					System.out.println("m3 .... ....");
					lock.unlock();
				}else {
					System.out.println("m3 .... .... I have already waited 5s for lock, but still no lock acquired");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		void m4() {
			try {
				lock.lockInterruptibly(); // 设置可以被打断的锁
				System.out.println("m4 .... .... lock Interruptibly");
				Thread.sleep(5000);
				lock.unlock();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				
			}
		}
		
		public static void main(String[] args) {
			
			TestReentrantLock t = new TestReentrantLock();
			
			new Thread(t::m1).start();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			new Thread(t::m2).start();
			new Thread(t::m3).start();
//			Thread t2 = new Thread(t::m4) ;
//			t2.start();
//			t2.interrupt();
		}
}
