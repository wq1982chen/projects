package my.distributed.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 
https://www.cnblogs.com/felixzh/p/5869212.html

 ���������͵�znode�� 
1��PERSISTENT-�־û�Ŀ¼�ڵ�,�ͻ�����zookeeper�Ͽ����Ӻ�,�ýڵ����ɴ��� 
2��PERSISTENT_SEQUENTIAL-�־û�˳����Ŀ¼�ڵ�,�ͻ�����zookeeper�Ͽ����Ӻ�,�ýڵ����ɴ���,ֻ��Zookeeper���ýڵ����ƽ���˳���� 
3��EPHEMERAL-��ʱĿ¼�ڵ� ,�ͻ�����zookeeper�Ͽ����Ӻ�,�ýڵ㱻ɾ�� 
4��EPHEMERAL_SEQUENTIAL-��ʱ˳����Ŀ¼�ڵ� ,�ͻ�����zookeeper�Ͽ����Ӻ�,�ýڵ㱻ɾ��,ֻ��Zookeeper���ýڵ����ƽ���˳���� 
 *
 */
public class ZKOnDocker {

	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		ZooKeeper zk = new ZooKeeper("192.168.99.100:2181", 3000, new MyWatcher());
		
		String node = "/fistZkNode";
		String child = node+"/sub1";
		
		Stat stat = zk.exists(node, false);
		if( null == stat ) {
			
			/**
			  * �����ڵ�
	          * ����һ��·����ַ
	          * ����������Ҫ��������ݣ���Ҫת�����ֽ�����
	          * ��������ACL���ʿ����б�Access control list��,
	          *      ��������ΪArrayList<ACL>��Ids�ӿ��ṩ��һЩĬ�ϵ�ֵ���Ե��á�
	          *      OPEN_ACL_UNSAFE     This is a completely open ACL 
	          *                          ����һ����ȫ���ŵ�ACL������ȫ
	          *      CREATOR_ALL_ACL     This ACL gives the
	          *                           creators authentication id's all permissions.
	          *                          ���ACL������Щ��Ȩ�˵��û��߱�Ȩ��
	          *      READ_ACL_UNSAFE     This ACL gives the world the ability to read.
	          *                          ���ACL�����û�����Ȩ�ޣ�Ҳ���ǻ�ȡ����֮���Ȩ�ޡ�
	          * �����ģ������Ľڵ����͡�ö��ֵCreateMode
	          *      PERSISTENT (0, false, false)
	          *      PERSISTENT_SEQUENTIAL (2, false, true)
	          *          ���������ʹ����Ķ��ǳ־������ͽڵ㣬�ػ�����֮�󲻻��Զ�ɾ����
	          *          �������ڣ��ڶ��������������Ľڵ��������һ��������������ֵ
	          *      EPHEMERAL (1, true, false)
	          *      EPHEMERAL_SEQUENTIAL (3, true, true)
	          *          ����������������������ʱ�����ͽڵ㣬�ڻػ�����֮���Զ�ɾ����
	          *          �������ڣ��ڶ�����������������ʱ�ͽڵ����������һ��������������ֵ��
	          * ���create()�����ķ���ֵ�Ǵ����Ľڵ��ʵ��·��
	          */
			String createResult = zk.create(node, "firstNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("create node :" + createResult);
		}
		/**
         *  �鿴/node�ڵ����ݣ�����Ӧ�����"conan"
         *  ����һ����ȡ�ڵ��·��
         *  ��������˵���Ƿ���Ҫ�۲�ýڵ㣬����Ϊtrue�����趨����Ĭ�ϵĹ۲���
         *  ��������stat�࣬����ڵ����Ϣ���������ݰ汾��Ϣ������ʱ�䣬�޸�ʱ�����Ϣ
         */
		byte[] b = zk.getData(node, false, stat);
		System.out.println("byte[] b convert to String : " + new String(b));
		
		  // ����һ����Ŀ¼�ڵ�
	     if (zk.exists(child, true) == null) {
	         zk.create(child, "sub1".getBytes(),
	        		 ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	         System.out.println("����һ����Ŀ¼�ڵ�:create /firstNode/sub1 sub1");
	         // �鿴node�ڵ�
	         System.out.println("�鿴node�ڵ�:ls /node => "
	                 + zk.getChildren(node, true));
	     }
	     System.out.println("*******************************************************");
	
		 // ɾ���ڵ�
	     if (zk.exists(child, true) != null) {
	         zk.delete(child, -1);
	         zk.delete(node, -1);
	         // �鿴���ڵ�
	         System.out.println("ɾ���ڵ�:ls / => " + zk.getChildren("/", true));
	     }
	     // �ر�����
		zk.close();
	}
	
}

class MyWatcher implements Watcher {

	public void process(WatchedEvent event) {
		
		System.out.println("--------------------------");
		System.out.println("path:" + event.getPath());
		System.out.println("type:" + event.getType());
		System.out.println("state:" + event.getState());
		System.out.println("--------------------------");
		
	}
	
	
}
