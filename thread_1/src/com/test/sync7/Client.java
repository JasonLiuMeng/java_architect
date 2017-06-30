package com.test.sync7;

import java.util.Random;

public class Client {
	
	public static void main(String[] args) {
		System.out.println("我的机器当前可用的线程数：" + Runtime.getRuntime().availableProcessors());
		Master master = new Master(new MyWorker(), Runtime.getRuntime().availableProcessors());
		Random random = new Random();
		for(int i = 1; i <= 100; i++){
			Job job = new Job(i, "任务_" + i, random.nextInt(1000));
			master.submit(job);
		}
		master.execute();
		long start = System.currentTimeMillis();
		while (true) {
			if(master.isComplete()){
				long end = System.currentTimeMillis() - start;
				int ret = master.getResult();
				System.out.println("处理结果是 : " + ret + ",  最终执行耗时：" + end);
				break;
			}
		}
	}
	
}
