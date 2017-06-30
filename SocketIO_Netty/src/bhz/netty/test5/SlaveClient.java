package bhz.netty.test5;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SlaveClient {

	public void init(String ip, int port) {
		// TODO Auto-generated method stub
		EventLoopGroup wGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		try {
			bootstrap.group(wGroup)
			.channel(NioSocketChannel.class)
			.handler(new LoggingHandler(LogLevel.INFO))
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					// TODO Auto-generated method stub
					sc.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
					sc.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecode());
					sc.pipeline().addLast(new SlaveClientHeartBeatHandler());
				}
			});
			
			ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
			System.out.println("Slave Client started. IP : " + ip + ":" + port);
			channelFuture.channel().closeFuture().sync();
			wGroup.shutdownGracefully();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		new SlaveClient().init("172.29.132.218", 8765);
	}
	
}
