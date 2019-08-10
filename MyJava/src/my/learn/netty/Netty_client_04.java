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
	 * @desc ��������̨�߳�
	 * @param channel
	 */
	 private static void startConsoleThread(Channel channel) {
		 System.out.println("�ͻ��ˣ���������̨�߳�");
		 new Thread(() -> {
			 while (!Thread.interrupted()) {
				if (LoginUtil.hasLogin(channel)) {
					 System.out.print("������Ϣ�����������:");
					 String msg = new Scanner(System.in).nextLine();//�Իس�����Ϊһ��
					 MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
					 messageRequestPacket.setMessage(msg);
					 ByteBuf byteBuf = PacketCodec.INSTANCE.enCode(channel.alloc().buffer(),messageRequestPacket);
					 channel.writeAndFlush(byteBuf);
				 } else {
					 System.out.println("�Զ���¼�У����Ժ�...");
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
		// ��������
		bootstrap.connect(IP, port)
			.addListener(future -> {
				// ���ڱհ����� �����޸��ⲿ�ı��� ������Ҫ�ڱհ��ڶ���һ����ͬ�ı��� �����ⲿ������ֵ
				int[] finalRetryIndex ;
				if (future.isSuccess()) {
					System.out.println("���ӳɹ�");
					startConsoleThread(((ChannelFuture)future).channel());
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
				//ch.pipeline().addLast(new StringEncoder());//��buf��ת�����ַ������󲢴��ݸ�������handler
				ch.pipeline().addLast(new MyChannelInboundHandlerAdapter());
				ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
					
					/**
					 * 	�ͻ������ӽ����ɹ�֮�� �� ����channelActive() 
					 */
					@Override
					public void channelActive(ChannelHandlerContext ctx) throws Exception {
							
							LoginRequestPacket login = new LoginRequestPacket();
							login.setUerId((int)(Math.random()*1000)+1);
							login.setUserName("Ronaldo");
							login.setPassword("Great Forward");
							//���ֲ���ByteBuf����������
							//ctx.alloc().buffer() & ByteBufAllocator.DEFAULT.ioBuffer()
							ByteBuf byteBuf = PacketCodec.INSTANCE.enCode(ctx.alloc().buffer() ,login);
							System.out.println("-----------------before write----------------------");
							ctx.channel().writeAndFlush(byteBuf);
							System.out.println("-----------------after write----------------------");
					}
					
					/**
					 * ��������ڽ��յ�����֮��ᱻ�ص�
					 */
					@Override
					public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
						
						ByteBuf byteBuf = (ByteBuf) msg;
//					System.out.println(new Date()+": �ͻ��˶������� ->"
//												+ byteBuf.toString(Charset.forName("UTF-8")));
						Packet packet = PacketCodec.INSTANCE.deCode(byteBuf);
						
						switch(packet.getCommand()) {
						
							case  Packet.Command.LOGIN_RESPONSE :	 
										LoginResponsePacket response = (LoginResponsePacket)packet;
										if(LoginUtil.isSuccess(response)) {
											LoginUtil.markAsLogin(ctx.channel());
										}
										System.out.printf("�ͻ���: %s �յ��������Ӧ ��%s�� \n",
																new Date(),response.getMsg());
										break;
							case Packet.Command.MESSAGE_RESPONSE :
										MessageResponsePacket messageResponsePacket = (MessageResponsePacket)packet;
										System.out.println("�ͻ���:"+new Date()+ "�յ��������Ϣ --> "+ messageResponsePacket.getMessage());
										break;
							default:
										System.out.println("�ͻ��ˣ�"+new Date()+"�յ�δ֪��ָ�"+packet.getCommand()+"��\n");
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
