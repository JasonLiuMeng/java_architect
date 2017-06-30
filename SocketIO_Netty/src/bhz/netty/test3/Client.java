package bhz.netty.test3;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Client {

	public static void main(String[] args) throws Exception {
		
		EventLoopGroup workgroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workgroup)
		.channel(NioSocketChannel.class)
		.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				//配置固定长度解析器
				sc.pipeline().addLast(new FixedLengthFrameDecoder(5));
				//设置字符串形式的解析器
				sc.pipeline().addLast(new StringDecoder());
				sc.pipeline().addLast(new StringEncoder());
				sc.pipeline().addLast(new ClientHandler());
			}
		});
		
		ChannelFuture cf1 = b.connect("127.0.0.1", 8765).sync();
		
		//上面设置了定长为5个字节，那么如果不足5个字节消息不会发送，如果超过五个直接，那么会将前面的5个字节当做一个消息发出，剩下的部分如果不足5个，那么则不会发送，如果够5个就当做一个新的消息发出
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("aaaaa".getBytes()));
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("bbbbb".getBytes())); 
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("ccccccc   ".getBytes()));
		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("ddd".getBytes()));
		
		cf1.channel().closeFuture().sync();
		workgroup.shutdownGracefully();
		
	}
}
