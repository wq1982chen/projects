package osgi.http;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;

import osgi.http.servlet.MyServlet;

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
//		ServiceReference serviceReference = context.getServiceReference(HttpService.class.getName());
//      service = (HttpService) context.getService(serviceReference);
//      
//      service.registerServlet("/SayHello", new SayHello(),null,null);
//		HttpServlet printNameServlet = new PrintNameServlet(bundleContext);
//		
//		helloServiceRegistration = context.registerService(HttpServlet.class  
//                .getName(), printNameServlet, null);

// 		ServiceReference serviceReference = bundleContext.getServiceReference(HttpService.class.getName());  
//	    
//	    if( serviceReference != null ) {
//		    service = (HttpService) bundleContext.getService(serviceReference);  
//	        //register  
//		    HttpContext httpContext = service.createDefaultHttpContext();  
//		    service.registerResources("/osgi", "/web", httpContext); //���ñ��������ж�"/osgi"ӳ�䵽"web"Ŀ¼  
//		    service.registerServlet("/osgi/print", new PrintNameServlet(bundleContext) , null, httpContext);   //����servlet������'/osgi/print"ӳ�䵽servlet��ʵ��
//	    }
		Activator.context = bundleContext;
		HttpService httpService = (HttpService)context.getService(context.getServiceReference(HttpService.class.getName())); 
		//
		httpService.registerResources("/", "/pages", null);
		// �ҰѶ˿ڸĳ���8099
		// http://localhost:8099/index.html
		//ע��servlet
		MyServlet servlet = new MyServlet(); 
		httpService.registerServlet("/servlet", servlet, null, null);

	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
