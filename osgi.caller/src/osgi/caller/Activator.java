package osgi.caller;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import osgi.caller.event.handler.MyEventHandler;
import osgi.tester.event.MyEvent;
import osgi.tester.interfaces.IHello;
import osgi.tester.interfaces.IService;



public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("Registe Event Listener ...... "); 
        String[] topics = new String[]{MyEvent.my_topic};
        Hashtable<String,String[]> ht = new Hashtable<String,String[]>();
        ht.put(EventConstants.EVENT_TOPIC, topics);
        EventHandler myHandler = new MyEventHandler(); 
        context.registerService(EventHandler.class.getName(), myHandler, ht);
        System.out.println("Event Listener Registered ...... "); 
		
	    /** 
	        * Test hello service from bundle1. 
	    */ 
	    IHello hello1 = 
	        (IHello) context.getService( 
	        context.getServiceReference(IHello.class.getName())); 
	    System.out.println(hello1.getHello()); 
	    
	    IService myService = 
	    		(IService) context.getService( 
	    		        context.getServiceReference(IService.class.getName()));
	    
	    System.out.println(myService.getServiceList());
	    
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
