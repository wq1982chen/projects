package my.learn.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * ��֤�̵߳Ŀɼ���
 * 1. volatile ��Ȼjvm��֤�����ͬ��������,���������ʱ����ԭ���ԣ��ʻ��ǻ��ܶ��߳�Ӱ��
 * ֻӦ���ڵ��߳�д�����̶߳��ĳ��������������в����������⣩��ʵ���У������sychronized ����ͬ��
 * 
 * 
 * @author Administrator
 *
 */
public class MyThread {

	//private volatile int i=0;
	//private int i=0;
	private AtomicInteger i = new AtomicInteger(0);
	
	//Ϊ�˱�֤ԭ���Բ���,�Է�������ͬ���ؼ���
//	public synchronized int add() {
//		i = i+1;// ��ԭ�Ӳ���,���Ȱ�����ȡi��ֵ, ִ��i+1,��ֵ��i,��������
//		return i;
//	}
	
	public  void add() {
		i.getAndAdd(1);// ��ԭ�Ӳ���,���Ȱ�����ȡi��ֵ, ִ��i+1,��ֵ��i,��������
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
			
			 //��������Ȼ�����߳�û��ִ�����
	        while(Thread.activeCount() > 1){
	            Thread.yield();//ʹ�õ�ǰ�̣߳����̣߳��ó�CPUʱ��Ƭ
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
