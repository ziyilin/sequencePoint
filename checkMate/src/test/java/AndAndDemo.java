public class AndAndDemo {
	private static String name = "123";

	public static void main(String[] args) {
		new Thread1().start();
		new Thread2().start();
	}

	private static class Thread1 extends Thread {
		public void run() {
			if (name != null) {
				SpMap.insertSP(1, Thread.currentThread().hashCode());
				SpMap.checkThread(1);

				SpMap.insertSP(4, Thread.currentThread().hashCode());
				SpMap.checkThread(4);
				if (name.equals("111")) {
					System.out.println(name);
				} else {
					System.out.println(name);
				}
			}
		}
	}

	private static class Thread2 extends Thread {
		public void run() {
			SpMap.insertSP(2, Thread.currentThread().hashCode());
			SpMap.checkThread(2);
			name = null;
			SpMap.insertSP(3, Thread.currentThread().hashCode());
			SpMap.checkThread(3);
		}
	}
}
