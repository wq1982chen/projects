package my.learn.thread.concurrent.pool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * ������ɵĴ�����ʱ�䳤
 * ���Խ������зֳ�С����
 * �ֱ�ִ��С������ٽ����н���ϲ�����
 * 
 * ģ��һ��100����������
 * @author Administrator
 *
 */
public class T10_ForkJoinPool {

		static int nums[] = new int[1000000];
		static final int MAX = 50000;// С����ļ�����������5����
		static Random r =new Random();
		static {	// ������㷽ʽ
		
				for(int i=0;i<nums.length;i++) {
					nums[i] = r.nextInt(100);
				}
				
				System.out.println( "ԭʼ:" + Arrays.stream(nums).sum());
				
		}
		
		// ForkJoin �߳�ִ�е� ForjJoinTask , һ����ͨ���̳�RecursiveAction��ʵ��
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
						
						subTask1.fork();// ֻҪfork , һ���µ��߳̾�������
						subTask2.fork();
					}
				
			}
			
		}

		//������ֵ
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
						
						subTask1.fork();// ֻҪfork , һ���µ��߳̾�������
						subTask2.fork();
						
						return subTask1.join() + subTask2.join();
					}
				
			}
			
		}
		
		public static void main(String[] args) throws IOException {
			
			ForkJoinPool fjp = new ForkJoinPool();// �����߳�
//			fjp.execute( new AddAction(0, nums.length));
//			System.in.read();
			
			//�з���ֵ
			AddTask task = new AddTask(0, nums.length);
			fjp.execute( task);
			System.out.println(task.join()); // �Դ���������
		}
}
