package edu.sjtu.stap.checkmate.demo.logger.simpler;

import edu.sjtu.stap.monday.utils.JarTest;

public class TestSimplerLogger {
	JarTest jar;

	public TestSimplerLogger() {
		jar = new JarTest(1,2);
	}

	private void run() {
		Thread w1 = new WorkerThread1();
		Thread w2 = new WorkerThread2();
		w1.start();
		w2.start();
		try {
			w1.join();
			w2.join();
		} catch (InterruptedException e) {

		}
	}

	public static void main(String[] args) {
		TestSimplerLogger test = new TestSimplerLogger();
		test.run();
	}

	private class WorkerThread1 extends Thread {
		public WorkerThread1() {
			super("WorkerThread 1");
		}

		@Override
		public void run() {
			try {
				// trigger race
				jar.append(3);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private class WorkerThread2 extends Thread {

		public WorkerThread2() {
			super("WorkerThread 2");
		}

		public void run() {
			System.out.println(jar.getSum());
			// trigger race
			jar.setSb(null);
		}
	}

}
