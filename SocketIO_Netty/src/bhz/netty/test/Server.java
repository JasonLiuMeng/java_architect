package bhz.netty.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {

	public static void main(String[] args) throws Exception {
		//1 第一个线程组 是用于接收Client端连接的
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//2 第二个线程组 是用于实际的业务处理操作的
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		//3 创建一个辅助类Bootstrap，就是对我们的Server进行一系列的配置
		ServerBootstrap b = new ServerBootstrap(); 
		//把俩个工作线程组加入进来
		b.group(bossGroup, workerGroup)
		//我要指定使用NioServerSocketChannel这种类型的通道
		 .channel(NioServerSocketChannel.class)
		//一定要使用 childHandler 去绑定具体的 事件处理器
		 .childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				//设置自定义分隔符
				ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
				//配置分割符解析器，并设置最大帧的长度与自定义分割符
				sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
				//设置字符串形式的解析器
				sc.pipeline().addLast(new StringDecoder());
				//设置字符串形式的编码器
				sc.pipeline().addLast(new StringEncoder());
				sc.pipeline().addLast(new ServerHandler());
			}
		})
		//设置TCP缓冲区
		.option(ChannelOption.SO_BACKLOG, 128)
		//设置发送缓冲大小
		.option(ChannelOption.SO_SNDBUF, 32*1024)
		//设置接收缓冲大小
		.option(ChannelOption.SO_RCVBUF, 32*1024)
		//保持连接
		.option(ChannelOption.SO_KEEPALIVE, true);

		//绑定指定的端口 进行监听
		ChannelFuture f = b.bind(8765).sync(); 
		
		//Thread.sleep(1000000);
		//异步监听管道关闭
		f.channel().closeFuture().sync();
		
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		 
		
		
	}
	
}
