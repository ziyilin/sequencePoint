public class TraceProgram {
	static Object l1265744872 = new Object();
	static Object l0 = new Object();
	static Object l1666948712 = new Object();
	static Object l1 = new Object();
	static Object l1443465675 = new Object();
	static Object l166999797 = new Object();
	static Object l1068592722 = new Object();
	static Object l871639912 = new Object();
	static Object l246688990 = new Object();
	static Object l1497207796 = new Object();
	static Object l659025413 = new Object();
	static boolean c259943062;
	static boolean c400535505;
	static boolean c24105143;
	static boolean c870314914;
	static boolean c1586482837;
	static boolean c1457895203;
	static Thread t1 = new Thread() {
		public void run() {
			t9.start();

			synchronized (l1) { // org.apache.log4j.AsyncAppender@setBufferSize@393
				l1.notifyAll();// org.apache.log4j.AsyncAppender@setBufferSize@398
			}

			t10.start();

			synchronized (l1497207796) { // org.apache.log4j.Category@callAppenders@201
				synchronized (l1666948712) { // org.apache.log4j.AppenderSkeleton@doAppend@-1
					c259943062 = false;
					synchronized (l1) { // org.apache.log4j.AsyncAppender@append@173
						if (c259943062) {
							synchronized (l1) {
								try {
									l1.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							} // ending

							l246688990.notifyAll();// org.apache.log4j.AsyncAppender@append@187
						} // ending

					}

				}

			}

			try {
				t10.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	static Thread t9 = new Thread() {
		public void run() {
			c24105143 = true;
			synchronized (l1) { // org.apache.log4j.AsyncAppender$Dispatcher@run@562
				if (c24105143) {
					try {
						l1.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}// org.apache.log4j.AsyncAppender$Dispatcher@run@567
				} // ending

				l1.notifyAll();// org.apache.log4j.AsyncAppender$Dispatcher@run@595
			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender$Dispatcher@run@604
				synchronized (l1068592722) { // org.apache.log4j.AppenderSkeleton@doAppend@-1
				}

			}

			synchronized (l246688990) { // org.apache.log4j.AsyncAppender$Dispatcher@run@562
				if (c24105143) {
					synchronized (l246688990) {
						try {
							l246688990.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				l1.notifyAll();// org.apache.log4j.AsyncAppender$Dispatcher@run@595
			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender$Dispatcher@run@604
				synchronized (l1068592722) { // org.apache.log4j.AppenderSkeleton@doAppend@-1
				}
			}
		}
	};
	static Thread t10 = new Thread() {
		public void run() {
			synchronized (l1497207796) { // org.apache.log4j.Category@callAppenders@201
				synchronized (l1666948712) { // org.apache.log4j.AppenderSkeleton@doAppend@-1
					c870314914 = false;
					synchronized (l1) { // org.apache.log4j.AsyncAppender@append@173
						if (c870314914) {
							synchronized (l1) {
								try {
									l1.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							} // ending

							l871639912.notifyAll();// org.apache.log4j.AsyncAppender@append@187
						} // ending

					}

				}

			}

			synchronized (l1497207796) { // org.apache.log4j.Category@callAppenders@201
				synchronized (l1666948712) { // org.apache.log4j.AppenderSkeleton@doAppend@-1
					c1457895203 = false;
					synchronized (l1) { // org.apache.log4j.AsyncAppender@append@173
						if (c1457895203) {
							synchronized (l1) {
								try {
									l1.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							} // ending

							l1265744872.notifyAll();// org.apache.log4j.AsyncAppender@append@187
						} // ending

					}

				}

			}

			synchronized (l1497207796) { // org.apache.log4j.Category@callAppenders@201
				synchronized (l1666948712) { // org.apache.log4j.AppenderSkeleton@doAppend@-1
					c400535505 = false;
					synchronized (l1265744872) { // org.apache.log4j.AsyncAppender@append@173
						if (c400535505) {
							synchronized (l1265744872) {
								try {
									l1265744872.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							} // ending

						} // ending

					}

				}

			}

			synchronized (l1497207796) { // org.apache.log4j.Category@callAppenders@201
				synchronized (l1666948712) { // org.apache.log4j.AppenderSkeleton@doAppend@-1
					c1586482837 = false;
					synchronized (l1443465675) { // org.apache.log4j.AsyncAppender@append@173
						if (c1586482837) {
							synchronized (l1443465675) {
								try {
									l1443465675.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							} // ending

						} // ending

					}

				}

			}

			synchronized (l1497207796) { // org.apache.log4j.Category@callAppenders@201
				synchronized (l1666948712) { // org.apache.log4j.AppenderSkeleton@doAppend@-1
					synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@146
						synchronized (l1068592722) { // org.apache.log4j.AppenderSkeleton@doAppend@-1
						}

					}

				}

			}

		}
	};

	public static void main(String[] args) {
		t1.start();
	}
}