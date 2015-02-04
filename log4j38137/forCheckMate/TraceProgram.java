public class TraceProgram {
	static Object l1551156138 = new Object();
	static Object l2009698649 = new Object();
	static Object l166999797 = new Object();
	static Object l826845731 = new Object();
	static boolean c414667768;
	static boolean c736742261;
	static boolean c1312737936;
	static boolean c1408212765;
	static boolean c836051140;
	static Thread t1 = new Thread() {
		public void run() {
			c1312737936 = false;
			c836051140 = false;
			c1408212765 = false;
			c414667768 = false;
			c736742261 = false;
			t10.start();

			t11.start();

			t12.start();

			try {
				t10.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				t11.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				t12.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	static Thread t9 = new Thread() {
		public void run() {
			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

					try {
						l166999797.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}// org.apache.log4j.Dispatcher@run@372
				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c414667768) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.Dispatcher@run@360
				if (c736742261) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending
				}
				try {
					l166999797.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}// org.apache.log4j.Dispatcher@run@372
			}
		}
	};
	static Thread t10 = new Thread() {
		public void run() {
			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

		}
	};
	static Thread t11 = new Thread() {
		public void run() {
			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					l166999797.notify();// org.apache.log4j.AsyncAppender@append@149
				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					l166999797.notify();// org.apache.log4j.AsyncAppender@append@149
				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

		}
	};
	static Thread t12 = new Thread() {
		public void run() {
			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

			synchronized (l166999797) { // org.apache.log4j.AsyncAppender@append@128
				if (c1312737936) {
					synchronized (l166999797) {
						try {
							l166999797.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} // ending

				} // ending

				if (c836051140) {
					synchronized (l166999797) {
						l166999797.notify();
					} // ending

				} // ending

			}

		}
	};

	public static void main(String[] args) {
		t1.start();
		t9.start();
	}
}