package com.test.springboot.webflux;

public class ResourceNotFoundException extends Exception  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	ResourceNotFoundException(String msg){
		super(msg);
	}
}
