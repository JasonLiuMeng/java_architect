package bhz.netty.test2;

import java.io.Console;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.PromiseAggregator;

public class ServerHandler extends ChannelHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Channel Active...");
		super.channelActive(ctx);
	}
	
	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Channel Read...");
		try{
			ByteBuf buf = (ByteBuf)msg;
			byte[] msgByte = new byte[buf.readableBytes()];
			buf.readBytes(msgByte);
			System.out.println("Server Handler received message : " + new String(msgByte , "utf-8"));
			
			ChannelFuture writeFlush = ctx.writeAndFlush(Unpooled.copiedBuffer(("hi, client.").getBytes()));
			//给writeFlush添加监听，当数据发送完毕之后，调用该方法
			writeFlush.addListener(new ChannelFutureListener() {
				
				@Override
				public void operationComplete(ChannelFuture arg0) throws Exception {
					// TODO Auto-generated method stub
					System.out.println(arg0);
					ctx.close();
				}
			});
			writeFlush.addListener(ChannelFutureListener.CLOSE);

		}finally{
			ReferenceCountUtil.release(msg);
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Channel Read Complete...");
		super.channelReadComplete(ctx);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Exception Caught...");
		super.exceptionCaught(ctx, cause);
	}
	
}
