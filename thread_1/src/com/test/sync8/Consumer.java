package com.test.sync8;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * 消费者
 * @author jliu10
 */
public class Consumer implements Runnable{

	//公共缓存区
	private BlockingQueue<Data> queue;
	
	private static Random r = new Random();
	
	public Consumer(BlockingQueue<Data> queue) {
		// TODO Auto-generated constructor stub
		this.queue = queue;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				//从公共缓冲区获取数据进行消费
				Data data = this.queue.take();
				//模拟消费数据
				Thread.sleep(r.nextInt(1000));
				System.out.println("当前消费线程：" + Thread.currentThread().getName() + ", 消费成功，消费数据id:" + data.getId());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
