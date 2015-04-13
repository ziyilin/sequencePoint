public class LoopThreadDemo {
	private static String name = "name";

	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			int tt;
			Thread t = new Thread(new Runnable() {
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
			});
			int x;
			if (i == 0) {
				SpMap.insertSP(1, t.hashCode());
				SpMap.insertSP(4, t.hashCode());
			}
			if (i == 1) {
				SpMap.insertSP(2, t.hashCode());
				SpMap.insertSP(3, t.hashCode());
			}
			t.start();
		}
	}
}
