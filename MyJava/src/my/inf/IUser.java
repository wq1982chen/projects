package my.inf;

public interface IUser {

	public abstract void find(String userId);
	
	public abstract void save(String userJson);
	
	public default void update(String userJson) {
			System.out.println("User has been update");
	};
}
