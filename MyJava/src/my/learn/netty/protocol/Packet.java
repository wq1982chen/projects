package my.learn.netty.protocol;

/**
 * �������ݰ�
 * @author Administrator
 *
 */
public abstract class Packet {

	/**
	 * Э��汾
	 * */
	 private Byte version = 1;
	 
	 public Byte getVersion() {
		return version;
	}
	public void setVersion(Byte version) {
		this.version = version;
	}
	/**
	 * ��ȡָ��
	 * */
	 public abstract Byte getCommand();
	 /**
	 * ָ����ڲ��ӿ�
	 * */
	 public interface Command { 
		 
		 public static final byte LOGIN_REQUEST = 1;
		// ��½��Ӧָ��
		 public static final byte LOGIN_RESPONSE = 2;
		 //
		 public static final byte MESSAGE_REQUEST = 3;
		 //
		 public static final byte MESSAGE_RESPONSE = 4;
	 }
}
