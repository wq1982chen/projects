package my.learn.netty.protocol;

public class LoginResponsePacket extends Packet {

	 private String msg;
	 private int code;
	 
	 @Override
	 public Byte getCommand() {
		 return Command.LOGIN_RESPONSE;
	 }

	 
	 public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}

	/**
	  * ��Ӧ�뼯��
	  * */
	public  interface Code{
		  // �ɹ�����Ӧ��
		  public static final int SUCCESS= 10000;
		  // ʧ�ܵ���Ӧ��
		  public static final int FAIL = 10001;
	  }
	 
}
