package my.learn.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {

			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				
				System.out.println("----------------little bear------------------");
				/*
				 * ���ø����channelRead() ���� �� 
				 * �����channelRead()�����������һ��inboundHandler��channelRead() ,
				 *  ���һ�Ѵ�����ϵĶ��󴫵ݸ���һ��inboundHandler
				 */
				super.channelRead(ctx, msg);//ȷ��
			}
}
