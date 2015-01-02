package edu.sjtu.stap.checkmate.loadclass.test;

import edu.sjtu.stap.monday.utils.JarTest;

public class DemoThread1 implements Runnable {
	private Person p;

	public DemoThread1(Person p) {
		this.p = p;
	}

	@Override
	public void run() {
		p.setAge(1);
		JarTest test1 = new JarTest(p.getAge(), 0);
		System.out.println("Test From Jar: " + test1.getSum());
	}
}
