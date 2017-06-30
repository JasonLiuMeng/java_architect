package bhz.netty.test2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

	private String ip;
	private int port;
	
	public Client(String ip, int port) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.port = port;
	}
	
	public void run(){
		//客户端用来连接服务端的连接组
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			
			bootstrap.group(workerGroup)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel sChannel) throws Exception {
					// TODO Auto-generated method stub
					sChannel.pipeline().addLast(new ClientHandler());
				}
			})
			.option(ChannelOption.SO_KEEPALIVE, true);
			//可以多次调用绑定多个端口
			ChannelFuture future = bootstrap.connect(this.ip, this.port).sync();
//			ChannelFuture future2 = bootstrap.connect(this.ip, 8764).sync();
			
			future.channel().writeAndFlush(Unpooled.copiedBuffer(("Hello Server.").getBytes()));
			future.channel().writeAndFlush(Unpooled.copiedBuffer(("Hello Server.").getBytes()));
			future.channel().writeAndFlush(Unpooled.copiedBuffer(("Hello Server.").getBytes()));
//			future2.channel().writeAndFlush(Unpooled.copiedBuffer(("Hello2 Server2.").getBytes()));
			
			future.channel().closeFuture().sync();
//			future2.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			workerGroup.shutdownGracefully();
		}
		
	}
	
	public static void main(String[] args) {
		Client client = new Client("127.0.0.1", 8765);
		client.run();
	}
	
}
