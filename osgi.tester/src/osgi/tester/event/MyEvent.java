package osgi.tester.event;

import java.util.Dictionary;

import org.osgi.service.event.Event;

public class MyEvent extends Event {

	public final static String my_topic = "testosgi/MyEvent";
	
	public MyEvent(String topic, Dictionary<String, ?> properties) {
		super(my_topic, properties);
	}
	
	public MyEvent(){
		this(my_topic, null);
	}
	

	public String toString(){
		return "Occur a Event ";
	}
}