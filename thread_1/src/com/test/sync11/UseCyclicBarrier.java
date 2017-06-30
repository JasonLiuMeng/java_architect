package com.test.sync11;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UseCyclicBarrier {

	static class Runner implements Runnable{
		
		private CyclicBarrier barrier; 
		private String name;
		
		public Runner(CyclicBarrier barrier, String name) {
			// TODO Auto-generated constructor stub
			this.barrier = barrier;
			this.name = name;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(1000 * new Random().nextInt(5));
				System.out.println(this.name + "准备ok！");
				barrier.await();
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println(System.currentTimeMillis() + " - " + this.name + " Go!");
		}
	}
	
	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(3);
		ExecutorService pool = Executors.newFixedThreadPool(3);
		
		pool.submit(new Thread(new Runner(barrier, "zhangsan")));
		pool.submit(new Thread(new Runner(barrier, "lisi")));
		pool.submit(new Thread(new Runner(barrier, "wangwu")));
		
		pool.shutdown();
	}
	
}
