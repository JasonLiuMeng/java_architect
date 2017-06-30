package com.test;


public class DirtyRead {

	private String userName = "bjsxt";
	private String passWord = "123";
	
	public synchronized void setValue(String userName, String passWord){
		this.userName = userName;
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.passWord = passWord;
		System.out.println("set value over, userName : " + userName + ", passWord : " + passWord);
	}
	
	public synchronized void getValue(){
		System.out.println("get value, userName : " + userName + ", passWord : " + passWord);
	}
	
	public static void main(String[] args) throws Exception {
		final DirtyRead dirtyRead = new DirtyRead();
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				dirtyRead.setValue("张三", "456");
			}
		});
		t1.start();
		Thread.sleep(1000);
		dirtyRead.getValue();
	}
	
}
