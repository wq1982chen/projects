package my.learn.netty.protocol;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.buffer.ByteBuf;

public class PacketCodec {

	private static final int MAGIC_NUMBER = 0x12345678;
	// ָ�� �� ���ݰ� ӳ��
	private final Map<Byte, Class<? super Packet>> packetTypeMap;
	// ���л��㷨 �� ���л��� ӳ��
	private final Map<Byte, Class<? super Serializer>> serializerMap;
	// ����
	public static final PacketCodec INSTANCE = new PacketCodec();
	// ע��Packet����
	@SuppressWarnings("rawtypes")
	List<Class> packetList = Arrays.asList(new Class[] { LoginRequestPacket.class,
																											LoginResponsePacket.class,
																											MessageRequestPacket.class,
																											MessageResponsePacket.class});
	// ע�����л��㷨����
	@SuppressWarnings("rawtypes")
	List<Class> serializerList = Arrays.asList(new Class[] { JSONSerializer.class });
				
	 /**
	  * ��ʼ�� ָ�� �� ���ݰ� ӳ�� ���л��㷨 �� ���л��� ӳ��
	  */
	@SuppressWarnings("unchecked")
	 private PacketCodec() {
		  // ��ʼ�� ָ�� �� ���ݰ� ӳ��
		  packetTypeMap = new HashMap<Byte, Class<? super Packet>>();
		  packetList.forEach(clazz -> {
			  try {
					  Method method = clazz.getMethod("getCommand");
					  Byte command = (Byte) method.invoke(clazz.newInstance());
					  packetTypeMap.put(command, clazz);
			 } catch (Exception e) {
					  e.printStackTrace();
			}
		});
		// ��ʼ�����л��㷨 �� ���л��� ӳ��
		  serializerMap = new HashMap<Byte, Class<? super Serializer>>();
		  serializerList.forEach(clazz -> {
			  try {
				  Method method = clazz.getMethod("getSerializerAlgorithm");
				  //Byte serializerAlgorithm = (Byte) method.invoke(clazz);
				  byte serializerAlgorithm = (byte) method.invoke((JSONSerializer)clazz.newInstance());
				  serializerMap.put(serializerAlgorithm, clazz);
			  } catch (Exception e) {
				  e.printStackTrace();
			  }
		  });
	  }
	
	public ByteBuf enCode(ByteBuf byteBuf,Packet packet) {
		
		//ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
		// 2. ���л�java����
		 byte[] bs = Serializer.DEFAULT.serialize(packet);
		// 3. ʵ�ʱ������
		 byteBuf.writeInt(MAGIC_NUMBER); // д��ħ��
		 byteBuf.writeByte(packet.getVersion()); // д��Э��汾��
		 byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm()); // д�����л��㷨
		 byteBuf.writeByte(packet.getCommand()); // д��ָ��
		 byteBuf.writeInt(bs.length); // д�����ݳ���
		 byteBuf.writeBytes(bs); // д������
		 return byteBuf;
	}
	
	/**
	 *  ����
	 * @param byteBuf
	 * @return
	 * @throws Exception
	 */
	 public Packet deCode(ByteBuf byteBuf) throws Exception {
		 
		 //System.out.println("============DECODE=============");
		 byteBuf.skipBytes(4);//���� ħ�� У��
		 //System.out.printf("MAGIC_NUMBER:%d\n", byteBuf.readInt());
		 byteBuf.skipBytes(1);// �����汾��
		 //System.out.printf("VERSION:%s\n", byteBuf.readByte());
		 byte serializeAlgorithm = byteBuf.readByte();// ���л��㷨��ʶ
		 //System.out.printf("Algorithm:%s\n", serializeAlgorithm);
		 byte command = byteBuf.readByte(); // ָ���ʶ
		 //System.out.printf("Command:%s\n", command);
		 int length = byteBuf.readInt();// ���ݰ�����
		 //System.out.printf("LENGTH:%d\n", length);
		 byte[] bs = new byte[length];// ����
		 byteBuf.readBytes(bs);
		 // ͨ�����л��㷨��ʶ��ȡ��Ӧ�� ���л�����
		 Serializer serializer = getSerializer(serializeAlgorithm);
		 // ͨ��ָ���ʶ ��ȡ��Ӧ�� ���ݰ���
		 Packet packet = getRequestPacket(command);
		 // ִ�н���
		 if (serializer != null && packet != null) {
			 return serializer.deSerialize(packet.getClass(), bs);
		 } else {
			 System.out.println("û���ҵ���Ӧ�����л���������ݰ�����");
			 return null;
		 }
	 }
	 
	/**
	 *  ͨ��ָ���ȡ��Ӧ�� ���ݰ��� 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	 private Packet getRequestPacket(byte command) throws Exception {
		 return (Packet) packetTypeMap.get(command).newInstance();
	 }
	 /**
	  *  ͨ�����л��㷨��ʶ ��ȡ��Ӧ�����л���
	  * @param serializeAlgorithm
	  * @return
	  * @throws Exception
	  */
	 private Serializer getSerializer(byte serializeAlgorithm) throws Exception {
		 return (Serializer) serializerMap.get(serializeAlgorithm).newInstance();
	 }
}
