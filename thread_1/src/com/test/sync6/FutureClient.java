package com.test.sync6;


/**
 * 核心处理类，对请求启用独立线程
 * @author jliu10
 *
 */
public class FutureClient{

	public Data request(final String condition){
		//创建一个数据包装对象
		final FutureData futureData = new FutureData();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//业务处理，请求数据
				RealData realData = new RealData();
				realData.request(condition);
				//将真实的结果数据设置到Future数据包装对象中
				futureData.setRealData(realData);
			}
		}).start();
		//返回数据包装对象，此时返回的包装对象是虚拟的，因为在线程没有执行完成之前，即futureData.setRealData(realData);调用之前，该对象是没有真实数据的
		return futureData;
	}
}
