package com.test.multi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ListAdd2 {

	private List<String> list = new ArrayList<String>();
	
	public void add(){
		list.add("bjsxt");
	}
	
	public int size(){
		return list.size();
	}
	
	public static void main(String[] args) {
		final ListAdd2 list2 = new ListAdd2();
		
		final CountDownLatch countDownLatch = new CountDownLatch(2);
//		final Object lock = new Object();
		Thread t1 = new Thread(new Runnable() {
		
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				synchronized (lock) {
					for(int i = 0; i < 10; i++){
						list2.add();
						System.out.println("当前线程为:" + Thread.currentThread().getName() + ", 添加了一个元素。");
						if( list2.size() == 5 || list2.size() == 6 ){
//							lock.notify();
							countDownLatch.countDown();
							System.out.println("size等于5，发出通知.");
						}
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
//				}
			}
		},"t1");
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				synchronized (lock) {
					if( list2.size() != 5 ){
						try {
							countDownLatch.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						try {
//							lock.wait();
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
					}
					System.out.println("当前线程收到通知:" + Thread.currentThread().getName() + " list size=" + list2.size());
					throw new RuntimeException();
				}
//			}
		},"t2");
		
		t2.start();
		t1.start();
	}
}
