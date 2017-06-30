package com.test.sync12;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UseManyCondition {
	
	private ReentrantLock lock = new ReentrantLock();
	private Condition c1 = lock.newCondition();
	private Condition c2 = lock.newCondition();
	
	public void m1(){
		try {
			lock.lock();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入m1等待...");
			System.out.println("当前线程：" + Thread.currentThread().getName() + "getHoldCount " + lock.getHoldCount());
			System.out.println("当前线程：" + Thread.currentThread().getName() + "isLocked " + lock.isLocked());
			System.out.println("当前线程：" + Thread.currentThread().getName() + "getWaitQueueLength " + lock.getWaitQueueLength(c1));
			c1.await();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "getHoldCount " + lock.getHoldCount());
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入m1继续...");
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
	}
	
	public void m2(){
		try {
			System.out.println("当前线程：" + Thread.currentThread().getName() + "isLocked " + lock.isLocked());
			System.out.println("当前线程：" + Thread.currentThread().getName() + "tryLock " + lock.tryLock());
			System.out.println("当前线程：" + Thread.currentThread().getName() + "getHoldCount " + lock.getHoldCount());
			lock.lock();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "getWaitQueueLength " + lock.getWaitQueueLength(c1));
			System.out.println("当前线程：" + Thread.currentThread().getName() + "getHoldCount " + lock.getHoldCount());
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入m2等待...");
			c1.await();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入m2继续...");
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
	}
	
	public void m3(){
		try {
			lock.lock();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入m3等待...");
			c2.await();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入m3继续...");
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
	}
	
	public void m4(){
		try {
			lock.lock();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入m4，唤醒c1...");
			System.out.println("当前线程：" + Thread.currentThread().getName() + "getWaitQueueLength " + lock.getWaitQueueLength(c1));
			System.out.println("当前线程：" + Thread.currentThread().getName() + "hasWaiters  " + lock.hasWaiters(c1));
			c1.signalAll();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
	}
	
	public void m5(){
		try {
			lock.lock();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入m5，唤醒c2...");
			c2.signal();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
	}
	
	public static void main(String[] args) throws Exception {
		final UseManyCondition umc = new UseManyCondition();
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				umc.m1();
			}
		}, "t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				umc.m2();
			}
		}, "t2");
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				umc.m3();
			}
		}, "t3");
		t1.start();
		t2.start();
		t3.start();
		Thread.sleep(2000);
		Thread t4 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				umc.m4();
			}
		}, "t4");
		t4.start();
		Thread.sleep(2000);
		Thread t5 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				umc.m5();
			}
		}, "t5");
		t5.start();
	}
}
