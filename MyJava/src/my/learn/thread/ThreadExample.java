package my.learn.thread;

public class ThreadExample {

	private static volatile Object resource = new Object();
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread threadA = new Thread( new Runnable(){
			
			public void run() {
				
				synchronized( resource ){
					
					System.out.println("ThreadA get resource lock. ");
					try{
						System.out.println("ThreadA begin wait. ");
						resource.wait();
						System.out.println("ThreadA end wait. ");
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
				
			}
		});
		
		Thread threadB = new Thread( new Runnable(){
		
			public void run() {
				
				synchronized( resource ){
					
					System.out.println("ThreadB get resource lock. ");
					try{
						System.out.println("ThreadB begin wait. ");
						resource.wait();
						System.out.println("ThreadB end wait. ");
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
				
			}
		});
		
		Thread threadC = new Thread( new Runnable(){

			public void run() {
				
				synchronized( resource ){
					System.out.println("ThreadC begin notify. ");
					resource.notifyAll();
				}
			}
		});
		
		 threadA.start();
		 threadB.start();
		 Thread.sleep(1000);
		 threadC.start(); // 等待线程结束
		 threadA.join();
		 threadB.join();
		 threadC.join();
		 System.out.println("main over");
	}
}
