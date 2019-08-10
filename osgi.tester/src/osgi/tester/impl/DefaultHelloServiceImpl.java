package osgi.tester.impl;

import osgi.tester.interfaces.IHello;

public class DefaultHelloServiceImpl implements IHello {

	@Override
	public String getHello() {
		return "Hello osgi,service";
	}

}
