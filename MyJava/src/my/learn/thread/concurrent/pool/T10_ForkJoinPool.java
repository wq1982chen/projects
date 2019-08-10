package my.learn.thread.concurrent.pool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * 难以完成的大任务，时间长
 * 可以将任务切分成小任务
 * 分别执行小任务后再将所有结果合并起来
 * 
 * 模拟一个100万的数组相加
 * @author Administrator
 *
 */
public class T10_ForkJoinPool {

		static int nums[] = new int[1000000];
		static final int MAX = 50000;// 小任务的计算量不超过5万量
		static Random r =new Random();
		static {	// 常规计算方式
		
				for(int i=0;i<nums.length;i++) {
					nums[i] = r.nextInt(100);
				}
				
				System.out.println( "原始:" + Arrays.stream(nums).sum());
				
		}
		
		// ForkJoin 线程执行的 ForjJoinTask , 一般是通过继承RecursiveAction来实现
		static class AddAction extends RecursiveAction {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			int start ,end;
			
			AddAction(int s, int e){
				start = s;
				end = e;
			}
			@Override
			protected void compute() {
			
					if( end - start <= MAX ) {
						long sum = 0L;
						for( int i = start ; i < end ;i++ ) {
							sum += nums[i];
						}
						System.out.println("from:" + start  + " ,to " + end  + " = " + sum);
					}else {
						
						int middle = start + ( end - start)/2;
						AddTask subTask1 = new AddTask(start,middle);
						AddTask subTask2 = new AddTask(middle,end);
						
						subTask1.fork();// 只要fork , 一个新的线程就启动了
						subTask2.fork();
					}
				
			}
			
		}

		//带返回值
		static class AddTask extends RecursiveTask<Long> {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			int start ,end;
			
			AddTask(int s, int e){
				start = s;
				end = e;
			}
			@Override
			protected Long compute() {
			
					if( end - start <= MAX ) {
						long sum = 0L;
						for( int i = start ; i < end ;i++ ) {
							sum += nums[i];
						}
						//System.out.println("from:" + start  + " ,to " + end  + " = " + sum);
						return sum;
					}else {
						
						int middle = start + ( end - start)/2;
						AddTask subTask1 = new AddTask(start,middle);
						AddTask subTask2 = new AddTask(middle,end);
						
						subTask1.fork();// 只要fork , 一个新的线程就启动了
						subTask2.fork();
						
						return subTask1.join() + subTask2.join();
					}
				
			}
			
		}
		
		public static void main(String[] args) throws IOException {
			
			ForkJoinPool fjp = new ForkJoinPool();// 精灵线程
//			fjp.execute( new AddAction(0, nums.length));
//			System.in.read();
			
			//有返回值
			AddTask task = new AddTask(0, nums.length);
			fjp.execute( task);
			System.out.println(task.join()); // 自带阻塞功能
		}
}
