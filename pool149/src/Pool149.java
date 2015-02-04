import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * Bug URL:https://issues.apache.org/jira/browse/POOL-149 This is a wait-notify
 * deadlock. Reproduce environment: commons-pool-1.5, JDK 1.6.0_33.
 * 
 * This test shall not reveal the deadlock reliably. The detection tools can use
 * this test to verify their ability.
 * 
 * @collector Ziyi Lin
 */
public class Pool149 {

	public static int MAX_ACTIVE = 1;
	private ObjectPool testPool;

	public Pool149() {
		testPool = new GenericObjectPool(new DummyPoolableObjectFactory(),
				MAX_ACTIVE);
	}

	public void run() throws Exception {

		Object obj = testPool.borrowObject();

		Thread t1 = new Thread1(obj);
		Thread t2 = new Thread2();
		t1.start();
		t2.start();

		t1.join();
		t2.join();

	}

	public static void main(String[] args) throws Exception {
		Pool149 test = new Pool149();
		test.run();
	}

	/**
	 * A dummy PoolableObjectFactory, creates Strings with unique names
	 */
	private class DummyPoolableObjectFactory implements PoolableObjectFactory {

		public Object makeObject() throws Exception {
			return "Object number-" + counter.getAndIncrement();
		}

		public void destroyObject(Object obj) throws Exception {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		public boolean validateObject(Object obj) {
			return true;
		}

		public void activateObject(Object obj) throws Exception {
		}

		public void passivateObject(Object obj) throws Exception {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		private final AtomicInteger counter = new AtomicInteger();
	}

	class Thread1 extends Thread {

		private Object object;

		public Thread1(Object o) {
			object = o;
		}

		public void run() {

			try {
				testPool.invalidateObject(object);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class Thread2 extends Thread {
		public void run() {
			try {
				testPool.borrowObject();
			} catch (Exception ex) {
			}
		}
	}
}
