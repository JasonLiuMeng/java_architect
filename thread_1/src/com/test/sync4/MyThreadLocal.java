package com.test.sync4;

public class MyThreadLocal {
	
	private static ThreadLocal<String> th = new ThreadLocal<String>();
	
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				MyThreadLocal.th.set("123");
				System.out.println(Thread.currentThread().getName() + " : " + MyThreadLocal.th.get());
			}
		},"t1");
		Thread t2 = new Thread(new Runnable() {
					
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println(Thread.currentThread().getName() + " : " + MyThreadLocal.th.get());
			}
		},"t2");

		t1.start();
		t2.start();
	}
}
