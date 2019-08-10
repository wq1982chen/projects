package my.distributed.kafka;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
//http://www.jasongj.com/2015/01/02/Kafka%E6%B7%B1%E5%BA%A6%E8%A7%A3%E6%9E%90/
//https://blog.csdn.net/woshixiazaizhe/article/details/80610432
public class MyProducer extends Thread {

	String topic;
	
	MyProducer(String topic){
		this.topic = topic;
	}
	
	@Override
	public void run() {
		KafkaProducer<String, ?> producer = createProducer();
		
		int i=0;  
        while( i < 30 ){  
            producer.send(new ProducerRecord(topic,Integer.toString(i), "message: " + i++));  
            try {  
                TimeUnit.SECONDS.sleep(2);  
            } catch (InterruptedException e) {
            	producer.close();
                e.printStackTrace();  
            } 
            //System.out.println("I'm continue all the time");
        }  
	}
	
	private KafkaProducer<String, ?> createProducer() {  
        Properties properties = new Properties();  
        properties.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        properties.put("group.id", "test-group");
        properties.put("key.serializer", 	"org.apache.kafka.common.serialization.StringSerializer");  
        properties.put("value.serializer", 	"org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer(properties);  
     }
	
	public static void main(String[] args) {  
        new MyProducer("mytopic").start();   
    }  

}
