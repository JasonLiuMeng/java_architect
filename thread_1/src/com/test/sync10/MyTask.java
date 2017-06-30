package com.test.sync10;

public class MyTask implements Runnable{

	private int taskId;
	private String taskName;
	
	public MyTask(int taskId, String taskName) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println(Thread.currentThread().getName() + " - run taskId = " + this.taskId);
			Thread.sleep(5 * 1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Integer.toString(this.taskId);
	}
	
	
}
