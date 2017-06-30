package com.test.sync8;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 主测试函数
 * @author jliu10
 */
public class Main {

	public static void main(String[] args) {
		//内存缓冲区
		BlockingQueue<Data> blockingQueue = new LinkedBlockingQueue<Data>(10);
			
		//生产者
		Provider p1 = new Provider(blockingQueue);
		Provider p2 = new Provider(blockingQueue);
		Provider p3 = new Provider(blockingQueue);
		
		//消费者
		Consumer c1 = new Consumer(blockingQueue);
		Consumer c2 = new Consumer(blockingQueue);
		Consumer c3 = new Consumer(blockingQueue);
		
		//创建线程池运行
		ExecutorService cachPool = Executors.newCachedThreadPool();
		
		cachPool.execute(p1);
		cachPool.execute(p2);
		cachPool.execute(p3);
		
		cachPool.execute(c1);
		cachPool.execute(c2);
		cachPool.execute(c3);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		p1.stop();
		p2.stop();
		p3.stop();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
