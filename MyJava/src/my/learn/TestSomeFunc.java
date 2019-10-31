package my.learn;

import java.util.Optional;
import java.util.Scanner;

public class TestSomeFunc {
	
	public void calc() {
		//测试运算符优先级，是先进行位运算
		// 3 = 0011
		// 2 = 0010
		// 2<<2 = 1000
		// 3|2<<2 = 0011 | 1000 = 1011 = 11
		System.out.println(3|2<<2); //输出11

		// 测试10除以8的求商和求余
		System.out.println(10&0x07); //输出2,即余数
		System.out.println(10>>3); //输出1,即商
	}
	
	public void testScanner() {
		///*
		//测试Scanner
		Scanner sc=new Scanner(System.in);
		int i = sc.nextInt();
		 //在控制台打印输入的整数
		System.out.println(i);
		//*/
		sc.close();
	}
	
	public void test() {
		
		Person p = new Person();
		
		Optional<Person> optional = Optional.of(p);
		optional.ifPresent(System.out::println); // if(p!=null) System.out....
	}

	
	public static void main(String[] args) {
		
		TestSomeFunc f = new TestSomeFunc();
		f.test();
	}
}

class Person {
	@Override
	public String toString() {
		return "PersonInstance";
	}
}
