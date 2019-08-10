package my.learn.thread.concurrent.pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class T07_ParallelCompute {
	
	private AtomicInteger primeCount = new AtomicInteger(0);
	
	public void doParallelCompute(int start ,int end){
		if(start > end) return;
		do {
			if(isPrime(start)) primeCount.addAndGet(1);
			start++;
		}while(start<=end);
		
	}
	
	public int getPrimeCount() {
		return primeCount.get();
	}

	private boolean isPrime(int i) {
			
		for(  int a = 2 ; a < i ; a++ ) {
			if( i%a == 0 ) return false;
		}
		return true;
	}
		
		public static void main(String[] args) throws InterruptedException, ExecutionException {
			
			//传统单线程方式
			T07_ParallelCompute p = new T07_ParallelCompute();
			long start_time = System.currentTimeMillis();
			p.doParallelCompute(1, 500000);
			long end_time = System.currentTimeMillis();
			System.out.println("Execute Time (ms):" + (end_time - start_time) );
			System.out.println("Prime Number :" + p.getPrimeCount() );
			//Single Thread Execute Time (ms):26865
			//Prime Number :41539
			
			// -------------------------------------------------------------------------------------
			T07_ParallelCompute pc = new T07_ParallelCompute();
			
			ExecutorService x = Executors.newFixedThreadPool(5);
			long s_time = System.currentTimeMillis();
			Future f1 = x.submit(()->{
				pc.doParallelCompute(1, 80000);
			});
			Future f2 = x.submit(()->{
				pc.doParallelCompute(80001, 170000);
			});
			Future f3 = x.submit(()->{
				pc.doParallelCompute(170001, 330000);
			});
			Future f4 = x.submit(()->{
				pc.doParallelCompute(330001, 390000);
			});
			Future f5 = x.submit(()->{
				pc.doParallelCompute(390001, 500000);
			});
			f1.get();
			f2.get();
			f3.get();
			f4.get();
			f5.get();
			long e_time = System.currentTimeMillis();
			System.out.println(x);
			x.shutdown();
			
			System.out.println("Paralle Thread Execute Time (ms):" + (e_time - s_time) );
			System.out.println("Prime Number :" + pc.getPrimeCount() );
		}
}
