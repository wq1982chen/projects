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
		//boss��ʾ�����˿ڽ��������ӵ��߳���
		//һ������� �ӿ��߳�������һ���̼߳���,NioEventLoopGroup Ĭ�ϵĸ���Ϊ CPU����*2
		NioEventLoopGroup boss = new NioEventLoopGroup();
		//worker ��ʾ����ÿһ�������ϵ����ݶ�д���߳��� 
		NioEventLoopGroup worker = new NioEventLoopGroup();
		//ServerBootstrap����ཫ�������ǽ��з���˵���������
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(boss, worker)
			//������˵�channel ָ��һЩ�Զ�������(�Ǳ���)
			.attr(AttributeKey.newInstance("serverName"), "NettyServer")
		   /* option
			 * �������channel ����һЩTCP����(�Ǳ���)
			 * ��ʾϵͳ���ڴ���Ѿ�����������ֵ�����Ķ��е���󳤶� ��
			 * �����������Ƶ���� �����������������ӽ����� �����ʵ������������
			 */
			.option(ChannelOption.SO_BACKLOG, 1024)
			.channel(NioServerSocketChannel.class)	//ָ������˵�IOģ��Ϊ NIO,
			/*��ȻҲ������ѡ��,����IO ģ��Ϊ BIO,��������OioServerSocketChannel.class����*/ 
			//handler����ָ����������������е�һЩ�߼�(�Ǳ���)
			.handler(new ChannelInitializer<Channel>() {//
				@Override
				protected void initChannel(Channel channel) throws Exception {
					// ȡ�����������
					Attribute<Object> serverName = channel.attr(AttributeKey.valueOf("serverName") );
					System.out.printf("%s�����������...\n",serverName.get() ); 
				}
			})
			//ͨ��childAttr ��ÿһ�����������Զ�������(�Ǳ���)
			.childAttr(AttributeKey.newInstance("clientName"), "NettyClient")
			//childHandler() ����ָ���������������ݵ�ҵ���߼�
			.childHandler(new ChannelInitializer<NioSocketChannel>() {
			  /*������Ҫ���Ƕ���ÿ�����ӵ����ݶ�д��ҵ�����߼� 
				*NioSocketChannel����Netty��NIO���͵����ӵĳ���
				*NioServerSocketChannel ��NioSocketChannel�ĸ����൱��BIO ģ����ServerSocket ��Socket 
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
						 * ��������ڽ��յ�����֮��ᱻ�ص�
						 */
						@Override
						public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
								//��ȡ�ͻ�������
								ByteBuf readBuf = (ByteBuf) msg;
								System.out.println(new Date() + ": ����˶�������->"
															+ readBuf.toString(Charset.forName("UTF-8")));
								//��ͻ��˻ظ�����
								String content = "��ã�����ɭ!";
								System.out.println(new Date() + ": �����д������->"+ content);
								ByteBuf writeBuf = getByteBuf(ctx,content);
								ctx.channel().writeAndFlush(writeBuf);
						}
						
						private ByteBuf getByteBuf(ChannelHandlerContext ctx,String content) {
							// ��ȡ�����Ƴ��� ByteBuffer
							ByteBuf buf = ctx.alloc().buffer();
							// ׼������
							byte[] bs = content.getBytes(Charset.forName("UTF-8"));
							// ��������䵽 buf
							buf.writeBytes(bs);
							return buf;
					}
					});
					
				}
			})
			/* childOption�������Ը�ÿ����������һЩTCP�ײ���ص�����(�Ǳ���)
			 	 https://www.cnblogs.com/googlemeoften/p/6082785.html �������*/
			.childOption(ChannelOption.SO_KEEPALIVE, true);//SO_KEEPALIVE��ʾ�Ƿ���TCP�ײ���������
		bind(serverBootstrap,port); 
	}
	
	private static void bind(ServerBootstrap serverBootstrap, int port) {
		//bind() �������ʱ�첽��,���ԶԷ���ֵChannelFuture���Ӽ���,ʵ�ְ󶨶˿ڼ���
		//����ʵ�ֵ��ǰ󶨼����˿ڣ��类ռ�����Զ�����һ���˿ں�,ֱ���󶨳ɹ�Ϊֹ(�Ǳ���)
		serverBootstrap.bind(port).addListener(
				new GenericFutureListener<Future<? super Void>>() {
					@Override
					public void operationComplete(Future<? super Void> future) throws Exception {
						if(future.isSuccess()) {
							System.out.println("�ɹ��󶨶˿ڣ�"+port);
						}else {
							System.out.println("�󶨶˿�ʧ�ܣ�"+ port);
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
