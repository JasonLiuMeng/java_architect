package bhz.netty.test;

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
				ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
				//配置分割符解析器，并设置最大帧的长度与自定义分割符
				sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
				//设置字符串形式的解析器
				sc.pipeline().addLast(new StringDecoder());
				//设置字符串形式的编码器
				sc.pipeline().addLast(new StringEncoder());
				sc.pipeline().addLast(new ClientHandler());
			}
		});
		
		ChannelFuture cf1 = b.connect("127.0.0.1", 8765).sync();
		
		//buf
//		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("我是消息1;$_".getBytes()));
//		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("我是消息2;$_".getBytes()));
//		cf1.channel().writeAndFlush(Unpooled.copiedBuffer("我是消息3;$_".getBytes()));
		cf1.channel().writeAndFlush("我是消息1;$_");
		cf1.channel().writeAndFlush("我是消息2;$_");
		cf1.channel().writeAndFlush("我是消息3;$_");
		
		cf1.channel().closeFuture().sync();
		workgroup.shutdownGracefully();
		
	}
}
