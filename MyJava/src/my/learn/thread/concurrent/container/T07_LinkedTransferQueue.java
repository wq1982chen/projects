package my.learn.thread.concurrent.container;

import java.util.concurrent.LinkedTransferQueue;

/**
 *  Ԥռģʽ
 *  �������߳�ȡԪ��ʱ��������в�Ϊ�գ���ֱ��ȡ������,������Ϊ�գ�
 *  �Ǿ�����һ���ڵ㣨�ڵ�Ԫ��Ϊnull�����Ȼ���������̱߳��ȴ�������ڵ��ϣ�
 *  �����������߳����ʱ������һ��Ԫ��Ϊnull�Ľڵ㣬�������߳̾Ͳ�����ˣ�
 *  ֱ�Ӿͽ�Ԫ����䵽�ýڵ㣬�����Ѹýڵ�ȴ����̣߳�
 *  �����ѵ��������߳�ȡ��Ԫ�أ��ӵ��õķ�������
 *  
 * @author Administrator
 *
 */
public class T07_LinkedTransferQueue {

	public static void main(String[] args) {
		
		LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();
			
		//�������߳�������
		new Thread(()->{
				try {
					Thread.sleep(1000);
					System.out.println(strs.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}).start();
		
		strs.add("aaa");//��������
		strs.put("bbb");//�б߽���зŲ����˻�����
		try {
				strs.transfer("ccc");//����������ִ�к����Ĵ���,
				//��������������������������,Ҫ��������ʵʱ����
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
			
	}
}
