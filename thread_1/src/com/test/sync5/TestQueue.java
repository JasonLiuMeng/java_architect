package com.test.sync5;

import java.util.PriorityQueue;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class TestQueue extends Thread{
	
	private static int id;
	static{
		System.out.println(id);
	}
	
	private static ArrayBlockingQueue<String> aq = new ArrayBlockingQueue<>(100);
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				String string = UUID.randomUUID().toString();
				aq.put( string );
				System.out.println(currentThread().getName() + " put " + string);
				sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	static class Task implements Comparable<Task>{

		private int id;
		private String name;
		

		
		public Task(int id, String name) {
			// TODO Auto-generated constructor stub
			this.id = id;
			this.name = name;
		}
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public int compareTo(Task o) {
			// TODO Auto-generated method stub
			if(this.id > o.id){
				return 1;
			}else if(this.id < o.id){
				return -1;
			}else{
				return 0;
			}
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return this.id + "-" + this.name;
		}
	}
	
	static class Task2 implements Delayed{

		@Override
		public int compareTo(Delayed o) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	public static void main(String[] args) {
		
		ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<String>(0);
		abq.offer("123");
		
		TestQueue tQueue =  new TestQueue();
//		Thread t1 = new Thread(tQueue, "t1");
//		Thread t2 = new Thread(tQueue, "t2");
//		Thread t3 = new Thread(tQueue, "t3");
//		Thread t4 = new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while(true){
//					try {
//						String string = aq.poll();
//						System.out.println(currentThread().getName() + " poll ----------------------------------" + string);
//						sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		}, "t4");
//		t1.start();
//		t2.start();
//		t3.start();
//		t4.start();
		
		PriorityQueue<Task> pq = new PriorityQueue<>();
		Task ts1 = new Task(4, "Task1");
		Task ts2 = new Task(6, "Task2");
		Task ts3 = new Task(1, "Task3");
		Task ts4 = new Task(2, "Task4");
		pq.add(ts1);
		pq.add(ts2);
		pq.add(ts3);
		pq.add(ts4);
		System.out.println(pq);
		
		while(pq.size() > 0){
			System.out.println(pq.poll().name);
		}
		
		DelayQueue<Task2>  dq = new DelayQueue<>();
	}
	
}
