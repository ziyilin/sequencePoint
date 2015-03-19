package edu.sjtu.stap.squencepoint.instrumentation.methodbased;

import java.util.Hashtable;
import java.util.Properties;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import edu.sjtu.stap.checkmate.framework.instrument.InstrumentInfo;
import edu.sjtu.stap.checkmate.framework.instrument.Instrumentor;

public class LineBasedInstrumentor extends Instrumentor {

	public LineBasedInstrumentor(Properties p) {
		super(p);
		initiateData();
	}

	private Hashtable<String, InstrumentInfo> candidates;

	@Override
	protected boolean needInstru(String className) {
		currentClass = className;
		if(candidates==null){
			initiateData();
		}
		return candidates.get(className) != null;
	}

	final protected void initiateData() {
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
	protected byte[] doInstrument(byte[] classData) {

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
		return classData;
	}
}
