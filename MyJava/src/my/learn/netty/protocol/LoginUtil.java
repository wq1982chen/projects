package my.learn.netty.protocol;

import io.netty.channel.Channel;

public class LoginUtil {
	
	/**
	 * @desc 判断登录成功
	 * @param loginResponsePacket
	 * @return 是否登录成功
	 */
	 public static boolean isSuccess(LoginResponsePacket loginResponsePacket) {
		 boolean flag = false;
		 if(loginResponsePacket.getCode() 
				 == LoginResponsePacket.Code.SUCCESS) {
			 flag = true;
		 }
		 return flag;
	 }
	 
	 /**
	  * @desc 标识连接登录成功
	  * @param channel
	  * @return
	  */
	  public static void markAsLogin(Channel channel) {
		  channel.attr(ChannelAttributes.LOGIN).set(true);
	  }

	/**
	 * @desc 判断是否登录
	 * @param channel
	 * @return
	 */
	 public static boolean hasLogin(Channel channel) {
		 boolean flag = false;
		 Boolean attr = channel.attr(ChannelAttributes.LOGIN).get();
		 if(attr == null) return flag;
		 return attr;
	 }
}
