package my.learn.netty.protocol;

public interface Serializer {

	// 在接口中声明的变量"public  static final"可省略
	Serializer DEFAULT = new JSONSerializer();
	/**
	 * 序列化算法
	 * */
	 byte getSerializerAlgorithm();
	 /**
	 * java 对象转换成二进制 （序列化） 
	 * */
	 byte[] serialize(Object obj);
	 /**
	 * 二进制转换为java对象 （反序列化）
	 * */
	 <T> T deSerialize(Class<T> clazz , byte[] bytes);
	 /**
	 * 序列化算法标识集合接口
	 * */
	 interface SerializerAlgorithm{
		 public static final byte JSON = 1;
	 }
}
