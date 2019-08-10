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
	  * 响应码集合
	  * */
	public  interface Code{
		  // 成功的响应码
		  public static final int SUCCESS= 10000;
		  // 失败的响应码
		  public static final int FAIL = 10001;
	  }
	 
}
