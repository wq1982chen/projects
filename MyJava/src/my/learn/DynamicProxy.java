package my.learn;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import my.inf.IUser;
import my.inf.dao.UserDAO;

public class DynamicProxy {

	private Object target;
	
	public DynamicProxy(Object target) {
		this.target = target;
	}
	
	public Object getProxyInstance() {
		
		Object proxy = Proxy.newProxyInstance(
										target.getClass().getClassLoader(), 
										target.getClass().getInterfaces(), 
										new InvocationHandler() {
											
											@Override
											public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
												String methodName = method.getName();
												Object result = null;;
												
												switch(methodName) {
													case "find": return method.invoke(target, args);
													case "update":
													case "delete":
													case "save":
														System.out.println("Begin Transaction ..... ");
														result = method.invoke(target, args);
														System.out.println("End Transaction ..... ");
														return result;
													default : return result;
												}
											}
										});
		return proxy;
	}
	
	public static void main(String[] args) {
		
		DynamicProxy proxy = new DynamicProxy(new UserDAO());
		IUser user = (IUser)proxy.getProxyInstance();
		
		user.save("User JSON Data");
		
		user.find("User Id");
		
		user.update("User JSON Data");
	}
}
