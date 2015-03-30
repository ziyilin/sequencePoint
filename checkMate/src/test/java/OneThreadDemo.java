
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

	public OneThreadDemo() {
		// Declaration here
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				// Insert Sequence Point Here,
				// Sp@Site@Thread(Debug_Name)=1@(11,17)@t
				SpMap.checkThread();
				modifier = t.getName();
			}
		});
		// Thread t, sp = 2;
		SpMap.insertSP(t.hashCode(), 2);
	}

	public void startThread() {
		t.setName("T");

		t.start();

		modifier = getClass().getSimpleName();
		
		SpMap.checkThread();
	
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
		SpMap.insertSP(Thread.currentThread().hashCode(), 1);

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
