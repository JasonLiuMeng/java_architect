package com.test.sync11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class UseSemaphore {

	public static void main(String[] args) {
		final Semaphore semp = new Semaphore(5);
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < 20; i++){
			final int NO = i;
			Runnable run = new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						semp.acquire();//获取许可，如果能够获取则继续执行，否则阻塞等待
						System.out.println("Accessing: " + NO);
						Thread.sleep(5 * 1000);
						semp.release();//释放许可
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			};
			exec.execute(run);
		}
		exec.shutdown();
	}
	
}
