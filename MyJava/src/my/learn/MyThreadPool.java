package my.learn;

import java.util.concurrent.Executors;

public class MyThreadPool {

	private Executors executor;
	
	public static void getExecutorService() {
		Executors.newCachedThreadPool();//����һ���ɻ����̳߳أ��̵߳��������̶�
		Executors.newFixedThreadPool(10);	//����һ���������̳߳أ��ɿ����߳���󲢷������������̻߳��ڶ����еȴ�
		Executors.newScheduledThreadPool(10);	//����һ�������̳߳أ�֧�ֶ�ʱ������������ִ��
		Executors.newSingleThreadExecutor();	//����һ�����̻߳����̳߳�
	}

}
