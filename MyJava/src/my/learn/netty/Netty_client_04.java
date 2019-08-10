package my.learn.netty;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import my.learn.netty.protocol.LoginRequestPacket;
import my.learn.netty.protocol.LoginResponsePacket;
import my.learn.netty.protocol.LoginUtil;
import my.learn.netty.protocol.MessageRequestPacket;
import my.learn.netty.protocol.MessageResponsePacket;
import my.learn.netty.protocol.Packet;
import my.learn.netty.protocol.PacketCodec;
import io.netty.channel.ChannelFuture;

public class Netty_client_04 {
	
	/**
	 * @desc 启动控制台线程
	 * @param channel
	 */
	 private static void startConsoleThread(Channel channel) {
		 System.out.println("客户端：启动控制台线程");
		 new Thread(() -> {
			 while (!Thread.interrupted()) {
				if (LoginUtil.hasLogin(channel)) {
					 System.out.print("输入消息发送至服务端:");
					 String msg = new Scanner(System.in).nextLine();//以回车换行为一行
					 MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
					 messageRequestPacket.setMessage(msg);
					 ByteBuf byteBuf = PacketCodec.INSTANCE.enCode(channel.alloc().buffer(),messageRequestPacket);
					 channel.writeAndFlush(byteBuf);
				 } else {
					 System.out.println("自动登录中，请稍候...");
					 try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				 }
			 }
		 }).start();
	}
	
	public static void connect(Bootstrap bootstrap, String IP, int port ,int maxRetry , int... retryIndex) {
		// 建立连接
		bootstrap.connect(IP, port)
			.addListener(future -> {
				// 由于闭包特性 不能修改外部的变量 所有需要在闭包内定义一个相同的变量 拷贝外部变量的值
				int[] finalRetryIndex ;
				if (future.isSuccess()) {
					System.out.println("连接成功");
					startConsoleThread(((ChannelFuture)future).channel());
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
				//ch.pipeline().addLast(new StringEncoder());//将buf会转换成字符串对象并传递给后续的handler
				ch.pipeline().addLast(new MyChannelInboundHandlerAdapter());
				ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
					
					/**
					 * 	客户端连接建立成功之后 ， 调用channelActive() 
					 */
					@Override
					public void channelActive(ChannelHandlerContext ctx) throws Exception {
							
							LoginRequestPacket login = new LoginRequestPacket();
							login.setUerId((int)(Math.random()*1000)+1);
							login.setUserName("Ronaldo");
							login.setPassword("Great Forward");
							//两种产生ByteBuf方法的区别
							//ctx.alloc().buffer() & ByteBufAllocator.DEFAULT.ioBuffer()
							ByteBuf byteBuf = PacketCodec.INSTANCE.enCode(ctx.alloc().buffer() ,login);
							System.out.println("-----------------before write----------------------");
							ctx.channel().writeAndFlush(byteBuf);
							System.out.println("-----------------after write----------------------");
					}
					
					/**
					 * 这个方法在接收到数据之后会被回调
					 */
					@Override
					public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
						
						ByteBuf byteBuf = (ByteBuf) msg;
//					System.out.println(new Date()+": 客户端读到数据 ->"
//												+ byteBuf.toString(Charset.forName("UTF-8")));
						Packet packet = PacketCodec.INSTANCE.deCode(byteBuf);
						
						switch(packet.getCommand()) {
						
							case  Packet.Command.LOGIN_RESPONSE :	 
										LoginResponsePacket response = (LoginResponsePacket)packet;
										if(LoginUtil.isSuccess(response)) {
											LoginUtil.markAsLogin(ctx.channel());
										}
										System.out.printf("客户端: %s 收到服务端响应 【%s】 \n",
																new Date(),response.getMsg());
										break;
							case Packet.Command.MESSAGE_RESPONSE :
										MessageResponsePacket messageResponsePacket = (MessageResponsePacket)packet;
										System.out.println("客户端:"+new Date()+ "收到服务端消息 --> "+ messageResponsePacket.getMessage());
										break;
							default:
										System.out.println("客户端："+new Date()+"收到未知的指令【"+packet.getCommand()+"】\n");
										break;
						}
					}
				});
				ch.pipeline().addFirst(new MyChannelOutboundHandlerAdapter());
			}
		 });
		bootstrap.config().group().schedule(()->{
			connect(bootstrap, "127.0.0.1", 6382,5);
		}, 2, TimeUnit.SECONDS);

	}
}
