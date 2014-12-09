package edu.sjtu.stap.checkmate.loadclass.test;


public class Demo {
	private Person testPerson = new Person();

	public static void main(String[] args) {
		boolean test = true;
		if (test) {
			Person a = new Person();
			DemoThread1 t1 = new DemoThread1(a);
			DemoThread2 t2 = new DemoThread2(a);
			Thread tt1 = new Thread(t1);
			tt1.start();
			new Thread(t2).start();
			//a.setAge(5);
			try {
				tt1.join();
				tt1.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			System.out.println(a.getAge());
		}
	}

}
