import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Yilei
 * 
 */
public class SpMap {
	// Maps sequence point to thread hashcod
	public static Map<Integer, Integer> ThreadSequenceMap = new ConcurrentHashMap<>();
	
	//
	public static int SP = 0;

	// Check the sp whether belongs to current thread.
	public static void checkThread(int sp) {
		int value = ThreadSequenceMap.get(sp);
		
		if ( value == Thread.currentThread().hashCode())
			SP = sp;
	}

	// Insert Pair<SequencePoint, ThreadHashcode>
	public static void insertSP(int sp, int threadHashCode) {
		ThreadSequenceMap.put(sp, threadHashCode);
	}

}
