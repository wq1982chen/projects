package com.acca.dubbo.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.acca.dubbo.service.TestService;

public class TestServiceImpl implements TestService {
	
	public static final String HELLO = "Hello";

	public String say(String name) {
		
		return HELLO + "," + name;
	}

	public List<String> getPermissions(Long id) {
		List<String> demo = new ArrayList<String>();
		demo.add(String.format("Permission_%d", id - 1));
        demo.add(String.format("Permission_%d", id));
        demo.add(String.format("Permission_%d", id + 1));
		return demo;
	}

}
