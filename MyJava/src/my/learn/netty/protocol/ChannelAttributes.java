package my.learn.netty.protocol;

import io.netty.util.AttributeKey;

public interface ChannelAttributes {

	AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
