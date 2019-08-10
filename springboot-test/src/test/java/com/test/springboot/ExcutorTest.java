package com.test.springboot;

import java.util.concurrent.Callable;

import javax.annotation.Resource;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.test.springboot.dao.model.User;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcutorTest    extends TestCase {
	
	@Resource
	private WebApplicationContext webApplicationContext;
	
	/**
	 * MockMvc讲解
	 * https://www.cnblogs.com/0201zcr/p/5756642.html
	 */
	private MockMvc mvc;
	
	@Before
	public void init() throws Exception{
		 //mvc =  MockMvcBuilders.standaloneSetup(new HelloWorldController()).build();
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
    
	@Test
    public void getHello() throws Exception {
       String str = mvc.perform(MockMvcRequestBuilders.get("/hello")
    		   .contentType(MediaType.APPLICATION_JSON))
    		   .andExpect(MockMvcResultMatchers.status().isOk())
    		   //检查返回的结果中有HelloWorld字符
    		   .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("Hello World")))
    		   .andReturn().getResponse().getContentAsString();
    		   
       System.out.println(str);
    }
	
	@Test
    public void getFindObject() throws Exception {
       String str = mvc.perform(MockMvcRequestBuilders.get("/1/2")
    		   .contentType(MediaType.APPLICATION_JSON_UTF8))
    		   .andExpect(MockMvcResultMatchers.status().isOk())
    		   //针对返回格式为JSON的结果,用“$.属性”获取里面的数据，如我要获取返回数据中的"data.name"，可以写成"$.data.name"
    		   .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
    		   .andReturn().getResponse().getContentAsString();
    		   
       System.out.println(str);
    }
	
	@Test
	public void mthread() {
		
			Callable<Object> callable = new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					// TODO Auto-generated method stub
					return null;
				}
				
			};
	}
}
