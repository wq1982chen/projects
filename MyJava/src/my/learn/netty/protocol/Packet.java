package my.learn.netty.protocol;

/**
 * 定义数据包
 * @author Administrator
 *
 */
public abstract class Packet {

	/**
	 * 协议版本
	 * */
	 private Byte version = 1;
	 
	 public Byte getVersion() {
		return version;
	}
	public void setVersion(Byte version) {
		this.version = version;
	}
	/**
	 * 获取指令
	 * */
	 public abstract Byte getCommand();
	 /**
	 * 指令集合内部接口
	 * */
	 public interface Command { 
		 
		 public static final byte LOGIN_REQUEST = 1;
		// 登陆响应指令
		 public static final byte LOGIN_RESPONSE = 2;
		 //
		 public static final byte MESSAGE_REQUEST = 3;
		 //
		 public static final byte MESSAGE_RESPONSE = 4;
	 }
}
