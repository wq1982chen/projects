package my.learn.thread.deadlock;

/**
 * ������������java����
 * @author Administrator
 *
 */
public class TestDeadLockThread {

	public static void main(String[] args)
	{
	    DeadLock dl = new DeadLock();
	    Thread1 t1 = new Thread1(dl);
	    Thread2 t2 = new Thread2(dl);
	    t1.start();
	    t2.start();

	    while(true);   
	}
}
