package com.test.sync5;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class WangBa extends Thread {

	private DelayQueue<WangMin> swQueue = new DelayQueue<WangBa.WangMin>();
	public boolean yinye = true;
	
	public void shangji(String name, String id, int money){
		WangMin wangMin = new WangMin(name, id, money);
		System.out.println(wangMin.toString());
		this.swQueue.add(wangMin);
	}
	
	public void xiaji(WangMin w){
		System.out.println("网名："+w.getName() + " 身份证："+w.getId() + "，时间到，下机. ");
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(yinye){
			try {
				WangMin w = this.swQueue.take();
				System.out.println("获取到元素...");
				xiaji(w);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("网吧开始营业...");
		WangBa siyu = new WangBa();
		Thread shangwang = new Thread(siyu);
		shangwang.start();
		
		siyu.shangji("路人甲", "123", 1);
		siyu.shangji("路人乙", "234", 10);
		siyu.shangji("路人丙", "345", 5);
	}
	
	static class WangMin implements Delayed{
		private String name;
		private String id;
		private int money;
		private long endTime;
		private TimeUnit timeUnit = TimeUnit.SECONDS;
		
		public WangMin(String name, String id, int money) {
			this.name = name;
			this.id = id;
			this.money = money;
			this.endTime = 1000 * money + System.currentTimeMillis();
		}

		public int getMoney() {
			return money;
		}

		public void setMoney(int money) {
			this.money = money;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public long getEndTime() {
			return endTime;
		}

		public void setEndTime(long endTime) {
			this.endTime = endTime;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "网名：" +this.name + " 身份证：" + this.id + "押金：" + this.money + "，开始上机..."; 
		}
		
		@Override
		public int compareTo(Delayed o) {
			// TODO Auto-generated method stub
			WangMin w = (WangMin) o;
			return this.getDelay(this.timeUnit) - w.getDelay(timeUnit) > 0 ? 1 : 0;
		}
		
		@Override
		public long getDelay(TimeUnit unit) {
			// TODO Auto-generated method stub
			return endTime - System.currentTimeMillis();
		}
	}
	
}
