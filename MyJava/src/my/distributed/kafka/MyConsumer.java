package my.distributed.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class MyConsumer extends Thread {
	
	private String topic;  

    public MyConsumer(String topic){  
        super();  
        this.topic = topic;  
    }  


    @Override  
    public void run() {  
    	KafkaConsumer<String, String> kafkaConsumer = createConsumer();
		try{
            while (true){
                ConsumerRecords<String,String> records = kafkaConsumer.poll(10);
                for (ConsumerRecord<?, ?> record :  records ){
                    System.out.println(String.format("Topic - %s, Partition - %d, Value: %s", record.topic(), record.partition(), record.value()));
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            kafkaConsumer.close();
        }
		
    }  

    private KafkaConsumer<String, String> createConsumer() {  
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        properties.put("group.id", "test-group");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = 
        								new KafkaConsumer<String, String>(properties);
        
        List<String> topicsList = new ArrayList<String>();
		topicsList.add(topic);
		kafkaConsumer.subscribe(topicsList);
        
        return kafkaConsumer;
        
     }  

    public static void main(String[] args) {  
        new MyConsumer("mytopic").start();// 使用kafka集群中创建好的主题 test   
    }      

}
