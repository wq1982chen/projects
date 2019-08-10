package my.jvm;

public class ClazzLoading {

	public static void main(String[] args) {
		
		/*
		 * 1. Son类并没有被初始化
		 * 2. Father的构造函数并没有被调用
		 */
		System.out.println("Father ' age :" + Son.age );
	}
}

class GrandFather {
	
	static {
		
		System.out.println("GrandFather Say OK ");
	}
}

class Father extends GrandFather {
	
	public static int age = 25;
	
	static {
		
		System.out.println("Father Say OK ");
	}
	
	public Father() {
		
		System.out.println("Father Construct OK ");
	}
}

class Son extends Father {
	
	static {
		
		System.out.println("Son Say OK ");
	}
}
