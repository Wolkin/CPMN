package com.db.access;

public class Test {

	public void print() {
		String path = this.getClass().getResource("/").getPath();
		System.out.println("第一次：" + path);
		path = path.substring(1, path.indexOf("classes"));
		System.out.println("第二次：" + path);
	}
	
	public static void main(String[] args) {
		Test test = new Test();
		test.print();
	}
}
