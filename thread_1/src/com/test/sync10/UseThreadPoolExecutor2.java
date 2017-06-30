package com.test.sync10;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseThreadPoolExecutor2 {

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Runnable> queue =
//				new LinkedBlockingQueue<Runnable>();//使用无界队列
				new ArrayBlockingQueue<Runnable>(3);//使用有界队列
		ExecutorService pool = new ThreadPoolExecutor(
				1, 
				2, 
				120L, 
				TimeUnit.SECONDS, 
				queue,
//				new ThreadPoolExecutor.DiscardOldestPolicy());//jdk自带拒绝策略，丢弃最老的一个请求
				new MyRejected());//自定义拒绝策略
		
		for(int i = 1; i <= 20; i++){
			MyTask task = new MyTask(i, "任务" + i);
			pool.execute(task);
		}
		
		Thread.sleep(1000);
		System.out.println("queue size : " + queue.size());
		Thread.sleep(2000);
		
	}
	
}
