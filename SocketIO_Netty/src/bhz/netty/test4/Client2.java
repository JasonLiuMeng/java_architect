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
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

public class Client2 {
	
	
	private static class SingletonHolder{
		static final Client2 instance = new Client2();
	}

	public static Client2 getInstance(){
		return SingletonHolder.instance;
	}
	
	private String ip;
	private int port;
	private EventLoopGroup workerGroup; 
	private Bootstrap bootstrap;
	private ChannelFuture channelFuture;
	
	public Client2(){
		
		workerGroup = new NioEventLoopGroup();
		try {
			bootstrap = new Bootstrap();
			
			bootstrap.group(workerGroup)
			.channel(NioSocketChannel.class)
			.handler(new LoggingHandler(LogLevel.INFO))
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel sChannel) throws Exception {
					// TODO Auto-generated method stub
					sChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingEncoder());
					sChannel.pipeline().addLast(MarshallingCodeFactory.buildMarshallingDecode());
					//超时设置
					sChannel.pipeline().addLast(new ReadTimeoutHandler(3));
					sChannel.pipeline().addLast(new ClientHandler());
				}
			})
			.option(ChannelOption.SO_KEEPALIVE, true);
			//可以多次调用绑定多个端口
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void connect(){
		try {
			this.channelFuture = bootstrap.connect("127.0.0.1", 8765).sync();
			System.out.println("远程服务器已经连接，可以进行数据交换！");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ChannelFuture getChannelFuture(){
		//获取连接，如果连接未初始化过，则初始化建立连接
		if( null == this.channelFuture ){
			this.connect();
		}
		//判断连接是否可用，如果不可用则重新连接
		if( !this.channelFuture.channel().isActive() ){
			this.connect();
		}
		return this.channelFuture;
	}
	
	public static void main(String[] args) {
		Client2 client2 = Client2.getInstance();
		ChannelFuture channelFuture = null;
		for(int i = 0; i < 4; i++){
			channelFuture  = client2.getChannelFuture();
			Request req = new Request();
			req.setId("id_" + i);
			req.setName("name_" + i);
			req.setRequestMessage("数据消息_" + i);
			channelFuture.channel().writeAndFlush(req);
			try {
				TimeUnit.SECONDS.sleep(6);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("断开连接，主线程结束..");
	}
	
}
