package bhz.netty.test5;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;

public class SlaveClientHeartBeatHandler extends ChannelHandlerAdapter{

	private ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);
	
	private ScheduledFuture<?> heartBeat;
	
	private InetAddress address;
	
	private static final String SUCCESS_KEY = "auth_success_key";
	private static final String FAILURE_KEY = "auth_failure_key";
	
	/**
	 * 当连接建立激活时调用
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("auth start...");
		address = InetAddress.getLocalHost();
		String ip = address.getHostAddress();
		String key = "1234";
		//模拟证书与主机进行认证, 真实环境中此处应该进行加密处理
		ctx.writeAndFlush(new AuthRequest(ip + ":" + key));
	}
	
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		try{
			if((msg instanceof AuthResponse)){
				AuthResponse ret = (AuthResponse)msg;
				if( SUCCESS_KEY.equals(ret.getResult()) ){
					//定时任务，每隔5秒钟执行一次HeartBeatTask, 延迟0秒开始
					this.heartBeat = this.scheduled.scheduleWithFixedDelay(new HeartBeatTask(ctx), 0, 5, TimeUnit.SECONDS);
					System.out.println(ret.getResult());
				}else if(FAILURE_KEY.equals(ret.getResult())){
					System.out.println("权限认证失败。");
				}
			}else if((msg instanceof HeartBeatResponse)){
				HeartBeatResponse ret = (HeartBeatResponse)msg;
				System.out.println(ret.getMsg());
			}else if((msg instanceof Error)){
				Error ret = (Error)msg;
				System.out.println(ret.getError());
			}else{
				System.out.println(msg);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			ReferenceCountUtil.release(msg);
		}
		
	}
	
	
	public class HeartBeatTask implements Runnable{
		
		private final ChannelHandlerContext ctx;
		
		public HeartBeatTask(ChannelHandlerContext ctx) {
			// TODO Auto-generated constructor stub
			this.ctx = ctx;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				HeartBeatRequest requestInfo = new HeartBeatRequest();
				requestInfo.setIp(address.getHostAddress());
				Sigar sigar = new Sigar();
				
				CpuPerc cpuPerc = sigar.getCpuPerc();
				Map<String, Object> cpuMap = new HashMap<String, Object>();
				cpuMap.put("combined", cpuPerc.getCombined());
				cpuMap.put("user", cpuPerc.getUser());
				cpuMap.put("sys", cpuPerc.getSys());
				cpuMap.put("wait", cpuPerc.getWait());
				cpuMap.put("idle", cpuPerc.getIdle());
				requestInfo.setCupMap(cpuMap);
				
				Mem memPerc = sigar.getMem();
				Map<String, Object> memMap = new HashMap<String, Object>();
				memMap.put("total", memPerc.getTotal() / 1024L);
				memMap.put("used", memPerc.getUsed() / 1024L);
				memMap.put("free", memPerc.getFree() / 1024L);
				requestInfo.setMemoryMap(memMap);
				
				ctx.writeAndFlush(requestInfo);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
