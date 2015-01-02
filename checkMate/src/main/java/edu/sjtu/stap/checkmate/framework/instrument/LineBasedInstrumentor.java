package edu.sjtu.stap.checkmate.framework.instrument;

import java.util.Hashtable;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import edu.sjtu.stap.checkmate.instrumentation.LineBasedCVFactory;
import edu.sjtu.stap.checkmate.instrumentation.LineBasedMVFactory;

public class LineBasedInstrumentor extends Instrumentor {

	private Hashtable<String, InstrumentInfo> candidates;
	private String currentClass;

	@Override
	protected boolean needInstru(String className) {
		currentClass = className;
		if(candidates==null){
			initiateData();
		}
		return candidates.get(className) != null;
	}

	private void initiateData() {
		candidates= new Hashtable<String, InstrumentInfo>();
		String prefix="candidates.";
		int number=Integer.parseInt(properties.getProperty(prefix+"num"));
		for(int i=0;i<number;i++){
			String name=properties.getProperty(prefix+i+".name");
			int id=Integer.parseInt(properties.getProperty(prefix+i+".id"));
			int lineNumber=Integer.parseInt(properties.getProperty(prefix+i+".line"));
			String tag=properties.getProperty(prefix+i+".tag");
			candidates.put(name,
					new InstrumentInfo(name,id,lineNumber,tag));
		}
	}

	@Override
	protected void doInstrument(byte[] classData) {

		ClassReader cr = new ClassReader(classData);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		try {
			ClassVisitor cv = new LineBasedCVFactory(cw,
					new LineBasedMVFactory(candidates.get(currentClass)
							.getLines()));
			cr.accept(cv, 0);
			System.out.println("Instrumenting completed.");
			classData = cw.toByteArray();
		} catch (Exception e) {
			System.err.println("Fail to instrument, caused by:");
			e.printStackTrace();
		}
	}

	private void generateTestData() {
		// In Jar, Name=java.util.logging.Logger, SeqNumber = 4, Line = 495, tag
		// = "4";
		candidates.put("java.util.logging.Logger", new InstrumentInfo(
				"java.util.logging.Logger", 4, 495, "4"));
	}

}
