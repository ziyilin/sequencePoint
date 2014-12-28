package edu.sjtu.stap.checkmate.loadclass;

import java.util.Hashtable;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import edu.sjtu.stap.checkmate.instrumentation.LineBasedCVFactory;
import edu.sjtu.stap.checkmate.instrumentation.LineBasedMVFactory;
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
		candidates.put("edu.sjtu.stap.checkmate.loadclass.test.DemoThread1",
				new InstrumentInfo(
						"edu.sjtu.stap.checkmate.loadclass.test.DemoThread1",
						1, 11, "1"));

		// Name=DemoThread2, SeqNumber = 2, Line = 11, tag = "2";
		candidates.put("edu.sjtu.stap.checkmate.loadclass.test.DemoThread2",
				new InstrumentInfo(
						"edu.sjtu.stap.checkmate.loadclass.test.DemoThread2",
						1, 13, "2"));

		// In Jar, Name=JarTest, SeqNumber = 3, Line = 9, tag = "3";
		candidates.put("edu.sjtu.stap.monday.utils.JarTest",
				new InstrumentInfo("edu.sjtu.stap.monday.utils.JarTest", 3, 9,
						"3"));

	}

	public static byte[] lineBasedInstrumentation(byte[] clazz, int line) {
		ClassReader cr = new ClassReader(clazz);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassVisitor cv = new LineBasedCVFactory(cw, new LineBasedMVFactory(
				line));
		cr.accept(cv, 0);
		System.out.println("Instrumenting completed.");
		return cw.toByteArray();
	}
}
