package my.learn.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class MyChannelOutboundHandlerAdapter extends ChannelOutboundHandlerAdapter {

		@Override
		public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
			System.out.println("------------------little start------------------------");
			super.write(ctx, msg, promise);
		}
}
