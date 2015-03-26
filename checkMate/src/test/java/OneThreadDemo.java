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
	// Insert MonitorThreadMap, which maps Thread.hashCode to sequence point
	public static Map<Integer, Integer> ThreadSequenceMap = new ConcurrentHashMap<>();
	public static int SP = 0;

	public OneThreadDemo() {
		// Declaration here
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				modifier = t.getName();
				// Insert Sequence Point Here,
				// Sp@Site@Thread(Debug_Name)=1@(11,17)@t
				if (ThreadSequenceMap.containsKey(Thread.currentThread()
						.hashCode()))
					SP = ThreadSequenceMap.get(Thread.currentThread()
							.hashCode());
			}
		});
		// Thread t, sp = 1;
		ThreadSequenceMap.put(t.hashCode(), 2);
	}

	public void startThread() {
		t.setName("T");

		t.start();

		if (ThreadSequenceMap.containsKey(Thread.currentThread().hashCode()))
			SP = ThreadSequenceMap.get(Thread.currentThread().hashCode());
		modifier = getClass().getSimpleName();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Last Modifier is " + modifier);
	}

	public static void main(String[] args) {
		// main thread, sp = 2;
		ThreadSequenceMap.put(Thread.currentThread().hashCode(), 1);

		Thread.currentThread().setName("OneThreadDemo");

		Thread t = new Thread() {
			@Override
			public void run() {
				System.out.println("Test Anonymous Thread.");
			}
		};
		t.start();

		OneThreadDemo demo = new OneThreadDemo();
		demo.startThread();
	}
}
