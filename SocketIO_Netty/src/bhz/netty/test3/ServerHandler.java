package bhz.netty.test3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GenericFutureListener;

public class ServerHandler  extends ChannelHandlerAdapter {

	/**
	 * @param ctx 连接上下文
	 * @msg 传输的消息对象
	 */
	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
			try {
				//do something msg
				String request = (String)msg;
				System.out.println("Server: " + request);
				//写给客户端
				String response = "ccccc";
				//调用write方法的时候，netty会自动释放msg，所以下面的ReferenceCountUtil.release可以省略
				ChannelFuture future = ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
				//添加监听，当数据回写完毕之后，调用该监听方法
				future.addListener(new ChannelFutureListener() {
					
					@Override
					public void operationComplete(ChannelFuture arg0) throws Exception {
						// TODO Auto-generated method stub
						System.out.println("Server消息会送完毕，回调该方法。");
						//关闭连接
//						ctx.close();
					}
				});
				//当数据回写完毕之后，关闭与客户端的连接
//				future.addListener(ChannelFutureListener.CLOSE);
			} catch (Exception e) {
				// TODO: handle exception
			}finally{
//				ReferenceCountUtil.release(msg);
			}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
