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
				SpMap.checkThread(2);
				modifier = t.getName();
			}
		});
		// Thread t, sp = 2;
		SpMap.insertSP(2, t.hashCode());

	}

	public void startThread() {
		t.setName("T");

		t.start();

		modifier = getClass().getSimpleName();
		
		SpMap.checkThread(1);
	
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Last Modifier is " + modifier);
	}

	public static void main(String[] args) {
		// main thread, sp = 1;
		SpMap.insertSP(1, Thread.currentThread().hashCode());

		Thread.currentThread().setName("OneThreadDemo");

		new Thread() {
			@Override
			public void run() {
				SpMap.insertSP(3, Thread.currentThread().hashCode());
				SpMap.checkThread(3);

				System.out.println("Test Anonymous Thread.");
			}
		}.start();

		OneThreadDemo demo = new OneThreadDemo();
		demo.startThread();
	}
}
