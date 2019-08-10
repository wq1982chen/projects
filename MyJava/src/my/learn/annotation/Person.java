package my.learn.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Documented �������javadoc
 * @Inherited ��Ǽ̳й�ϵ
 * @Retention ע���������
 * @Target ��ע��Ŀ��
 * @author Administrator
 * @Target�����ע���������
 *  	ElementType.METHOD�����ע�����������б�
 *  	METHOD�Ƿ�������������֮�⣬���У�
 *  	CONSTRUCTOR�����췽��������,
 *  	FIELD���ֶ�������,
 *  	LOCAL VARIABLE���ֲ�����������,
 *  	METHOD������������,
 *  	PACKAGE����������,
 *  	PARAMETER������������,
 *  	TYPE����ӿڣ�
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Person {

		String name();
		int age() default 10;
}
