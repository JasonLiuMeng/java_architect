package com.test.sync3;

public class MultiVolatile extends Thread{

	private volatile boolean isRunning = true;
	
	private void setIsRunning(boolean bol){
		this.isRunning = bol;
	}
	
	@Override
	public void run() {
		System.out.println("进入run方法....");
		while( isRunning == true ){
			
		}
		System.out.println("程序结束");
	}
	
	
	public static void main(String[] args) {
		MultiVolatile multiVolatile = new MultiVolatile();
		multiVolatile.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		multiVolatile.setIsRunning(false);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(multiVolatile.isRunning);
	}
}
