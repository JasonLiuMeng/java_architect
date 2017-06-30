package com.test.sync6;

/**
 * Future数据包装对象，利用notify和wait来阻塞请求，以确保最终能够获取到数据
 * @author jliu10
 */
public class FutureData implements Data{
	
	private RealData realData;//真实数据
	private boolean isReady = false;//是否已经加载成功
	
	/**
	 * 设置真实数据
	 * @param realData
	 */
	public synchronized void setRealData(RealData realData){
		if( isReady ){ //如果已经设置结果了，直接返回
			return;
		}
		//设置结果对象
		this.realData = realData;
		isReady = true;
		//唤醒获取数据的线程
		System.out.println("数据准备完成，唤醒等待数据的线程...");
		notify();
	}
	
	@Override
	public synchronized String getRequest() {
		// TODO Auto-generated method stub
		while(!isReady){//如果还没有设置结果，进入等待，等待结果设置成功
			try {
				System.out.println("数据还没有准备好，等待数据...");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.realData.getRequest();
	}
}
