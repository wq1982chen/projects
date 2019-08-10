package my.learn.netty;

import java.nio.charset.Charset;
import java.util.Date;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class Netty_server_03 {

	public void start () {
	
		int port = 6382;
		//boss表示监听端口接收新连接的线程组
		//一般情况下 接口线程组配置一个线程即可,NioEventLoopGroup 默认的个数为 CPU核数*2
		NioEventLoopGroup boss = new NioEventLoopGroup();
		//worker 表示处理每一条连接上的数据读写的线程组 
		NioEventLoopGroup worker = new NioEventLoopGroup();
		//ServerBootstrap这个类将引导我们进行服务端的启动工作
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(boss, worker)
			//给服务端的channel 指定一些自定义属性(非必须)
			.attr(AttributeKey.newInstance("serverName"), "NettyServer")
		   /* option
			 * 给服务端channel 设置一些TCP属性(非必须)
			 * 表示系统用于存放已经完成三次握手的请求的队列的最大长度 ，
			 * 如果建立连接频繁， 服务器处理创建新连接较慢， 可以适当调大这个参数
			 */
			.option(ChannelOption.SO_BACKLOG, 1024)
			.channel(NioServerSocketChannel.class)	//指定服务端的IO模型为 NIO,
			/*当然也有其他选择,如想IO 模型为 BIO,这里配置OioServerSocketChannel.class类型*/ 
			//handler用于指定服务端启动过程中的一些逻辑(非必须)
			.handler(new ChannelInitializer<Channel>() {//
				@Override
				protected void initChannel(Channel channel) throws Exception {
					// 取出服务端属性
					Attribute<Object> serverName = channel.attr(AttributeKey.valueOf("serverName") );
					System.out.printf("%s服务端启动中...\n",serverName.get() ); 
				}
			})
			//通过childAttr 给每一条连接设置自定义属性(非必须)
			.childAttr(AttributeKey.newInstance("clientName"), "NettyClient")
			//childHandler() 用于指定处理新连接数据的业务逻辑
			.childHandler(new ChannelInitializer<NioSocketChannel>() {
			  /*这里主要就是定义每条连接的数据读写、业务处理逻辑 
				*NioSocketChannel就是Netty对NIO类型的连接的抽象
				*NioServerSocketChannel 和NioSocketChannel的概念相当于BIO 模型中ServerSocket 和Socket 
				*/
				@Override
				protected void initChannel(NioSocketChannel childChannel) throws Exception {
//					childChannel.pipeline().addLast(new StringDecoder());
//					childChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
//						@Override
//						protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//							System.out.println(msg);
//						}
//					});
//					Attribute<Object> clientName = childChannel.attr(AttributeKey.valueOf("clientName"));
//					System.out.printf("%s ...\n",clientName.get() ); 
					childChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
							
						/**
						 * 这个方法在接收到数据之后会被回调
						 */
						@Override
						public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
								//读取客户端数据
								ByteBuf readBuf = (ByteBuf) msg;
								System.out.println(new Date() + ": 服务端读到数据->"
															+ readBuf.toString(Charset.forName("UTF-8")));
								//向客户端回复数据
								String content = "你好，王先森!";
								System.out.println(new Date() + ": 服务端写出数据->"+ content);
								ByteBuf writeBuf = getByteBuf(ctx,content);
								ctx.channel().writeAndFlush(writeBuf);
						}
						
						private ByteBuf getByteBuf(ChannelHandlerContext ctx,String content) {
							// 获取二进制抽象 ByteBuffer
							ByteBuf buf = ctx.alloc().buffer();
							// 准备数据
							byte[] bs = content.getBytes(Charset.forName("UTF-8"));
							// 把数据填充到 buf
							buf.writeBytes(bs);
							return buf;
					}
					});
					
				}
			})
			/* childOption方法可以给每条连接设置一些TCP底层相关的属性(非必须)
			 	 https://www.cnblogs.com/googlemeoften/p/6082785.html 参数详解*/
			.childOption(ChannelOption.SO_KEEPALIVE, true);//SO_KEEPALIVE表示是否开启TCP底层心跳机制
		bind(serverBootstrap,port); 
	}
	
	private static void bind(ServerBootstrap serverBootstrap, int port) {
		//bind() 这个方法时异步的,可以对返回值ChannelFuture增加监听,实现绑定端口监听
		//这里实现的是绑定监听端口，如被占用则自动绑定下一个端口号,直至绑定成功为止(非必须)
		serverBootstrap.bind(port).addListener(
				new GenericFutureListener<Future<? super Void>>() {
					@Override
					public void operationComplete(Future<? super Void> future) throws Exception {
						if(future.isSuccess()) {
							System.out.println("成功绑定端口："+port);
						}else {
							System.out.println("绑定端口失败："+ port);
							bind(serverBootstrap, port+1);
						}
					}
				});
	}
	
	public static void main(String[] args) {
		
		Netty_server_03 server = new Netty_server_03();
		server.start();
	}
	
}
