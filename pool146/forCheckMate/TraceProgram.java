public class TraceProgram {
	static Object l1065330270 = new Object();
	static Object l1489977589 = new Object();
	static Object l899130236 = new Object();
	static Object l193026137 = new Object();
	static Object l1434682851 = new Object();
	static Object l1884511064 = new Object();
	static Object l4461550 = new Object();
	static boolean c1888626692;
	static boolean c1970826852;
	static boolean c1948780723;
	static boolean c1312737936;
	static Thread t1 = new Thread() {
		public void run() {
			c1312737936 = false;
			c1970826852 = true;
			synchronized (l4461550) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@borrowObject@1104
				synchronized (l4461550) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@allocate@1241
					synchronized (l899130236) { // org.apache.commons.pool.impl.GenericKeyedObjectPool$Latch@getkey@-1
					}

					if (c1312737936) {
						synchronized (l899130236) {
							l899130236.notify();
						}

					}

					synchronized (l899130236) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@allocate@1286
						l899130236.notify();// org.apache.commons.pool.impl.GenericKeyedObjectPool@allocate@1287
					}

				}

			}

			if (c1970826852) {
				synchronized (l899130236) {
					try {
						l899130236.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

			synchronized (l4461550) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@borrowObject@1203
				synchronized (l899130236) { // org.apache.commons.pool.impl.GenericKeyedObjectPool$Latch@getPool@-1
				}

			}

			t9.start();

			c1948780723 = true;
			synchronized (l4461550) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@borrowObject@1104
				synchronized (l4461550) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@allocate@1241
					synchronized (l1884511064) { // org.apache.commons.pool.impl.GenericKeyedObjectPool$Latch@getkey@-1
					}

					if (c1312737936) {
						synchronized (l1884511064) {
							l1884511064.notify();
						}

					}

					synchronized (l1884511064) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@allocate@1286
						l1884511064.notify();// org.apache.commons.pool.impl.GenericKeyedObjectPool@allocate@1287
					}

				}

			}

			if (c1948780723) {
				synchronized (l1884511064) {
					try {
						l1884511064.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

			synchronized (l4461550) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@borrowObject@1203
				synchronized (l1884511064) { // org.apache.commons.pool.impl.GenericKeyedObjectPool$Latch@getPool@-1
				}

			}

			synchronized (l4461550) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@addObjectToPool@1565
				synchronized (l1065330270) { // org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue@decrementActiveCount@2134
				}

				synchronized (l4461550) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@allocate@1241
					synchronized (l193026137) { // org.apache.commons.pool.impl.GenericKeyedObjectPool$Latch@getkey@-1
					}

					if (c1312737936) {
						synchronized (l193026137) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@allocate@1263
							l193026137.notify();// org.apache.commons.pool.impl.GenericKeyedObjectPool@allocate@1264
						}

					}

				}

			}

			try {
				t9.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	static Thread t9 = new Thread() {
		public void run() {
			c1888626692 = true;
			synchronized (l4461550) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@borrowObject@1104
				synchronized (l4461550) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@allocate@1241
					synchronized (l193026137) { // org.apache.commons.pool.impl.GenericKeyedObjectPool$Latch@getkey@-1
					}

					if (c1312737936) {
						synchronized (l193026137) {
							l193026137.notify();
						}

					}

				}

			}

			if (c1888626692) {
				synchronized (l193026137) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@borrowObject@1146
					try {
						l193026137.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}// org.apache.commons.pool.impl.GenericKeyedObjectPool@borrowObject@1148
				}

			}

			if (c1888626692) {
				synchronized (l193026137) {
					try {
						l193026137.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

			synchronized (l4461550) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@borrowObject@1203
				synchronized (l193026137) { // org.apache.commons.pool.impl.GenericKeyedObjectPool$Latch@getPool@-1
				}

			}

			synchronized (l4461550) { // org.apache.commons.pool.impl.GenericKeyedObjectPool@addObjectToPool@1565
				synchronized (l1065330270) { // org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue@decrementActiveCount@2134
				}

			}

		}
	};

	public static void main(String[] args) {
		t1.start();
	}
}