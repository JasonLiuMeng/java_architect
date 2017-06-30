package com.test.sync7;

public class MyWorker extends Worker {

	/**
	 * 处理业务逻辑的方法
	 */
	@Override
	public Object handle(Job job) {
		Object output = null;
		// TODO Auto-generated method stub
		try {
			//表示处理job任务，可能是数据的加工，也可能是操作数据库，此处用休眠做模拟，处理结果为output
			Thread.sleep(500);
			output = job.getPrice();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
}
