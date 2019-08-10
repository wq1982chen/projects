package com.acca.service;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

//import junit.framework.Test;
import junit.framework.TestCase;
//import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TestServiceTest    extends TestCase {
//    /**
//     * Create the test case
//     *
//     * @param testName name of the test case
//     */
//    public TestServiceTest( String testName )
//    {
//        super( testName );
//    }
//
//    /**
//     * @return the suite of tests being tested
//     */
//    public static Test suite()
//    {
//        return new TestSuite( TestServiceTest.class );
//    }
//
//    /**
//     * Rigourous Test :-)
//     */
//    public void testApp()
//    {
//        assertTrue( true );
//    }
	
	public static void main(String args[]) {
		
		ClassPathXmlApplicationContext context = 
											new ClassPathXmlApplicationContext(new String[] { "application.xml" });
		
		context.start();
		
		System.out.println("提供者服务已注册成功");
		
		try {

			System.in.read();// 让此程序一直跑，表示一直提供服务

			} catch (IOException e) {

			e.printStackTrace();

			}
	}
}
