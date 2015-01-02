package edu.sjtu.stap.checkmate.loadclass.test;

public class DemoThread2 implements Runnable {
	private Person p;
	
	public DemoThread2(Person p) {
		this.p = p;
	}
	@Override
	public void run() {
		p.setAge(3);
	}

}
