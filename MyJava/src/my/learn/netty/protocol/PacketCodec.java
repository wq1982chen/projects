package my.learn.netty.protocol;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.buffer.ByteBuf;

public class PacketCodec {

	private static final int MAGIC_NUMBER = 0x12345678;
	// 指令 与 数据包 映射
	private final Map<Byte, Class<? super Packet>> packetTypeMap;
	// 序列化算法 与 序列化类 映射
	private final Map<Byte, Class<? super Serializer>> serializerMap;
	// 单例
	public static final PacketCodec INSTANCE = new PacketCodec();
	// 注册Packet集合
	@SuppressWarnings("rawtypes")
	List<Class> packetList = Arrays.asList(new Class[] { LoginRequestPacket.class,
																											LoginResponsePacket.class,
																											MessageRequestPacket.class,
																											MessageResponsePacket.class});
	// 注册序列化算法集合
	@SuppressWarnings("rawtypes")
	List<Class> serializerList = Arrays.asList(new Class[] { JSONSerializer.class });
				
	 /**
	  * 初始化 指令 与 数据包 映射 序列化算法 与 序列化类 映射
	  */
	@SuppressWarnings("unchecked")
	 private PacketCodec() {
		  // 初始化 指令 与 数据包 映射
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
		// 初始化序列化算法 与 序列化类 映射
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
		// 2. 序列化java对象
		 byte[] bs = Serializer.DEFAULT.serialize(packet);
		// 3. 实际编码过程
		 byteBuf.writeInt(MAGIC_NUMBER); // 写入魔数
		 byteBuf.writeByte(packet.getVersion()); // 写入协议版本号
		 byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm()); // 写入序列化算法
		 byteBuf.writeByte(packet.getCommand()); // 写入指令
		 byteBuf.writeInt(bs.length); // 写入数据长度
		 byteBuf.writeBytes(bs); // 写入数据
		 return byteBuf;
	}
	
	/**
	 *  解码
	 * @param byteBuf
	 * @return
	 * @throws Exception
	 */
	 public Packet deCode(ByteBuf byteBuf) throws Exception {
		 
		 //System.out.println("============DECODE=============");
		 byteBuf.skipBytes(4);//跳过 魔数 校验
		 //System.out.printf("MAGIC_NUMBER:%d\n", byteBuf.readInt());
		 byteBuf.skipBytes(1);// 跳过版本号
		 //System.out.printf("VERSION:%s\n", byteBuf.readByte());
		 byte serializeAlgorithm = byteBuf.readByte();// 序列化算法标识
		 //System.out.printf("Algorithm:%s\n", serializeAlgorithm);
		 byte command = byteBuf.readByte(); // 指令标识
		 //System.out.printf("Command:%s\n", command);
		 int length = byteBuf.readInt();// 数据包长度
		 //System.out.printf("LENGTH:%d\n", length);
		 byte[] bs = new byte[length];// 数据
		 byteBuf.readBytes(bs);
		 // 通过序列化算法标识获取对应的 序列化对象
		 Serializer serializer = getSerializer(serializeAlgorithm);
		 // 通过指令标识 获取对应的 数据包类
		 Packet packet = getRequestPacket(command);
		 // 执行解码
		 if (serializer != null && packet != null) {
			 return serializer.deSerialize(packet.getClass(), bs);
		 } else {
			 System.out.println("没有找到对应的序列化对象或数据包对象");
			 return null;
		 }
	 }
	 
	/**
	 *  通过指令获取对应的 数据包类 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	 private Packet getRequestPacket(byte command) throws Exception {
		 return (Packet) packetTypeMap.get(command).newInstance();
	 }
	 /**
	  *  通过序列化算法标识 获取对应的序列化类
	  * @param serializeAlgorithm
	  * @return
	  * @throws Exception
	  */
	 private Serializer getSerializer(byte serializeAlgorithm) throws Exception {
		 return (Serializer) serializerMap.get(serializeAlgorithm).newInstance();
	 }
}
