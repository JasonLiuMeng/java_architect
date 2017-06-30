package bhz.netty.test4;

import java.io.File;
import java.io.FileOutputStream;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

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
			Request request = (Request)msg;
			
			byte[] attachment = request.getAttachment();
			if( null != attachment && attachment.length > 0 ){
				byte[] unGzipData = GzipUtils.unGzip(attachment);
				String outPath = System.getProperty("user.dir") + File.separatorChar + "receive" + File.separatorChar + "006_" + request.getId() + ".jpg";
				FileOutputStream out = new FileOutputStream(outPath);
				out.write(unGzipData);
				out.flush();
				out.close();
			}
			
			System.out.println("Server Handler received message : " + request.toString() );
			
			Response response = new Response();
			response.setId(request.getId());
			response.setName("response_name_" + request.getId());
			response.setResponseMessage("响应内容：" + request.getId());
			
			ctx.writeAndFlush(response);
			
//			writeFlush.addListener(ChannelFutureListener.CLOSE);

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
