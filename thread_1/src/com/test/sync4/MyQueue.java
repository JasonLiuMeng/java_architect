package com.test.sync4;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.print.DocFlavor.STRING;

public class MyQueue {

	//队列
	private final LinkedList<Object> list = new LinkedList<Object>(); 
	
	//计数器
	private final AtomicInteger count = new AtomicInteger();
	
	//最小长度
	private final int minSize = 0;
	
	//最大长度
	private final int maxSize;
	
	//同步锁
	private final Object lock = new Object();
	
	public MyQueue(int maxSize) {
		// TODO Auto-generated constructor stub
		this.maxSize = maxSize;
	}
	
	public void put(Object obj){
		synchronized (lock) {
			//如果达到最大值，那么就阻塞
			while(count.get() == this.maxSize){
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//添加元素
			list.push(obj);
			//计数器增加1
			count.incrementAndGet();
			System.out.println("加入新元素..." + obj);
			//通知另外一个线程（唤醒）
			lock.notify();
			
		}
	}
	
	public Object take(){
		Object ret = null;
		synchronized (lock) {
			if( list.size() == maxSize ){
				//如果达到最大值，那么就阻塞
				while(count.get() == this.minSize){
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//删除并返回第一个元素
				ret = list.removeFirst();
				//计数器减1
				count.decrementAndGet();
				//通知另外一个线程（唤醒）
				lock.notify();
			}
		}
		return ret;
	}
	
	public int getSize(){
		return this.count.get();
	}
	
	public static void main(String[] args) {
		final MyQueue mq = new MyQueue(5);
		mq.put("a");
		mq.put("b");
		mq.put("c");
		mq.put("d");
		mq.put("e");
		
		System.out.println("当前的线程长度:" + mq.getSize());
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mq.put("f");
				mq.put("g");
			}
		},"t1");
		t1.start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Object o1 = mq.take();
				System.out.println("移除的元素为：" + o1);
				Object o2 = mq.take();
				System.out.println("移除的元素为：" + o2);
			}
		});
		t2.start();
		
		CopyOnWriteArrayList<String> cow = new CopyOnWriteArrayList<String>(); 
		
		ConcurrentLinkedQueue<String> clq = new ConcurrentLinkedQueue<String>();
		LinkedBlockingQueue<String> lbq = new LinkedBlockingQueue<String>();
		
	}

}
