package bhz.netty.test5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MasterServer {

	public void init(int port){
		//用于接收网络连接
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//用于处理实际工作
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		try {
			serverBootstrap.group(bossGroup, workGroup);
			serverBootstrap.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					// TODO Auto-generated method stub
					sc.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
					sc.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecode());
					sc.pipeline().addLast(new MasterServerHandler());
				}
			});
			ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
			System.out.println("Master Server started. port : " + port);
			
			channelFuture.channel().closeFuture().sync();
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MasterServer().init(8765);
	}
	
}
