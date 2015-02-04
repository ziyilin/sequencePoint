public class TraceProgram {
	static Object l130672250 = new Object();
	static Object l2131361171 = new Object();
	static Object l858132903 = new Object();
	static boolean c227383376;
	static boolean c614213397;
	static Thread t1 = new Thread() {
		public void run() {
			c227383376 = false;
			synchronized (l2131361171) { // org.apache.commons.pool.impl.GenericObjectPool@borrowObject@1053
				synchronized (l2131361171) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@-1
					synchronized (l858132903) { // org.apache.commons.pool.impl.GenericObjectPool$Latch@setMayCreate@-1
					}

					synchronized (l858132903) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@1209
						l858132903.notify();// org.apache.commons.pool.impl.GenericObjectPool@allocate@1210
					}

				}

			}

			if (c227383376) {
				synchronized (l858132903) {
					try {
						l858132903.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} // ending

			} // ending

			t12.start();

			t13.start();

			try {
				t12.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				t13.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	static Thread t12 = new Thread() {
		public void run() {
			synchronized (l2131361171) { // org.apache.commons.pool.impl.GenericObjectPool@invalidateObject@1231
				synchronized (l2131361171) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@-1
					synchronized (l130672250) { // org.apache.commons.pool.impl.GenericObjectPool$Latch@setMayCreate@-1
					}

					synchronized (l130672250) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@1209
						l130672250.notify();// org.apache.commons.pool.impl.GenericObjectPool@allocate@1210
					}

				}

			}

		}
	};
	static Thread t13 = new Thread() {
		public void run() {
			c614213397 = true;
			if (c614213397) {
				synchronized (l130672250) { // org.apache.commons.pool.impl.GenericObjectPool@borrowObject@1095
					try {
						l130672250.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}// org.apache.commons.pool.impl.GenericObjectPool@borrowObject@1097
				}

			} // ending

			if (c614213397) {
				synchronized (l130672250) {
					try {
						l130672250.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} // ending

			} // ending

		}
	};

	public static void main(String[] args) {
		t1.start();
	}
}