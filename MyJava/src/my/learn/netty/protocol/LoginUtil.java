package my.learn.netty.protocol;

import io.netty.channel.Channel;

public class LoginUtil {
	
	/**
	 * @desc �жϵ�¼�ɹ�
	 * @param loginResponsePacket
	 * @return �Ƿ��¼�ɹ�
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
	  * @desc ��ʶ���ӵ�¼�ɹ�
	  * @param channel
	  * @return
	  */
	  public static void markAsLogin(Channel channel) {
		  channel.attr(ChannelAttributes.LOGIN).set(true);
	  }

	/**
	 * @desc �ж��Ƿ��¼
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
