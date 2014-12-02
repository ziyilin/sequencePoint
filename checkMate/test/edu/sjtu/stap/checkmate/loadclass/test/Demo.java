package edu.sjtu.stap.checkmate.loadclass.test;

public class Demo {

	public static void main(String[] args) {
		boolean test = true;
		if (test) {
			Person a = new Person();
			a.setAge(5);
			System.out.println(a.getAge());
		}
	}

}
