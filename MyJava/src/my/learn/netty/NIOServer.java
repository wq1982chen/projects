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
 * Ϊʲô��Ҷ���Ը���� JDK ԭ�� NIO ���п����أ�
 * ��̸��ӡ����ģ����
 * JDK �� NIO �ײ��� epoll ʵ�֣���ʵ�ֱ���ڸ���Ŀ���ѯ bug �ᵼ�� cpu ��� 100%
 * ��Ŀ�Ӵ�֮������ʵ�ֵ� NIO �����׳��ָ��� bug��ά���ɱ��ϸߣ�������һ������Ҷ����ܱ�֤û�� bug
 * 
 * Netty �ĳ��ֺܴ�̶��ϸ����� JDK ԭ�� NIO �����ڵ�һЩ�����������ܵ����⡣
 */
public class NIOServer {

	public static void main(String[] args) throws IOException {

		// 1. serverSelector������ѯ�Ƿ����µ����ӣ�����˼�⵽�µ�����֮�󣬲��ٴ���һ���µ��̣߳� 
		// ����ֱ�ӽ������Ӱ󶨵�clientSelector�ϣ������Ͳ��� IO ģ���� 1w �� while ѭ�������� 
		Selector serverSelector = Selector.open();
		// 2. clientSelector������ѯ�����Ƿ������ݿɶ� 
		Selector clientSelector = Selector.open();
		
		//Selector s = SelectorProvider.provider().openSelector();//ϵͳĬ�ϵĵ���

		new Thread(() -> {
			try {
				// ��ӦIO����з��������
				ServerSocketChannel listenerChannel = ServerSocketChannel.open();
				listenerChannel.socket().bind(new InetSocketAddress(3333));
				listenerChannel.configureBlocking(false);
				listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
				while (true) {
					// ����Ƿ����µ����ӣ������1ָ����������ʱ��Ϊ 1ms
					if (serverSelector.select(1) > 0) {

						Set<SelectionKey> set = serverSelector.selectedKeys();
						Iterator<SelectionKey> keyIterator = set.iterator();
						while (keyIterator.hasNext()) {
							SelectionKey key = keyIterator.next();
							if (key.isAcceptable()) {
								try {
									// (1)ÿ��һ�������ӣ�����Ҫ����һ���̣߳�����ֱ��ע�ᵽclientSelector
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

					// (2) ������ѯ�Ƿ�����Щ���������ݿɶ��������1ָ����������ʱ��Ϊ 1ms
					if (clientSelector.select(1) > 0) {
						Set<SelectionKey> set = clientSelector.selectedKeys();
						Iterator<SelectionKey> keyIterator = set.iterator();
						while (keyIterator.hasNext()) {

							SelectionKey key = keyIterator.next();
							if (key.isReadable()) {

								try {
									SocketChannel clientChannel = (SocketChannel) key.channel();
									ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
									// (3) ���� Buffer
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