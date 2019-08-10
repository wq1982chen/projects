package osgi.tester.impl;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;

import osgi.tester.event.MyEvent;
import osgi.tester.interfaces.IService;


public class DefaultServiceProvider implements IService {
	
	private BundleContext context = null;

	public BundleContext getContext() {
		return context;
	}

	public void setContext(BundleContext context) {
		this.context = context;
	}

	@Override
	public String getServiceList() {
		
		ServiceReference<?> ref = 
				context.getServiceReference(EventAdmin.class.getName());
		if(ref != null ) {
			EventAdmin eventAdmin = (EventAdmin)context.getService(ref);
			if(eventAdmin!=null){
				System.out.println("we post event started .");
				eventAdmin.postEvent(new MyEvent());
				System.out.println("we post event returned .");
			}
		}else{
			System.out.println("EventAdmin is null .." + EventAdmin.class.getName());
		}
		
		
		return "we can get a service list ";
	}

}
