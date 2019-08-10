package com.test.springboot.util;


public enum UserSexEnum {
	
	   MAN(1),WOMAN(0);
		
		private int value;
		
		private UserSexEnum(int value) {
			this.value = value;
		}
	
		public int value() {
			return value;
		}
}
