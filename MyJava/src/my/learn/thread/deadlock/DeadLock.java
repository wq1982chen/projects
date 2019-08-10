package my.learn.thread.deadlock;

public class DeadLock {
	private final ResourceA A = new ResourceA();
    private final ResourceB B = new ResourceB();
    
    public void A2B() throws Exception
    {
        synchronized (A)
        {
            Thread.sleep(2000);
            synchronized (B)
            {
                System.out.println("leftRight end!");
            }
        }
    }
    
    public void B2A() throws Exception{
        synchronized (B)
        {
            Thread.sleep(2000);
            synchronized (A)
            {
                System.out.println("rightLeft end!");
            }
        }
    }
}
