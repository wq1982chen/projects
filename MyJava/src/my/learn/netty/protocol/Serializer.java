package my.learn.netty.protocol;

public interface Serializer {

	// �ڽӿ��������ı���"public  static final"��ʡ��
	Serializer DEFAULT = new JSONSerializer();
	/**
	 * ���л��㷨
	 * */
	 byte getSerializerAlgorithm();
	 /**
	 * java ����ת���ɶ����� �����л��� 
	 * */
	 byte[] serialize(Object obj);
	 /**
	 * ������ת��Ϊjava���� �������л���
	 * */
	 <T> T deSerialize(Class<T> clazz , byte[] bytes);
	 /**
	 * ���л��㷨��ʶ���Ͻӿ�
	 * */
	 interface SerializerAlgorithm{
		 public static final byte JSON = 1;
	 }
}
