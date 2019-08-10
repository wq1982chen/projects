package my.learn.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MyChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {

			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				
				System.out.println("----------------little bear------------------");
				/*
				 * 调用父类的channelRead() 方法 ， 
				 * 父类的channelRead()方法会调用下一个inboundHandler的channelRead() ,
				 *  并且会把处理完毕的对象传递给下一个inboundHandler
				 */
				super.channelRead(ctx, msg);//确保
			}
}
