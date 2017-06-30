package com.test.sync4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Modification {

	public static void main(String[] args) {
		final Vector<String> vector = new Vector<String>();
		for( int i = 0; i < 50; i++ ){
			vector.add("str_" + i);
		}
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Iterator<String> it = vector.iterator();
				while (it.hasNext()) {
					System.out.println(it.next());
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for( int i = 50; i < 100; i++){
					vector.add("str_" + i);
				}
			}
		});
		
		
		t1.start();
		t2.start();
		
		Collections.synchronizedList(new ArrayList<>());
		Collections.synchronizedMap(new HashMap<>());
		Collections.synchronizedCollection(new ArrayList<>());
		
		Executor executor = Executors.newFixedThreadPool(10);
	}
	
}
