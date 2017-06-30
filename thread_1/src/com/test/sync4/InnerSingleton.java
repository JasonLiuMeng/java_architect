package com.test.sync4;

public class InnerSingleton {

	static{
		System.out.println("加载类InnerSingleton");
	}
	
	private InnerSingleton(){
		System.out.println("初始化InnerSingleton");
	}
	
	private static class Singleton{
		static{
			System.out.println("加载类Singleton");
		}
		private static InnerSingleton single = new InnerSingleton();
		private Singleton(){}
	}
	
	public static InnerSingleton getInstance(){
		System.out.println("调用getInstance");
		return Singleton.single;
	}
	
	public static void main(String[] args) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println(InnerSingleton.getInstance().hashCode());
			}
		});
		Thread t2 = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						System.out.println(InnerSingleton.getInstance().hashCode());
					}
				});
		Thread t3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println(InnerSingleton.getInstance().hashCode());
			}
		});
		t1.start();
		t2.start();
		t3.start();
	}
}
