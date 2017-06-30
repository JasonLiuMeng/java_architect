package com.test.sync4;

public class DubbleCheck {

	private static DubbleCheck dc;
	
	private DubbleCheck(){
		
	}
	
	public static DubbleCheck getInstance(){
		if( null == dc ){
			synchronized (DubbleCheck.class) {
				if( null == dc ){
					System.out.println("实例化...");
					dc = new DubbleCheck();
				}
			}
		}
		return dc;
	}
	
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println(DubbleCheck.getInstance().hashCode());
			}
		});
		Thread t2 = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						System.out.println(DubbleCheck.getInstance().hashCode());
					}
				});
		Thread t3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println(DubbleCheck.getInstance().hashCode());
			}
		});
		t1.start();
		t2.start();
		t3.start();
	}
	
}
