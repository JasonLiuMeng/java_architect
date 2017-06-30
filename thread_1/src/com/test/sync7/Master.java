package com.test.sync7;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 1、使用高性能的并发队列来承装所有的任务，推荐：ConcurrentLinkedQueue
 * 2、使用一个容器来承装所有的Worker对象，不需要并发
 * 3、使用并发类容器来接收work的返回结果，为了能够查寻到是哪一个work的结果，推荐使用Map类容器
 * @author jliu10
 *
 */
public class Master {

	//高性能无锁并发队列，用来接收任务
	private final ConcurrentLinkedQueue<Job> jobsQueue = new ConcurrentLinkedQueue<>();
	
	//用来存放所有的work
	private Map<String, Thread> workersMap = new HashMap<String, Thread>();
	
	//用来承装每一个worker并发处理任务的结果集
	private final ConcurrentHashMap<String, Object> resultsMap = new ConcurrentHashMap<String, Object>();
	
	public Master(Worker worker, int workerCount) {
		//将master的任务队列和结果集传入worker
		worker.setMasterJobsQueue(this.jobsQueue);
		worker.setMasterResultsMap(this.resultsMap);
		for (int i = 0; i < workerCount; i++) {
			//key表示每一个worker的名字, value表示线程执行对象
			workersMap.put("worker-" + i, new Thread(worker));
		}
	}
	
	/**
	 * 提交方法
	 */
	public void submit(Job job){
		this.jobsQueue.add(job);
	}
	
	/**
	 * 执行方法，启动Master应用程序，让所有的Worker开始工作
	 */
	public void execute(){
		for(Map.Entry<String, Thread> me : workersMap.entrySet() ){
			me.getValue().start();
		}
	}

	/**
	 * 判断线程是否执行完毕
	 * @return
	 */
	public boolean isComplete() {
		// TODO Auto-generated method stub
		for(Map.Entry<String, Thread> me : workersMap.entrySet() ){
			if(me.getValue().getState() != Thread.State.TERMINATED){
				return false;
			}
		}
		return true;
	}

	public int getResult() {
		// TODO Auto-generated method stub
		int ret = 0;
		for(Map.Entry<String, Object> me : resultsMap.entrySet()){
			ret += (Integer)me.getValue();
		}
		return ret;
	}
	
}
