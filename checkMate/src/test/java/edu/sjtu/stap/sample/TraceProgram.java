package edu.sjtu.stap.sample;

public class TraceProgram {
	static Object bf = new Object();
	static boolean isFull;
	static Thread main = new Thread() {
		public void run() {
			isFull = false;
			p.start();
			r.start();
			c.start();
		}
	};
	static Thread p = new Thread() {
		public void run() {
			synchronized (bf) { // enter bf.put (0)
				if (isFull) {
					synchronized (bf) {
						try {
							bf.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				isFull = true;
				bf.notify();
			} // leave bf.put (0)
			synchronized (bf) { // enter bf.put (1)
				if (isFull) {
					synchronized (bf) {
						try {
							bf.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				bf.notify();
			} // leave bf.put (1)
		}
	};
	static Thread r = new Thread() {
		public void run() {
			synchronized (bf) { // enter bf. resize (10)
				isFull = false;
			} // leave bf. resize (10)
		}
	};
	static Thread c = new Thread() {
		public void run() {
			synchronized (bf) { // enter bf.get ()
				if (isFull) {
					synchronized (bf) {
						bf.notify();
					}
				}
			} // leave bf.get ()
		}
	};

	public static void main(String[] args) {
		main.start();
	}
}