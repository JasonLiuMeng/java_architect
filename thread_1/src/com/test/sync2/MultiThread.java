package com.test.sync2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThread {

	Lock lock1 = new ReentrantLock();
	
	public synchronized void method1(){
		System.out.println(Thread.currentThread().getName() + " - method1 start..." );
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " - method1 end." );
	}
	
	public void method2(){
		synchronized(this){
			System.out.println(Thread.currentThread().getName() + " - method2 start..." );
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " - method2 end." );
		}
	}
	
	public void method3(){
		synchronized(MultiThread.class){
			System.out.println(Thread.currentThread().getName() + " - method3 start..." );
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " - method3 end." );
		}
	}
	
	private Object lock = new Object();
	public void method4(){
		synchronized(lock){
			System.out.println(Thread.currentThread().getName() + " - method4 start..." );
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " - method4 end." );
		}
	}
	
	public void method5(){
		lock1.lock();
		System.out.println(Thread.currentThread().getName() + " - method5 start..." );
		try {
			Thread.sleep(9000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " - method5 end." );
		lock1.unlock();
	}
	
	public void method6(){
		lock1.lock();
		System.out.println(Thread.currentThread().getName() + " - method6 start..." );
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " - method6 end." );
		lock1.unlock();
	}
	
	public static void main(String[] args) {
		final MultiThread multiThread = new MultiThread();
		final MultiThread multiThread2 = new MultiThread();
		
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				multiThread.method1();
			}
		},"t1");
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				multiThread.method2();
			}
		},"t2");
		Thread t3 = new Thread(new Runnable() {
			public void run() {
				multiThread.method3();
			}
		},"t3");
		Thread t4 = new Thread(new Runnable() {
			public void run() {
				multiThread.method4();
			}
		},"t4");
		Thread t5 = new Thread(new Runnable() {
			public void run() {
				multiThread2.method3();
			}
		},"t5");
		Thread t6 = new Thread(new Runnable() {
			public void run() {
				multiThread2.method5();
			}
		},"t6");
		Thread t7 = new Thread(new Runnable() {
			public void run() {
				multiThread2.method6();
			}
		},"t7");
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
	}
}
