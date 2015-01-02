package edu.sjtu.stap.checkmate.sample;

public class TraceProgram2 {
	static Object bf = new Object();
	static boolean isFull;
	
	static Thread main = new Thread("0") {
		public void run() {
			synchronized(bf){
				isFull = false;
			}
			p.start();
			r.start();
			c.start();
		}
	};
	static Thread p = new Thread("1") {
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
				isFull = true;
				bf.notify();
			} // leave bf.put (1)
		}
	};
	static Thread r = new Thread("2") {
		public void run() {
			synchronized (bf) { // enter bf. resize (10)
				//isFull = false;
			} // leave bf. resize (10)
		}
	};
	static Thread c = new Thread("3") {
		public void run() {
			synchronized (bf) { // enter bf.get ()
				if (isFull) {
					synchronized (bf) {
						bf.notify();
					}
				}
				isFull = false;
			} // leave bf.get ()
		}
	};

	public static void main(String[] args) {
		main.start();
	}
}