package com.db.access;

public class Test {

	public void print() {
		String path = this.getClass().getResource("/").getPath();
		System.out.println("��һ�Σ�" + path);
		path = path.substring(1, path.indexOf("classes"));
		System.out.println("�ڶ��Σ�" + path);
	}
	
	public static void main(String[] args) {
		Test test = new Test();
		test.print();
	}
}
