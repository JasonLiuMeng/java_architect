package com.test.sync13.base;

import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.EventFactory;

public class LongEventFactory implements EventFactory<LongEvent>{
	
	private AtomicInteger integer = new AtomicInteger(0);
	
	 @Override
	public LongEvent newInstance() {
		// TODO Auto-generated method stub
//		System.out.println("我被调用了..." + integer.get());
//		integer.incrementAndGet();
		return new LongEvent();
	}
}
