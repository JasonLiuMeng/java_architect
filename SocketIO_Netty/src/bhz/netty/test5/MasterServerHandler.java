package bhz.netty.test5;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

public class MasterServerHandler extends ChannelHandlerAdapter{

	
	private static final String SUCCESS_KEY = "auth_success_key";
	private static final String FAILURE_KEY = "auth_failure_key";
	
	//存放从机信息的列表map
	private static Map<String, String> AUTH_IP_MAP = new HashMap<String, String>();
	
	//模拟从数据库读取数据，存入map,真实环境中应该是从数据库或缓存中读取
	static{
		AUTH_IP_MAP.put("172.29.132.218", "1234");
	}
	
	/**
	 * 丛机slave的权限校验。此处只是简单的验证处理，真实环境中应该根据实际情况去进行加解密处理或其他更安全的方式进行验证
	 * @param ctx
	 * @param msg
	 */
	private boolean auth(ChannelHandlerContext ctx, Object msg){
		String[] ret = ((AuthRequest)msg).getAuth().split(":");
		String auth = AUTH_IP_MAP.get(ret[0]);
		if( null != auth && auth.equals(ret[1]) ){
			ctx.writeAndFlush(new AuthResponse(SUCCESS_KEY));
			System.out.println("权限校验通过");
			return true;
		}else{
			System.out.println("权限校验失败！");
			//权限校验失败应该主动断开该次连接
			ctx.writeAndFlush(new AuthResponse(FAILURE_KEY)).addListener(ChannelFutureListener.CLOSE);
			return false;
		}
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("master read...");
		System.out.println( msg instanceof AuthRequest);
		if( ( msg instanceof AuthRequest ) ){
			this.auth(ctx, msg);
		}else if( ( msg instanceof HeartBeatRequest ) ){
			HeartBeatRequest req = (HeartBeatRequest)msg;
			System.out.println("--------------------------------------------");
		    System.out.println("当前主机ip为: " + req.getIp());
		    System.out.println("当前主机cpu情况: ");
		    Map cpu = req.getCupMap();
		    System.out.println("总使用率: " + cpu.get("combined"));
		    System.out.println("用户使用率: " + cpu.get("user"));
		    System.out.println("系统使用率: " + cpu.get("sys"));
		    System.out.println("等待率: " + cpu.get("wait"));
		    System.out.println("空闲率: " + cpu.get("idle"));
	
		    System.out.println("当前主机memory情况: ");
		    Map memory = req.getMemoryMap();
		    System.out.println("内存总量: " + memory.get("total"));
		    System.out.println("当前内存使用量: " + memory.get("used"));
		    System.out.println("当前内存剩余量: " + memory.get("free"));
		    System.out.println("--------------------------------------------");

			ctx.writeAndFlush(new HeartBeatResponse("info received!"));
		}else {
			ctx.writeAndFlush(new Error("connect failure!")).addListener(ChannelFutureListener.CLOSE);
		}
	}
	
}
