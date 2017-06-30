package com.test.sync11;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class UseFuture implements Callable<String>{

	private String para;
	
	public UseFuture(String para) {
		// TODO Auto-generated constructor stub
		this.para = para;
	}
	
	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		Thread.sleep(3000);
		String result = this.para + "处理完成";
		return result;
	}
	
	public static void main(String[] args) throws Exception{
		String queryStr = "query";
		FutureTask<String> futureTask = new FutureTask<String>(new UseFuture(queryStr));
		FutureTask<String> futureTask1 = new FutureTask<String>(new UseFuture(queryStr));
		ExecutorService pool = Executors.newFixedThreadPool(2);
		long start = System.currentTimeMillis();
		Future f = pool.submit(futureTask);
		Future f1 = pool.submit(futureTask1);
		System.out.println("请求完毕.");
//		System.out.println(f.isDone());
//		System.out.println(f.get()); //如果任务执行完成，那么f.get会返回null
//		System.out.println(f.isDone());
		System.out.println(futureTask.get());
		System.out.println("111111111");
		System.out.println(futureTask1.get());
		long end = System.currentTimeMillis() - start;
		System.out.println(end);
		pool.shutdown();
	}
	
}
