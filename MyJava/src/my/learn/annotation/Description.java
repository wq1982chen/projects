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
	//���ע��ֻ��һ����Ա��
	//���Ա������ȡ��Ϊvalue()����ʹ��ʱ���Ժ��Գ�Ա���͸�ֵ�ţ�=��
	 String value();
}
