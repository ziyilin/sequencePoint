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
	public static Map<Integer, String> MonitorThreadMap = new ConcurrentHashMap<>();

	public OneThreadDemo() {
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Current in T:"
						+ Thread.currentThread().hashCode());
				modifier = t.getName();

				// Insert Sequence Point Here,
				// Sp@Site@Thread(Debug_Name)=1@(11,17)@t
			}
		});
		System.out.println("T:" + t.hashCode());
		System.out.println("Current in OneThreadDemo:"
				+ Thread.currentThread().hashCode());

	}

	public void startThread() {
		t.setName("T");
		// Before Start
		MonitorThreadMap.put(t.hashCode(), t.getName());		
		t.start();

		//
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
		MonitorThreadMap.put(t1.hashCode(), t1.getName());
		System.out.println(t1.getName());
		
		Thread t = new Thread() {
			public void run() {
				System.out.println("Test Anonymous Thread.");
			}
		};
		t.start();
		
		OneThreadDemo demo = new OneThreadDemo();
		demo.startThread();
		System.out.println("Current in main:" + t1.hashCode());
		System.out.println("Demo:" + demo.hashCode());
	}
}
