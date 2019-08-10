package my.learn.netty;

//  Netty实战 IM即时通讯系统（十二）
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class Netty_server_02 {

	public void start () {
	
		int port = 8020;
		//boss表示监听端口接收新连接的线程组
		//一般情况下 接口线程组配置一个线程即可,NioEventLoopGroup 默认的个数为 CPU核数*2
		NioEventLoopGroup boss = new NioEventLoopGroup();
		//worker 表示处理每一条连接上的数据读写的线程组 
		NioEventLoopGroup worker = new NioEventLoopGroup();
		//ServerBootstrap这个类将引导我们进行服务端的启动工作
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(boss, worker)
			.channel(NioServerSocketChannel.class)	//指定服务端的IO模型为 NIO,
			/*当然也有其他选择,如想IO 模型为 BIO,这里配置OioServerSocketChannel.class类型*/ 
			.childHandler(new ChannelInitializer<NioSocketChannel>() {//为引导类创建一个ChannelInitializer
				//这里主要就是定义每条连接的数据读写、业务处理逻辑 
				//类NioSocketChannel就是Netty对NIO类型的连接的抽象
				//NioServerSocketChannel 和NioSocketChannel的概念相当于BIO 模型中ServerSocket 和Socket 
				@Override
				protected void initChannel(NioSocketChannel ch) throws Exception {
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
						@Override
						protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
							System.out.println(msg);
						}
					});
			}// 总结一下,启动Netty服务端,需指定三个类属性:线程模型\IO模型\处理逻辑,有了这三者之后再调用 bind(8020) 
		}).bind(port);//bind方法是异步的,返回值ChannelFuture,添加一个监听GenericFutureListener，以监听端口是否绑定成功 
	}
	
	public static void main(String[] args) {
		
		Netty_server_02 server = new Netty_server_02();
		server.start();
	}
	
}
