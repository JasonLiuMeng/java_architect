package bhz.netty.test4;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.File;
import java.io.FileInputStream;

public class Client {

	private String ip;
	private int port;
	
	public Client(String ip, int port) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.port = port;
	}
	
	public void run() throws Exception{
		//客户端用来连接服务端的连接组
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			
			bootstrap.group(workerGroup)
			.channel(NioSocketChannel.class)
			.handler(new LoggingHandler(LogLevel.INFO))
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel sChannel) throws Exception {
					// TODO Auto-generated method stub
					sChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
					sChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecode());
					sChannel.pipeline().addLast(new ClientHandler());
				}
			})
			.option(ChannelOption.SO_KEEPALIVE, true);
			//可以多次调用绑定多个端口
			ChannelFuture channelFuture = bootstrap.connect(this.ip, this.port).sync();

			for(int i = 0; i < 2; i++){
				Request req = new Request();
				req.setId("id_" + i);
				req.setName("name_" + i);
				req.setRequestMessage("数据消息_" + i);
				
				String path = System.getProperty("user.dir") + File.separatorChar + "source" + File.separatorChar + "006.jpg";
				File file = new File(path);
				if( file.exists() ){
					FileInputStream in = new FileInputStream(file);
					byte[] fileData = new byte[in.available()];
					in.read(fileData, 0, fileData.length);
					req.setAttachment(GzipUtils.gzip(fileData));
				}
				
				channelFuture.channel().writeAndFlush(req);
			}
			channelFuture.channel().closeFuture().sync();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			workerGroup.shutdownGracefully();
		}
		
	}
	
	public static void main(String[] args) {
		Client client = new Client("127.0.0.1", 8765);
		try {
			client.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
