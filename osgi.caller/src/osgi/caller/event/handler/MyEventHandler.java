package osgi.caller.event.handler;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class MyEventHandler implements EventHandler {

	@Override
	public void handleEvent(Event event) {
		
		System.out.println("We handle event start ... ");
		try{
			Thread.currentThread().sleep(5*1000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		System.out.println("We handle event OK ... ");
	}

}
