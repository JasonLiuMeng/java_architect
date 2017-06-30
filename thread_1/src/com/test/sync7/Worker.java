package com.test.sync7;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 1、每一个Worker为一个线程，所以必须要实现Runnable接口
 * 2、每一个Worker必须要有Master的ConcurrentLinkedQueue的引用，用于从中获取任务
 * 3、每一个Worker必须要有Master的ConcurrentHashMap的引用，用来将Worker的处理结果返回给Master
 * @author jliu10
 *
 */
public class Worker implements Runnable {

	private ConcurrentLinkedQueue<Job> masterJobsQueue;
	private ConcurrentHashMap<String, Object> masterResultsMap;
	
	public void setMasterJobsQueue(ConcurrentLinkedQueue<Job> masterJobsQueue) {
		this.masterJobsQueue = masterJobsQueue;
	}


	public void setMasterResultsMap(
			ConcurrentHashMap<String, Object> masterResultsMap) {
		this.masterResultsMap = masterResultsMap;
	}

	public Object handle(Job job){
		return null;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			//从队列中取一个任务开始执行
			Job job = this.masterJobsQueue.poll();
			//如果队列中没有任务了，则退出处理
			if( null == job ){
				break;
			}
			Object object = this.handle(job);
			this.masterResultsMap.put(Integer.toString(job.getId()), object);
		}
	}
	
}
