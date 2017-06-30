package com.test.sync1;

public class Extend {
		
	static class Main{
		public int num = 10;
		public synchronized void main(){
			num--;
			System.out.println("Main method, num : " + num);
		}
	}
	
	static class Sub extends Main{
		public synchronized void sub(){
			while(num > 0){
				num--;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Sub method, num : " + num);
				this.main();
			}
			
		}
	}
	
	public static void main(String[] args) {
		final Sub sub = new Sub();
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				sub.sub();
			}
		});
		t1.start();
	}
	
}
