package my.learn.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Documented 标记生成javadoc
 * @Inherited 标记继承关系
 * @Retention 注解的生存期
 * @Target 标注的目标
 * @author Administrator
 * @Target是这个注解的作用域，
 *  	ElementType.METHOD是这个注解的作用域的列表，
 *  	METHOD是方法声明，除此之外，还有：
 *  	CONSTRUCTOR（构造方法声明）,
 *  	FIELD（字段声明）,
 *  	LOCAL VARIABLE（局部变量声明）,
 *  	METHOD（方法声明）,
 *  	PACKAGE（包声明）,
 *  	PARAMETER（参数声明）,
 *  	TYPE（类接口）
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Person {

		String name();
		int age() default 10;
}
