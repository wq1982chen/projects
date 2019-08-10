package my.learn.netty.protocol;

public class LoginRequestPacket extends Packet {

	private Integer uerId ;
	 private String userName;
	 private String password;
	 
	 @Override
	 public Byte getCommand() {
		 return Command.LOGIN_REQUEST;
	 }

	public Integer getUerId() {
		return uerId;
	}

	public void setUerId(Integer uerId) {
		this.uerId = uerId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	 
}
