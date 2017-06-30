package com.test.sync6;

/**
 * 真实数据结果对象
 * @author jliu10
 */
public class RealData implements Data {
	
	private String result = null;
	
	/**
	 * 请求数据，真实场景中可能需要请求数据等操作，此处模拟耗时5s
	 * @param condition
	 */
	public void request(String condition){
		System.out.println("数据库获取数据，数据条件是：" + condition);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = "我是返回结果数据.";
		System.out.println("获取数据成功.");
	}
	
	@Override
	public String getRequest() {
		// TODO Auto-generated method stub
		return this.result;
	}

}
