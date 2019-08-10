package my.inf.dao;

import my.inf.IUser;

public class UserDAO implements IUser {

	@Override
	public void find(String userId) {
		System.out.println("I find a user");
	}

	@Override
	public void save(String userJson) {
		System.out.println("I saved a user");
	}

}
