package com.test.sync6;

/**
 * 主函数
 * @author jliu10
 */
public class Main {
	
	public static void main(String[] args) {
		//请求
		FutureClient client = new FutureClient();
		//future处理类返回虚拟结果
		Data data = client.request("请求数据...");
		System.out.println("请求发送成功...");
		
		System.out.println("处理其他逻辑...");
		try {
			Thread.sleep(2000);//模拟耗时2秒
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("使用请求的数据...");
		String realData = data.getRequest();
		System.out.println("数据结果是：" + realData);
	}
	
}
