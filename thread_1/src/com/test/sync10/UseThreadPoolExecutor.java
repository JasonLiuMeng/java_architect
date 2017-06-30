package com.test.sync10;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseThreadPoolExecutor {

	public static void main(String[] args) {
		// TODO Auto-generated constructor stub
		
				ThreadPoolExecutor pool = new ThreadPoolExecutor(
						1, 
						2, 
						60, 
						TimeUnit.SECONDS, 
						new ArrayBlockingQueue<Runnable>(3), //使用有界队列
//						new ThreadPoolExecutor.CallerRunsPolicy());
						new MyRejected());//自定义拒绝策略
			
				MyTask t1 = new MyTask(1, "任务1");
				MyTask t2 = new MyTask(2, "任务2");
				MyTask t3 = new MyTask(3, "任务3");
				MyTask t4 = new MyTask(4, "任务4");
				MyTask t5 = new MyTask(5, "任务5");
				MyTask t6 = new MyTask(6, "任务6");
				
				pool.execute(t1);
				pool.execute(t2);
				pool.execute(t3);
				pool.execute(t4);
				pool.execute(t5);
				pool.execute(t6);
				
				pool.shutdown();
				System.out.println("主函数运行完毕...");
//				List<Runnable> list = pool.shutdownNow();
//				System.out.println(list);
				
				Executors.newFixedThreadPool(10);
				Executors.newCachedThreadPool();
				Executors.newSingleThreadExecutor();
				Executors.newScheduledThreadPool(10);
	}
	
}
