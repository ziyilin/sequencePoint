import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Yilei
 * 
 */
public class OneThreadDemo {
	private Thread t;
	private String modifier;
	// Need instrumentation.
	// Insert MonitorThreadMap, which maps Thread.hashCode to Thread.getName()
	// public static Map<Integer, Thread> MonitorThreadMap = new
	// ConcurrentHashMap<>();
	public static int SP;

	public OneThreadDemo() {
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				// MonitorThreadMap.put(1, Thread.currentThread());
				SP = 1;
				modifier = t.getName();

				// Insert Sequence Point Here,
				// Sp@Site@Thread(Debug_Name)=1@(11,17)@t
			}
		});
	}

	public void startThread() {
		t.setName("T");
		// Before Start
		t.start();

		//
		// MonitorThreadMap.put(2, Thread.currentThread());
		SP = 2;
		modifier = getClass().getSimpleName();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(modifier);
	}

	public static void main(String[] args) {
		// Thread.currentThread().setName("OneThreadDemo");
		Thread t1 = Thread.currentThread();

		Thread t = new Thread() {
			public void run() {
				System.out.println("Test Anonymous Thread.");
			}
		};
		t.start();

		OneThreadDemo demo = new OneThreadDemo();
		demo.startThread();
	}
}
