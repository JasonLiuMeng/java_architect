package com.test.sync4;


class Test2 extends Test{
	public static int c;
	
	public int d;
	
	static{
		System.out.println("c=" + c);
		c = 30;
		System.out.println("c=" + c);
	}
	
	public Test2() {
		// TODO Auto-generated constructor stub
		super();
		System.out.println("d=" + d);
		d = 40;
		System.out.println("d=" + d);
	}
}

public class Test {

	private static int a;
	
	private String string;
	
	static{
		System.out.println("a=" + a);
		a = 10;
		System.out.println("a=" + a);
	}
	
	private int b;
	
	public Test() {
		// TODO Auto-generated constructor stub
		System.out.println("b=" + b);
		System.out.println("str=" + string);
		b = 20;
		System.out.println("b=" + b);
	}
	
	public static void main(String[] args) {
		new Test2();
	}
	
}
