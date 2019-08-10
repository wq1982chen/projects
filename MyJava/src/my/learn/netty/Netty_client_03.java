package my.learn.netty;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

public class Netty_client_03 {
	
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
				//ch.pipeline().addLast(new StringEncoder());
				ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
					
					/**
					 * 	�ͻ������ӽ����ɹ�֮�� �� ����channelActive() 
					 */
					@Override
					public void channelActive(ChannelHandlerContext ctx) throws Exception {
							
							String content = "��ã���������";
							System.out.println(new Date() + " �ͻ���д������->"+content);
							 // 1. ��ȡ����
							 ByteBuf buffer = getByteBuf(ctx,content);
							 // 2. д����
							 ctx.channel().writeAndFlush(buffer);
					}
					
					@Override
					public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
						
						ByteBuf byteBuf = (ByteBuf) msg;
						System.out.println(new Date()+": �ͻ��˶������� ->"
												+ byteBuf.toString(Charset.forName("UTF-8")));
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
		 });
		bootstrap.config().group().schedule(()->{
			connect(bootstrap, "127.0.0.1", 6382,5);
		}, 2, TimeUnit.SECONDS);

	}
}
