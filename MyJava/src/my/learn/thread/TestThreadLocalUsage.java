package my.learn.thread;

public class TestThreadLocalUsage {

	private ThreadLocal<String> stringLocal = new ThreadLocal<String>();
	private ThreadLocal<Long> longLocal = new ThreadLocal<Long>();
	private ThreadLocal<String> grpStringLocal = new ThreadLocal<String>() {
			@Override
			protected String initialValue() {
				return Thread.currentThread().getThreadGroup().getName();
			}
	};
	
	
	public void set() {
		stringLocal.set(Thread.currentThread().getName());
		longLocal.set(Thread.currentThread().getId());
	}
	
	public long getLong() {
        return (long) longLocal.get();
    }
     
    public String getString() {
        return (String) stringLocal.get();
    }
    
    public String getGrpStringLocal() {
        return (String) grpStringLocal.get();
    }
     
	
	public static void main(String[] args) throws InterruptedException {
		
		TestThreadLocalUsage ttlu = new TestThreadLocalUsage();
		ttlu.set();//重写ThreadLocal的initialValue方法可以免除调用赋值方法
		System.out.println(ttlu.getString());
		System.out.println(ttlu.getLong());
		System.out.println(ttlu.getGrpStringLocal()+"--------------");
		
		Thread t = new Thread() {
		
			public void run() {
				
					ttlu.set();
					
					System.out.println(ttlu.getString());
					System.out.println(ttlu.getLong());
					System.out.println(ttlu.getGrpStringLocal()+"--------------");
			};
		};
		
		t.start();
		t.join();
		
		System.out.println(ttlu.getString());
		System.out.println(ttlu.getLong());
		System.out.println(ttlu.getGrpStringLocal()+"--------------");
		
	}
}
