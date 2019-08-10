package osgi.tester;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import osgi.tester.Activator;
import osgi.tester.impl.DefaultHelloServiceImpl;
import osgi.tester.impl.DefaultServiceProvider;
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
		
		context.registerService( 
		        IHello.class.getName(),
		        new DefaultHelloServiceImpl(), 
		        null); 
		
		DefaultServiceProvider dsp = new DefaultServiceProvider();
		dsp.setContext(bundleContext);
		
		context.registerService( 
		        IService.class.getName(),
		        dsp, 
		        null);
		
        System.out.println("hello service has been registed.");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
