package com.test.sync5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class MyQueue {
	public static void main(String[] args) throws Exception{
		ArrayBlockingQueue<String> aq = new ArrayBlockingQueue<>(5);
		aq.put("a");
		aq.put("b");
		aq.add("c");
		aq.add("d");
		aq.add("e");
		System.out.println(aq.offer("f", 2, TimeUnit.SECONDS));
		System.out.println(aq);
		
		LinkedBlockingQueue<String> lq = new LinkedBlockingQueue<>();
		lq.offer("a");
		lq.offer("b");
		lq.offer("c");
		lq.offer("d");
		lq.offer("e");
		lq.add("f");
		System.out.println(lq);
		
		List<String> list = new ArrayList<>();
		System.out.println(lq.drainTo(list, 10));
		System.out.println(list);
		
		SynchronousQueue<String> sq = new SynchronousQueue<>();
		sq.add("123");
		
	}
}
