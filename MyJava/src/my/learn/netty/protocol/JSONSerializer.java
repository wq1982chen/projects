package my.learn.netty.protocol;

import com.alibaba.fastjson.JSONObject;

public class JSONSerializer implements Serializer {

	@Override
	public byte getSerializerAlgorithm() {
		return SerializerAlgorithm.JSON;
	}

	@Override
	public byte[] serialize(Object obj) {
		return JSONObject.toJSONBytes(obj);
	}

	@Override
	public <T> T deSerialize(Class<T> clazz, byte[] bytes) {
		return JSONObject.parseObject(bytes, clazz);
	}

}
