package com.test.springboot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.springboot.prop.LoadProperties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestLoadProperties implements  ApplicationContextAware {

	@Autowired
	LoadProperties prop;
	
	@Test
	public void testFieldValue() {
		assertNotNull(prop);
		assertNotNull(prop.getFoo());
		assertEquals("bar",prop.getFoo());
		
		assertNotNull(prop.getUrl());
		assertEquals(2, prop.getUrl().size());
	}
	
	@Test
	public void testLoadPropertiesDirectly() {
		assertNotNull(context);
		Binder binder = Binder.get(context.getEnvironment());
		List<String> url = binder.bind("com.didispace.url", Bindable.listOf(String.class)).get();
		assertNotNull(url);
		assertEquals(2, url.size());
	}

	//通过实现ApplicationContextAware获取ApplicationContext
	private ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

}
