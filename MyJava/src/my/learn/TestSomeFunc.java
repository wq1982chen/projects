package my.learn;

import java.util.Optional;
import java.util.Scanner;

public class TestSomeFunc {
	
	public void calc() {
		//������������ȼ������Ƚ���λ����
		// 3 = 0011
		// 2 = 0010
		// 2<<2 = 1000
		// 3|2<<2 = 0011 | 1000 = 1011 = 11
		System.out.println(3|2<<2); //���11

		// ����10����8�����̺�����
		System.out.println(10&0x07); //���2,������
		System.out.println(10>>3); //���1,����
	}
	
	public void testScanner() {
		///*
		//����Scanner
		Scanner sc=new Scanner(System.in);
		int i = sc.nextInt();
		 //�ڿ���̨��ӡ���������
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
