package my.learn.thread.deadlock;

public class Thread2 extends Thread {

	private DeadLock dl;
	
	public Thread2(DeadLock dl)
    {
        this.dl = dl;
    }
	
	public void run()
    {
        try
        {
            dl.B2A();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
