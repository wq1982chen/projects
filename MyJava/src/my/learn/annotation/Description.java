package my.learn.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Description {
	//如果注解只有一个成员，
	//则成员名必须取名为value()，在使用时可以忽略成员名和赋值号（=）
	 String value();
}
