package com.test.sync12;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UseReentrantLock {

	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	public void method1(){
		try {
			lock.lock();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入method1...");
			Thread.sleep(1000);
			System.out.println("当前线程：" + Thread.currentThread().getName() + "释放锁.");
			condition.await(); //进入等待，类似于使用synchronized时的wait()
			System.out.println("当前线程：" + Thread.currentThread().getName() + "继续method1...");
			System.out.println("当前线程：" + Thread.currentThread().getName() + "退出method1...");
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
	}
	
	public void method2(){
		try {
			lock.lock();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入method2...");
			Thread.sleep(2000);
			System.out.println("当前线程：" + Thread.currentThread().getName() + "退出method2...");
			Thread.sleep(1000);
			condition.signal(); //发出通知，类似于使用synchronized时的notify()
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final UseReentrantLock ul = new UseReentrantLock();
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ul.method1();
			}
		}, "t1");
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ul.method2();
			}
		}, "t2");
		t1.start();
		t2.start();
		try {
			Thread.sleep(10);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
