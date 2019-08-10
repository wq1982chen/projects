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
public class ZKApp {

	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		
		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 3000, new TestWatcher());
		
		String node = "/node2";
		
		Stat stat = zk.exists(node, false);
		if( null == stat ) {
			//�����ڵ�
			String createResult = zk.create(node, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("createResult:" + createResult);
		}
		byte[] b = zk.getData(node, false, stat);
		System.out.println("byte[] b convert to String : " + new String(b));
		zk.close();
	}
	
}

class TestWatcher implements Watcher {

	public void process(WatchedEvent event) {
		
		System.out.println("--------------------------");
		System.out.println("path:" + event.getPath());
		System.out.println("type:" + event.getType());
		System.out.println("state:" + event.getState());
		System.out.println("--------------------------");
		
	}
	
	
}
