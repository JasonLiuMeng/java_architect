package bhz.netty.test5;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

public class MarshallingCodeFactory {

	public static MarshallingDecoder buildMarshallingDecode(){
		//首先通过Marshlling工具类的方法获取Marshalling实例对象，参数serial标识创建的是java序列化工厂对象
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		//创建MarshallingConfiguration对象
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		//设置Marshalling的版本号
		configuration.setVersion(5);
		//根据MarshallerFactory和configuration创建provider
		UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
		//构建Netty的MarshallingDecoder对象，两个参数分别为provider何单个消息序列化后的最大长度
		MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024 * 1024);
		return decoder;
	}
	
	
	public static MarshallingEncoder buildMarshallingEncoder(){
		//首先通过Marshlling工具类的方法获取Marshalling实例对象，参数serial标识创建的是java序列化工厂对象
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		//创建MarshallingConfiguration对象
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		//设置Marshalling的版本号
		configuration.setVersion(5);
		//根据MarshallerFactory和configuration创建provider
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
		//构建Netty的MarshallingDecoder对象，两个参数分别为provider何单个消息序列化后的最大长度
		MarshallingEncoder encoder = new MarshallingEncoder(provider);
		return encoder;
	}
}
