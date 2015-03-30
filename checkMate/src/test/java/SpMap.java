import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class SpMap {
	public static Map<Integer, Integer> ThreadSequenceMap = new ConcurrentHashMap<>();
	public static int SP = 0;
	public static void checkThread() {
		if (ThreadSequenceMap.containsKey(Thread.currentThread()
				.hashCode()))
			SP = ThreadSequenceMap.get(Thread.currentThread()
					.hashCode());
	}
	public static void insertSP(int threadHashCode, int sp) {
		ThreadSequenceMap.put(threadHashCode, sp);		
	}

}
