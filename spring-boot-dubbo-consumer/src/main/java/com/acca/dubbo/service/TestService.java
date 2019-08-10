package com.acca.dubbo.service;

import java.util.List;

public interface TestService {

	public String say(String name);
	
	public List<String> getPermissions(Long id);
}
