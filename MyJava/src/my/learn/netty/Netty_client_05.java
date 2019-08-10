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
		// 建立连接
		bootstrap.connect(IP, port)
			.addListener(future -> {
				// 由于闭包特性 不能修改外部的变量 所有需要在闭包内定义一个相同的变量 拷贝外部变量的值
				int[] finalRetryIndex ;
				if (future.isSuccess()) {
					System.out.println("连接成功");
				} else if(maxRetry == 0){
					System.out.println("到达重试最大次数，放弃重连");
				}else {
					// 初始化 重试计数
					if(retryIndex.length == 0) {
						finalRetryIndex = new int[] {0};
					}else {
						finalRetryIndex = retryIndex;
					}
					//计算时间间隔
					int delay = 1 << finalRetryIndex[0];
					// 执行重试
					System.out.println(new Date()+"连接失败,剩余重连次数:"+maxRetry+","
															+delay+"秒后执行第"+(finalRetryIndex[0]+1)+"次重连...");
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
