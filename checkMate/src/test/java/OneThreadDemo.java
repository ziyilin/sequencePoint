/**
 * 
 * @author Yilei
 *
 */
public class OneThreadDemo {
	private Thread t;
	private String modifier;

	public OneThreadDemo() {
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				modifier = t.getName().startsWith("Thread-") == true ? "t"
						: t.getName();
				if ( t.getName().endsWith("s") && t.getName().startsWith("s")) {
					modifier=null;
				}
				// Insert Sequence Point Here,
				// Sp@Site@Thread(Debug_Name)=1@(11,17)@t
			}
		});
	}

	public void startThread() {
		t.setName("T");
		t.start();
		// Insert befor t.start();
		/*if (t.getName().startsWith("Thread-") )
			t.setName("Real_Name_t");
		*/
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
		//Thread.currentThread().setName("OneThreadDemo");
		Thread t1=Thread.currentThread();
		System.out.println(t1.getName());
		Thread t=new Thread(){
			public void run(){
				
			}
		};
		t.start();
		OneThreadDemo demo = new OneThreadDemo();
		demo.startThread();
	}
}
