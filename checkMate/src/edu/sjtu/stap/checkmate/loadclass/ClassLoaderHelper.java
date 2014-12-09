package edu.sjtu.stap.checkmate.loadclass;

import java.util.Hashtable;

import edu.sjtu.stap.checkmate.type.InstrumentInfo;

public class ClassLoaderHelper {
	public static Hashtable<String, InstrumentInfo> getInstrumentCandidates() {
		Hashtable<String, InstrumentInfo> candidates = new Hashtable<String, InstrumentInfo>();
		// Load information from files or use some other way
		generateTestData(candidates);
		return candidates;
	}

	private static void generateTestData(
			Hashtable<String, InstrumentInfo> candidates) {
		// Name=DemoThread1, SeqNumber = 1, Line = 11, tag = "1";
		candidates.put("edu.sjtu.stap.checkmate.loadclass.test.DemoThread1", new InstrumentInfo("edu.sjtu.stap.checkmate.loadclass.test.DemoThread1", 1, 11, "1"));
		
		// Name=DemoThread2, SeqNumber = 2, Line = 11, tag = "2";
		candidates.put("edu.sjtu.stap.checkmate.loadclass.test.DemoThread2", new InstrumentInfo("edu.sjtu.stap.checkmate.loadclass.test.DemoThread2", 1, 11, "2"));
		
	}
}
