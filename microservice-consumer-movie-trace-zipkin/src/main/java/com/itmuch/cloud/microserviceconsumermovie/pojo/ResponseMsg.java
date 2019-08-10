package com.itmuch.cloud.microserviceconsumermovie.pojo;

//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class ResponseMsg<T> {
	
	private int code;
    private String msg;
    private T data;
    
    public ResponseMsg(int code, String msg, T data) {
    	this.code = code;
    	this.msg = msg;
    	this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
    
}
