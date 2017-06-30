package bhz.netty.test4;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends ChannelHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		try{
			Response response = (Response)msg;
			System.out.println("ClientHandler received message : " + response.toString() );
		}finally{
			
		}
	}
	
}
