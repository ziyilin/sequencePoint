package edu.sjtu.stap.checkmate.loadclass.test;

public class DemoThread1 implements Runnable {
	private Person p;
	
	public DemoThread1(Person p) {
		this.p = p;
	}
	@Override
	public void run() {
		p.setAge(1);
	}

}
