package com.test.sync8;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者
 * @author jliu10
 */
public class Provider implements Runnable{

	//公共缓存区
	private BlockingQueue<Data> queue;
	
	//是否继续运行
	private volatile boolean isRunning = true;
	
	//静态变量，用来模拟数据
	private static AtomicInteger count = new AtomicInteger(0);
	
	private static Random r = new Random();
	
	public Provider(BlockingQueue<Data> queue) {
		// TODO Auto-generated constructor stub
		this.queue = queue;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRunning){
			try {
				//模拟生产数据，可以是从数据库获取
				Thread.sleep(r.nextInt(1000));
				int id = count.incrementAndGet();
				Data data = new Data(Integer.toString(id), "数据-" + id);
				System.out.println("当前线程：" + Thread.currentThread().getName() + " ,获取了数据，id为：" + id + ", 进行装载数据到缓冲区...");
				//将数据插入到公共缓冲区，2s超时
				if(!this.queue.offer(data, 2, TimeUnit.SECONDS)){
					System.out.println("提交数据到缓冲区失败...");
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		this.isRunning = false;
	}
}
