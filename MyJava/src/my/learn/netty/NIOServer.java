package my.learn.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * 为什么大家都不愿意用 JDK 原生 NIO 进行开发呢？
 * 编程复杂、编程模型难
 * JDK 的 NIO 底层由 epoll 实现，该实现饱受诟病的空轮询 bug 会导致 cpu 飙升 100%
 * 项目庞大之后，自行实现的 NIO 很容易出现各类 bug，维护成本较高，下面这一坨代码我都不能保证没有 bug
 * 
 * Netty 的出现很大程度上改善了 JDK 原生 NIO 所存在的一些让人难以忍受的问题。
 */
public class NIOServer {

	public static void main(String[] args) throws IOException {

		// 1. serverSelector负责轮询是否有新的连接，服务端监测到新的连接之后，不再创建一个新的线程， 
		// 而是直接将新连接绑定到clientSelector上，这样就不用 IO 模型中 1w 个 while 循环在死等 
		Selector serverSelector = Selector.open();
		// 2. clientSelector负责轮询连接是否有数据可读 
		Selector clientSelector = Selector.open();
		
		//Selector s = SelectorProvider.provider().openSelector();//系统默认的调用

		new Thread(() -> {
			try {
				// 对应IO编程中服务端启动
				ServerSocketChannel listenerChannel = ServerSocketChannel.open();
				listenerChannel.socket().bind(new InetSocketAddress(3333));
				listenerChannel.configureBlocking(false);
				listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
				while (true) {
					// 监测是否有新的连接，这里的1指的是阻塞的时间为 1ms
					if (serverSelector.select(1) > 0) {

						Set<SelectionKey> set = serverSelector.selectedKeys();
						Iterator<SelectionKey> keyIterator = set.iterator();
						while (keyIterator.hasNext()) {
							SelectionKey key = keyIterator.next();
							if (key.isAcceptable()) {
								try {
									// (1)每来一个新连接，不需要创建一个线程，而是直接注册到clientSelector
									SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
									clientChannel.configureBlocking(false);
									clientChannel.register(clientSelector, SelectionKey.OP_READ);
								} finally {
									keyIterator.remove();
								}
							}
						}
					}
				}
			} catch (IOException ignored) {
			}
		}).start();

		new Thread(() -> {
			try {
				while (true) {

					// (2) 批量轮询是否有哪些连接有数据可读，这里的1指的是阻塞的时间为 1ms
					if (clientSelector.select(1) > 0) {
						Set<SelectionKey> set = clientSelector.selectedKeys();
						Iterator<SelectionKey> keyIterator = set.iterator();
						while (keyIterator.hasNext()) {

							SelectionKey key = keyIterator.next();
							if (key.isReadable()) {

								try {
									SocketChannel clientChannel = (SocketChannel) key.channel();
									ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
									// (3) 面向 Buffer
									clientChannel.read(byteBuffer);
									byteBuffer.flip();
									System.out.println(
											Charset.defaultCharset().newDecoder().decode(byteBuffer).toString());
								}finally {
									keyIterator.remove();
									key.interestOps(SelectionKey.OP_READ);
								}
							}
						}
					}
				}
			}catch (IOException ignored) {}
		}).start();
	}
}