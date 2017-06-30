package com.test.sync3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicUser {
	
//	private static int count = 0;
	private static AtomicInteger count = new AtomicInteger(0);
	
	
	//synchronized
	public int multiAdd(){
		try{
			Thread.sleep(100);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		count += 1;
//		count += 2;
//		count += 3;
//		count += 4;
//		count++;
//		return count;
//		count.addAndGet(10);
//		count.addAndGet(1);
//		count.addAndGet(2);
//		count.addAndGet(3);
//		count.addAndGet(4);
		return count.addAndGet(1);
	}
	
	public static void main(String[] args) {
		final AtomicUser au = new AtomicUser();
		List<Thread> ts = new ArrayList<Thread>();
		for(int i = 0; i < 100; i++){
			ts.add(new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println(au.multiAdd());
				}
			}));
		}
		
		for(Thread t : ts){
			t.start();
		}
//		System.out.println(AtomicUser.count.get());
	}
}
