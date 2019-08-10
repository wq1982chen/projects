package my.ibmmq.local;

/**
 * @author Fenglb E-mail:56553655@163.com
 * @version ����ʱ�䣺2009-4-30 ����04:13:38
 * ��˵��
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;

public class LocalMQ{
     //������й������Ͷ��е�����
     private static String qmName; 
     private static String qName;
     private static MQQueueManager qMgr;
     static{
    	 //���û���:
    	 //MQEnvironment�а�������MQQueueManager�����еĻ����Ĺ��ɵľ�̬������MQEnvironment��ֵ���趨����MQQueueManager�Ĺ��캯�����ص�ʱ�������ã�
    	 //��˱����ڽ���MQQueueManager����֮ǰ�趨MQEnvironment�е�ֵ.
    	 MQEnvironment.hostname="127.0.0.1";        //MQ��������IP��ַ    
    	 MQEnvironment.channel="SVRCONN";  			//���������ӵ�ͨ��
    	 MQEnvironment.CCSID=1381;              	//������MQ����ʹ�õı���1381����GBK��1208����UTF(Coded Character Set Identifier:CCSID)
    	 MQEnvironment.port=1414;					//MQ�˿�
    	 qmName = "QM";							//MQ�Ķ��й���������
         qName = "QL.QM2";								//MQԶ�̶��е�����
         try {
        	 //���岢��ʼ�����й������������� 
	    	 //MQQueueManager���Ա����̹߳������Ǵ�MQ��ȡ��Ϣ��ʱ����ͬ���ģ��κ�ʱ��ֻ��һ���߳̿��Ժ�MQͨ�š�
			qMgr = new MQQueueManager(qmName);
		} catch (MQException e) {
			// TODO Auto-generated catch block
			System.out.println("��ʹ��MQ����");
			e.printStackTrace();
		} 
     }
     /**
      * ��MQ������Ϣ
      * @param message
      * @return
      */
     public static int sendMessage(String message){
    	 int result=0;
    	 try{	
    		 //���ý�Ҫ���ӵĶ�������
    	     // Note. The MQC interface defines all the constants used by the WebSphere MQ Java programming interface 
    	     //(except for completion code constants and error code constants).
    	     //MQOO_INPUT_AS_Q_DEF:Open the queue to get messages using the queue-defined default.
    	     //MQOO_OUTPUT:Open the queue to put messages.
    		 /*Ŀ��ΪԶ�̶��У��������ﲻ������MQOO_INPUT_AS_Q_DEF����*/
    		 //int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT;
    		 /*����ѡ����ʺ�Զ�̶����뱾�ض���*/
    		 int openOptions = MQConstants.MQOO_OUTPUT | MQConstants.MQOO_FAIL_IF_QUIESCING ;
    		 System.out.println("MQConstants.MQOO_OUTPUT :" + MQConstants.MQOO_OUTPUT);
    		 System.out.println("MQConstants.MQOO_FAIL_IF_QUIESCING :" + MQConstants.MQOO_FAIL_IF_QUIESCING);
    		 System.out.println("openOptions :" + openOptions);
	         //���Ӷ��� 
	         //MQQueue provides inquire, set, put and get operations for WebSphere MQ queues. 
	         //The inquire and set capabilities are inherited from MQManagedObject. 
    		 /*�ر��˾����´�*/
 			if(qMgr==null || !qMgr.isConnected()){
 				qMgr = new MQQueueManager(qmName);
 			}
	         MQQueue queue = qMgr.accessQueue(qName, openOptions); 
	         //����һ���򵥵���Ϣ
	         MQMessage putMessage = new MQMessage(); 
	         putMessage.persistence = MQConstants.MQPER_PERSISTENT;
	         putMessage.format = MQConstants.MQFMT_NONE;
	         //�����ݷ�����Ϣ������
	         putMessage.writeUTF(message);	
	         //����д����Ϣ�����ԣ�Ĭ�����ԣ�
	         MQPutMessageOptions pmo = new MQPutMessageOptions();	        
	         //����Ϣд����� 
	         queue.put(putMessage,pmo); 
	         queue.close();
    	 }catch (MQException ex) { 
             System.out.println("A WebSphere MQ error occurred : Completion code " 
             + ex.completionCode + " Reason code " + ex.reasonCode); 
             ex.printStackTrace();
         }catch (IOException ex) { 
             System.out.println("An error occurred whilst writing to the message buffer: " + ex); 
         }catch(Exception ex){
             ex.printStackTrace();
         }finally{
        	 try {
 				qMgr.disconnect();
 			} catch (MQException e) {
 				e.printStackTrace();
 			}
          }
    	 return result;
     }
     /**
      * �Ӷ�����ȥ��ȡ��Ϣ�����������û����Ϣ���ͻᷢ���쳣������û�й�ϵ����TRY...CATCH������ǵ�����������÷���������޷�����˵������Ϣ
      * ���������Խ��÷�������һ������ѭ����while(true){...}֮�У�����Ҫ���õȴ�����Ϊ�ڸ÷����ڲ���û����Ϣ��ʱ����Զ��ȴ���
      * @return
      */
     public static String getMessage(){
    	 String message=null;
    	 try{    		 
    		 //���ý�Ҫ���ӵĶ�������
    	     // Note. The MQC interface defines all the constants used by the WebSphere MQ Java programming interface 
    	     //(except for completion code constants and error code constants).
    	     //MQOO_INPUT_AS_Q_DEF:Open the queue to get messages using the queue-defined default.
    	     //MQOO_OUTPUT:Open the queue to put messages.
    		 int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF | MQConstants.MQOO_OUTPUT;
	    	 MQMessage retrieve = new MQMessage();
	    	 //����ȡ����Ϣ�����ԣ�Ĭ�����ԣ�
	    	 //Set the put message options.�����÷�����Ϣѡ� 
    		 MQGetMessageOptions gmo = new MQGetMessageOptions(); 
    		 gmo.options = gmo.options + MQConstants.MQGMO_SYNCPOINT;//Get messages under sync point control����ͬ��������»�ȡ��Ϣ�� 
    		 gmo.options = gmo.options + MQConstants.MQGMO_WAIT;  // Wait if no messages on the Queue������ڶ�����û����Ϣ��ȴ��� 
    		 gmo.options = gmo.options + MQConstants.MQGMO_FAIL_IF_QUIESCING;// Fail if Qeue Manager Quiescing��������й�����ͣ����ʧ�ܣ� 
    		 gmo.waitInterval = 1000 ;  // Sets the time limit for the wait.�����õȴ��ĺ���ʱ�����ƣ� 
    		 /*�ر��˾����´�*/
 			if(qMgr==null || !qMgr.isConnected()){
 				qMgr = new MQQueueManager(qmName);
 			}
	         MQQueue queue = qMgr.accessQueue(qName, openOptions); 
	         // �Ӷ�����ȡ����Ϣ
	         queue.get(retrieve, gmo);
	         message = retrieve.readUTF();	
	         System.out.println("The message is: " + message); 
	         queue.close();
    	 }catch (MQException ex) { 
             System.out.println("A WebSphere MQ error occurred : Completion code " 
             + ex.completionCode + " Reason code " + ex.reasonCode); 
         }catch (IOException ex) { 
             System.out.println("An error occurred whilst writing to the message buffer: " + ex); 
         }catch(Exception ex){
             ex.printStackTrace();
         }finally{
        	 try {
				qMgr.disconnect();
			} catch (MQException e) {
				e.printStackTrace();
			}
         }
    	 return message;
     }
     public static void main(String args[]) {
    	 
    	 File f = new File("E:\\swift");
    	 
    	 File[] fs = f.listFiles();
    	
         if( fs == null || fs.length == 0 ) {
        	 
        	 System.out.println("this is no swift message !");
        	 
        	 return ;
         }
    	 
    	 String encoding = "UTF-8";  
    	 
    	 for( File file : fs ) {
    		 
    		 Long filelength = file.length();  
    	        
    		 byte[] filecontent = new byte[filelength.intValue()];  
    	        
    		 try {  
    	            FileInputStream in = new FileInputStream(file);  
    	            in.read(filecontent);  
    	            in.close();  
    	     } catch (FileNotFoundException e) {  
    	            
    	    	 e.printStackTrace();  
    	     } catch (IOException e) {  
    	         e.printStackTrace();  
    	 }  
    	        try {  
    	          
    	         //sendMessage(new String(filecontent, encoding) + new Date());
    	       	 System.out.println(new String(filecontent, encoding));
    	        } catch (UnsupportedEncodingException e) {  
    	            System.err.println("The OS does not support " + encoding);  
    	            e.printStackTrace();  
    	        }  
    	 }
    	 /*��������������ͬʱʹ�ã�Ҳ���Ե���ʹ��*/
    	 
    	 //getMessage();
     }
}

