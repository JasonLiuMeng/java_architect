package com.test.sync9;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class UseExecutors {

	static class Temp extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println(System.currentTimeMillis());
		}
	}
	
	public static void main(String[] args) {
		
		ExecutorService fixedPool = Executors.newFixedThreadPool(10);
		
		ExecutorService cachedPool = Executors.newCachedThreadPool();
		
		ExecutorService singlePool = Executors.newSingleThreadExecutor();
		
		ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
		
		Temp command = new Temp();
		ScheduledFuture<?> scheduledFuture = scheduledPool.scheduleWithFixedDelay(command, 5, 3, TimeUnit.SECONDS);
		System.out.println(scheduledFuture);
	}
	
}
