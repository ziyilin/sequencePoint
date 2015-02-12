public class TraceProgram {
	static Object l944780329 = new Object();
	static Object l166999797 = new Object();
	static Object l193026137 = new Object();
	static Object l1809663735 = new Object();
	static Object l1884511064 = new Object();
	static boolean c1627433763;
	static boolean c1888626692;
	static boolean c768288241;
	static boolean c1065330270;
	static boolean c2114843907;
	static boolean c1147894048;
	static boolean c1927526549;
	static boolean c428903585;
	static boolean c1948780723;
	static boolean c246530656;
	static boolean c207501684;
	static Thread t1 = new Thread() {
		public void run() {
			synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@setMaxActive@-1
				synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@-1
					c1065330270 = false;
				}

			}

			synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@setWhenExhaustedAction@-1
				synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@-1
					c768288241 = false;
				}

			}

			synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@borrowObject@1295
				synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@-1
					c1948780723 = false;
					if (c1948780723) {
						synchronized (l1884511064) {
							l1884511064.notify();
						} // ending

					} // ending

					synchronized (l1884511064) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@1483
						l1884511064.notify();// org.apache.commons.pool.impl.GenericObjectPool@allocate@1484
					}

				}

			}

			c1888626692 = false;
			if (c1888626692) {
				synchronized (l1884511064) {
					try {
						l1884511064.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} // ending

			} // ending

			synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@addObjectToPool@1655
				synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@-1
					c1927526549 = false;
				}

			}

			synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@borrowObject@1295
				synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@-1
					c1147894048 = true;
					if (c1147894048) {
						synchronized (l1809663735) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@1460
							l1809663735.notify();// org.apache.commons.pool.impl.GenericObjectPool@allocate@1461
						}

					} // ending

				}

			}

			c207501684 = false;
			if (c207501684) {
				synchronized (l1809663735) {
					try {
						l1809663735.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} // ending

			} // ending

			synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@addObjectToPool@1655
				synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@-1
					c2114843907 = false;
				}

			}

			synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@close@1707
				synchronized (l193026137) { // Pool162$SimpleFactory@destroyObject@107
				}

			}

		}
	};
	static Thread t9 = new Thread() {
		public void run() {
			synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@borrowObject@1295
				synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@-1
					c246530656 = false;
					if (c246530656) {
						synchronized (l944780329) {
							l944780329.notify();
						} // ending

					} // ending

					synchronized (l944780329) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@1483
						l944780329.notify();// org.apache.commons.pool.impl.GenericObjectPool@allocate@1484
					}

				}

			}

			c428903585 = false;
			if (c428903585) {
				synchronized (l944780329) {
					try {
						l944780329.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} // ending

			} // ending

			synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@addObjectToPool@1655
				synchronized (l166999797) { // org.apache.commons.pool.impl.GenericObjectPool@allocate@-1
					c1627433763 = false;
				}

			}

		}
	};

	public static void main(String[] args) {
		t1.start();
		t9.start();
	}
}