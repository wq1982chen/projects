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

 有四种类型的znode： 
1、PERSISTENT-持久化目录节点,客户端与zookeeper断开连接后,该节点依旧存在 
2、PERSISTENT_SEQUENTIAL-持久化顺序编号目录节点,客户端与zookeeper断开连接后,该节点依旧存在,只是Zookeeper给该节点名称进行顺序编号 
3、EPHEMERAL-临时目录节点 ,客户端与zookeeper断开连接后,该节点被删除 
4、EPHEMERAL_SEQUENTIAL-临时顺序编号目录节点 ,客户端与zookeeper断开连接后,该节点被删除,只是Zookeeper给该节点名称进行顺序编号 
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
			  * 创建节点
	          * 参数一：路径地址
	          * 参数二：想要保存的数据，需要转换成字节数组
	          * 参数三：ACL访问控制列表（Access control list）,
	          *      参数类型为ArrayList<ACL>，Ids接口提供了一些默认的值可以调用。
	          *      OPEN_ACL_UNSAFE     This is a completely open ACL 
	          *                          这是一个完全开放的ACL，不安全
	          *      CREATOR_ALL_ACL     This ACL gives the
	          *                           creators authentication id's all permissions.
	          *                          这个ACL赋予那些授权了的用户具备权限
	          *      READ_ACL_UNSAFE     This ACL gives the world the ability to read.
	          *                          这个ACL赋予用户读的权限，也就是获取数据之类的权限。
	          * 参数四：创建的节点类型。枚举值CreateMode
	          *      PERSISTENT (0, false, false)
	          *      PERSISTENT_SEQUENTIAL (2, false, true)
	          *          这两个类型创建的都是持久型类型节点，回话结束之后不会自动删除。
	          *          区别在于，第二个类型所创建的节点名后会有一个单调递增的数值
	          *      EPHEMERAL (1, true, false)
	          *      EPHEMERAL_SEQUENTIAL (3, true, true)
	          *          这两个类型所创建的是临时型类型节点，在回话结束之后，自动删除。
	          *          区别在于，第二个类型所创建的临时型节点名后面会有一个单调递增的数值。
	          * 最后create()方法的返回值是创建的节点的实际路径
	          */
			String createResult = zk.create(node, "firstNode".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("create node :" + createResult);
		}
		/**
         *  查看/node节点数据，这里应该输出"conan"
         *  参数一：获取节点的路径
         *  参数二：说明是否需要观察该节点，设置为true，则设定共享默认的观察器
         *  参数三：stat类，保存节点的信息。例如数据版本信息，创建时间，修改时间等信息
         */
		byte[] b = zk.getData(node, false, stat);
		System.out.println("byte[] b convert to String : " + new String(b));
		
		  // 创建一个子目录节点
	     if (zk.exists(child, true) == null) {
	         zk.create(child, "sub1".getBytes(),
	        		 ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	         System.out.println("创建一个子目录节点:create /firstNode/sub1 sub1");
	         // 查看node节点
	         System.out.println("查看node节点:ls /node => "
	                 + zk.getChildren(node, true));
	     }
	     System.out.println("*******************************************************");
	
		 // 删除节点
	     if (zk.exists(child, true) != null) {
	         zk.delete(child, -1);
	         zk.delete(node, -1);
	         // 查看根节点
	         System.out.println("删除节点:ls / => " + zk.getChildren("/", true));
	     }
	     // 关闭连接
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
