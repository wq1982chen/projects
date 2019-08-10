package my.learn.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
// http://www.importnew.com/23564.html
//@Person(name="Kevin") // age is 10
@Person(age=30,name="Kevin")
public class TestAnnotation {

	public static void main(String[] args) throws ClassNotFoundException {
		
		// ---------------------- Example 1 ----------------------
		System.out.println( TestAnnotation.class.getName() );
		
		Person person = TestAnnotation.class.getAnnotation(Person.class);
		if( person != null ) {
			System.out.println("name:" + person.name() + " age:" + person.age());
		}else {
			System.out.println("Person unknown!");
		}
		// ---------------------- Example 2 ----------------------
		Class<?> c = Class.forName("my.learn.annotation.Child");
		boolean isExist = c.isAnnotationPresent(Description.class);
		if(isExist) {
			Description d = (Description)c.getAnnotation(Description.class);
			System.out.println(d.value());
		}
		Method[] methods = c.getMethods();
		for( Method method : methods ) {
			Annotation[] as = method.getAnnotations();
			for( Annotation a : as ) {
				  if( a instanceof  Description ) {
					  Description t = (Description)a;
					  System.out.println(t.value());
				  }
			}
		}
	}
}
