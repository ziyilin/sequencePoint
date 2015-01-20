package edu.sjtu.stap.concurrent;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;


/**
 * Bug URL:https://issues.apache.org/jira/browse/POOL-149
 * This is a wait-notify deadlock. 
 * Reproduce environment: commons-pool-1.5, JDK 1.6.0_33.
 * 
 * This test shall not reveal the deadlock reliably. The detection tools
 * can use this test to verify their ability.
 * 
 * @collector Ziyi Lin
 */
public class Pool149 {

    public static int MAX_ACTIVE = 1;


	public void run() throws Exception {

		ObjectPool testPool = new GenericObjectPool(
				new DummyPoolableObjectFactory(), MAX_ACTIVE);
		runOnce(testPool);
	}

    private void runOnce(ObjectPool testPool) throws Exception {
        Thread t1 = new Thread(new TestThread1(testPool));
        Thread t2 = new Thread(new TestThread2(testPool));

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
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void activateObject(Object obj) throws Exception {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void passivateObject(Object obj) throws Exception {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private final AtomicInteger counter = new AtomicInteger();
    }

    class TestThread1 implements Runnable {

        private final ObjectPool testPool;

        public TestThread1(ObjectPool testPool) {
            this.testPool = testPool;
        }

        public void run() {
            Object obj = null;
            try {
                obj = testPool.borrowObject();
            } catch (Exception ex) {
            } finally {
                try {
                    testPool.invalidateObject(obj);
                } catch (Exception ex) {
                }
            }
        }
    }

    class TestThread2 implements Runnable {

        private final ObjectPool testPool;

        public TestThread2(ObjectPool testPool) {
            this.testPool = testPool;
        }

        public void run() {
            try {
                testPool.borrowObject();
            } catch (Exception ex) {
            }
        }
    }
}
