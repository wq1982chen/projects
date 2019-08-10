package my.learn.annotation;

@Description("I'm a class annotation")
public class Child implements Pepole {

	private String name;
	@Description("I'm a method annotation")
	public String name() {
		return name;
	}

	public int age() {
		return 0;
	}

	public void work() {
	}
	
	public Child(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Child[" + name+"] ";
	}
}
