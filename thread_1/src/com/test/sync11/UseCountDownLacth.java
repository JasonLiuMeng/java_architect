package com.test.sync11;

import java.util.concurrent.CountDownLatch;

public class UseCountDownLacth {

	public static void main(String[] args) {
		final CountDownLatch count = new CountDownLatch(2);
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					System.out.println("进入线程t1，" + "等待其他线程处理完成...");
					count.await();
					System.out.println("t1线程继续执行...");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					System.out.println("进入线程t2，" + "执行t2线程的初始化操作...");
					Thread.sleep(3 * 1000);
					System.out.println("t2线程初始化完毕，通知t1线程...");
					count.countDown();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		Thread t3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					System.out.println("进入线程t3，" + "执行t3线程的初始化操作...");
					Thread.sleep(4 * 1000);
					System.out.println("t3线程初始化完毕，通知t1线程...");
					count.countDown();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		t1.start();
		t2.start();
		t3.start();
	}
	
}
