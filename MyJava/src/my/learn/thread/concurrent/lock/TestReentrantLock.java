package my.learn.thread.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ����
 * @author Administrator
 * ReentrantLock �� synchronized������
 *  1. ���Գ���������tryLock�����ò��������⴦�� , ���߽�һֱ��
 *  2. ��ƽ���ͷǹ�ƽ��
 */
public class TestReentrantLock {
	
	Lock lock = new ReentrantLock();
	
		void m1() {
			lock.lock(); // �൱�� synchronized(this)
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
			//lock.tryLock() //���Լ���,���ܻ�ȡ�����Ϸ���,lock()��һֱ�ȴ� 
			try {
				if(lock.tryLock(5,TimeUnit.SECONDS)) { //����5���ڻ����
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
				lock.lockInterruptibly(); // ���ÿ��Ա���ϵ���
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
