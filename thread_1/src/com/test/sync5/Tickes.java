package com.test.sync5;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class Tickes {

	public static void main(String[] args) throws Exception{
		final Map<String, String> cMap = new ConcurrentHashMap<>();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i = 0; i < 100; i++){
					cMap.put("key-" + i, "value-" + i);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		},"t1");
		
		Thread t2 = new Thread(new Runnable() {
					
					@Override
					public void run() {
						for(int i = 0; i < 100; i++){
							cMap.put("key-" + i, "value-" + i);
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
		},"t2");
		
		Thread t3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					System.out.println(cMap);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		},"t3");
		
		t1.start();
		t2.start();
		t3.start();
	
		List<String> cwlist = new CopyOnWriteArrayList<>();
		cwlist.add("");
	}
		
}

