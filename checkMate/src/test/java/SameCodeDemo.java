public class SameCodeDemo {
	private static String name = "name";

	public static void main(String[] args) {
		Thread1 t1 = new Thread1();
		Thread1 t2 = new Thread1();
		SpMap.insertSP(1, t1.hashCode());
		SpMap.insertSP(4, t1.hashCode());
		SpMap.insertSP(2, t2.hashCode());
		SpMap.insertSP(3, t2.hashCode());
		
		t1.start();
		t2.start();
	}

	private static class Thread1 extends Thread {
		public void run() {
			if (name != null) {
				SpMap.checkThread(1);

				SpMap.checkThread(4);
				if (name.equals("name")) {
					SpMap.checkThread(2);
					name = null;
					SpMap.checkThread(3);
				} else {
					System.out.println(name);
				}
			}
		}
	}
}
