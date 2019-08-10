package my.learn.netty;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Netty_client_05 {
	
	public static void connect(Bootstrap bootstrap, String IP, int port ,int maxRetry , int... retryIndex) {
		// ��������
		bootstrap.connect(IP, port)
			.addListener(future -> {
				// ���ڱհ����� �����޸��ⲿ�ı��� ������Ҫ�ڱհ��ڶ���һ����ͬ�ı��� �����ⲿ������ֵ
				int[] finalRetryIndex ;
				if (future.isSuccess()) {
					System.out.println("���ӳɹ�");
				} else if(maxRetry == 0){
					System.out.println("������������������������");
				}else {
					// ��ʼ�� ���Լ���
					if(retryIndex.length == 0) {
						finalRetryIndex = new int[] {0};
					}else {
						finalRetryIndex = retryIndex;
					}
					//����ʱ����
					int delay = 1 << finalRetryIndex[0];
					// ִ������
					System.out.println(new Date()+"����ʧ��,ʣ����������:"+maxRetry+","
															+delay+"���ִ�е�"+(finalRetryIndex[0]+1)+"������...");
					bootstrap.config().group().schedule(()->{
						connect(bootstrap, IP, port , maxRetry-1 , finalRetryIndex[0]+1);
					}, delay, TimeUnit.SECONDS);
				}
			});
	}
	
	public static void main(String[] args) throws InterruptedException {
		Bootstrap bootstrap = new Bootstrap();
		NioEventLoopGroup group = new NioEventLoopGroup();
		bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) {
				
			}
		 });
		bootstrap.config().group().schedule(()->{
			connect(bootstrap, "127.0.0.1", 6382,5);
		}, 2, TimeUnit.SECONDS);

	}
}
