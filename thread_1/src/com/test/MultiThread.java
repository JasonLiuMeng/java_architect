package com.test;

public class MultiThread {
	
	private static int num = 0;
	
	public static synchronized void printNum(String str){
		if( "a".equals(str) ){
			num = 100;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("target a, set number over.");
		}else{
			num = 200;
			System.out.println("target b, set number over.");
		}
		System.out.println("str is "+str+", number is " + num);
	}
	
	
	public static void main(String[] args) {
		
		Thread t1 = new Thread(){
			public void run() {
				MultiThread.printNum("a");
			};
		};
		
		Thread t2 = new Thread(){
			public void run() {
				MultiThread.printNum("b");
			};
		};
		
		t1.start();
		t2.start();
	}
}
