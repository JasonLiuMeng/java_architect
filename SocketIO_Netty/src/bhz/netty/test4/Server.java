package bhz.netty.test4;

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
import io.netty.handler.timeout.ReadTimeoutHandler;

public class Server {
	
	private int port;
	
	public Server(int port) {
		// TODO Auto-generated constructor stub
		this.port = port;
	}
	
	public void run(){
		//用来接收连接事件组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//用来处理接收到的连接的事件处理组
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			//server配置辅助类
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			//将连接接收组与事件处理组连接，当server的bossGroup接收到连接之后就会交给workerGroup进行处理
			serverBootstrap.group(bossGroup, workerGroup)
			//指定接收的Channel类型
			.channel(NioServerSocketChannel.class)
			//handler在初始化时就会执行，而childHandler会在客户端成功connect后才执行，这是两者的区别。
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel channel) throws Exception {
					// TODO Auto-generated method stub
					channel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
					channel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecode());
					channel.pipeline().addLast(new ReadTimeoutHandler(3));
					channel.pipeline().addLast(new ServerHandler());
				}
			})
			//设置TCP缓冲区的大小
			.option(ChannelOption.SO_BACKLOG, 128)
			//设置是否保持连接
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			//注意，此处option()是提供给NioServerSocketChannel用来接收进来的连接,也就是boss线程。
			//childOption()是提供给由父管道ServerChannel接收到的连接，也就是worker线程,在这个例子中也是NioServerSocketChannel。
				

			//异步绑定端口，可以多次调用绑定多个端口
			ChannelFuture channelFuture = serverBootstrap.bind(this.port).sync();
//			ChannelFuture channelFuture2 = serverBootstrap.bind(8764).sync();
			System.out.println("Server服务已经启动.");
			
			//异步检查管道是否关闭
			channelFuture.channel().closeFuture().sync();
//			channelFuture2.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server(8765);
		server.run();
	}
	
}
