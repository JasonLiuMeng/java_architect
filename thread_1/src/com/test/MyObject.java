package com.test;

public class MyObject {

	public synchronized void method1() {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName());
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("method1 over.");
	}
	
	public void method2() {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName());
		System.out.println("method2 over.");
	}
	
	public static void main(String[] args) {
		final MyObject mObject = new MyObject();
		
		Thread t1 = new Thread("t1"){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mObject.method1();
			}
		};
		
		Thread t2 = new Thread("t2"){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mObject.method2();
			}
		};
		
		t1.start();
		t2.start();
	}
}
