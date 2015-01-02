package edu.sjtu.stap.checkmate.sample;

/**
 * A dead lock sample.
 * @author Ziyi
 *
 */
public class Deadlocker {

	private volatile boolean flag;
	private int a;
	private Object o;

	public Deadlocker() {
		a=0;
		flag = false;
		o = new Object();
	}

	public void doSomething() {
		int a = new Noise().add(1, 2);  //just a noise, no side effects
		foo();
	}

	public void doAnotherThing(int value) {
		if (new Noise().isEvent(value)) {  //This input matters
			synchronized (o) {
				//1
				resetA();
			}
		}
	}

	private synchronized void foo() {
		//2
		if (flag) {
			//4
			synchronized (o) {
				a++;
			}
		}
	}

	private void resetA() {
		flag=true;
		//3
		synchronized (this) {
			a=a*a;
		}
	}
	
	public static void main(String[] args) {
		final Deadlocker deadlocker=new Deadlocker();
		
		Thread t1=new Thread(new Runnable(){
			public void run(){
				deadlocker.doSomething();
			}
		});
		
		Thread t2 =new Thread(new Runnable(){
			public void run(){
				deadlocker.doAnotherThing(4);
			}
		});
		
		t1.start();
		t2.start();

	}
}

class Noise {
	public int add(int a, int b) {
		return a + b;
	}

	public boolean isEvent(int value) {
		return value % 2 == 0;
	}
}