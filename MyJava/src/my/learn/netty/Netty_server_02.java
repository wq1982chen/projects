package my.learn.netty;

//  Nettyʵս IM��ʱͨѶϵͳ��ʮ����
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
		//boss��ʾ�����˿ڽ��������ӵ��߳���
		//һ������� �ӿ��߳�������һ���̼߳���,NioEventLoopGroup Ĭ�ϵĸ���Ϊ CPU����*2
		NioEventLoopGroup boss = new NioEventLoopGroup();
		//worker ��ʾ����ÿһ�������ϵ����ݶ�д���߳��� 
		NioEventLoopGroup worker = new NioEventLoopGroup();
		//ServerBootstrap����ཫ�������ǽ��з���˵���������
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(boss, worker)
			.channel(NioServerSocketChannel.class)	//ָ������˵�IOģ��Ϊ NIO,
			/*��ȻҲ������ѡ��,����IO ģ��Ϊ BIO,��������OioServerSocketChannel.class����*/ 
			.childHandler(new ChannelInitializer<NioSocketChannel>() {//Ϊ�����ഴ��һ��ChannelInitializer
				//������Ҫ���Ƕ���ÿ�����ӵ����ݶ�д��ҵ�����߼� 
				//��NioSocketChannel����Netty��NIO���͵����ӵĳ���
				//NioServerSocketChannel ��NioSocketChannel�ĸ����൱��BIO ģ����ServerSocket ��Socket 
				@Override
				protected void initChannel(NioSocketChannel ch) throws Exception {
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
						@Override
						protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
							System.out.println(msg);
						}
					});
			}// �ܽ�һ��,����Netty�����,��ָ������������:�߳�ģ��\IOģ��\�����߼�,����������֮���ٵ��� bind(8020) 
		}).bind(port);//bind�������첽��,����ֵChannelFuture,���һ������GenericFutureListener���Լ����˿��Ƿ�󶨳ɹ� 
	}
	
	public static void main(String[] args) {
		
		Netty_server_02 server = new Netty_server_02();
		server.start();
	}
	
}
