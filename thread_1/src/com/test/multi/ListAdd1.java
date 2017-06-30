package com.test.multi;

import java.util.ArrayList;
import java.util.List;

public class ListAdd1 {

	private volatile List<String> list = new ArrayList<String>();
	
	public void add(){
		list.add("bjsxt");
	}
	
	public int size(){
		return list.size();
	}
	
	public static void main(String[] args) {
		final ListAdd1 list1 = new ListAdd1();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i = 0; i < 10; i++){
					list1.add();
					System.out.println("当前线程为:" + Thread.currentThread().getName() + ", 添加了一个元素。");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, "t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					if(list1.size() == 5){
						System.out.println("当前线程收到通知:" + Thread.currentThread().getName() + " list size=" + list1.size());
						throw new RuntimeException();
					}
				}
			}
		}, "t2");
		t1.start();
		t2.start();
	}
}
