package com.test.sync3;

import java.util.concurrent.atomic.AtomicInteger;

public class MultiVolatile2 extends Thread{

	private static AtomicInteger count = new AtomicInteger();
	
	public void addCount(){
		for( int i = 0; i < 1000; i++){
			count.incrementAndGet();
		}
		System.out.println(count);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.addCount();
	}
	
	
	public static void main(String[] args) {
		MultiVolatile2 multiVolatile2[] = new MultiVolatile2[10];
		for(int i = 0; i < 10; i++){
			multiVolatile2[i] = new MultiVolatile2();
		}
		for(int i = 0; i < 10; i++){
			multiVolatile2[i].start();
		}
		System.out.println(MultiVolatile2.count);
	}
}
