package edu.sjtu.stap.squencepoint.instrumentation.linebased;

import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;

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

	// class name -> line number -> (column number, sp, insType, insInstance)
	private Map<String, Map<Integer, SortedSet<InstrumentInfo>>> candidates;

	@Override
	protected boolean needInstru(String className) {
		currentClass = className;
		if (candidates == null) {
			initiateData();
		}
		return candidates.get(className) != null;
	}

	final protected void initiateData() {
		candidates = new ConcurrentHashMap<>();
		String prefix = "candidates.";
		int number = Integer.parseInt(properties.getProperty(prefix + "num"));
		for (int classIndex = 0; classIndex < number; classIndex++) {
			String name = properties.getProperty(prefix + classIndex + ".name");
			int insNum = Integer.parseInt(properties.getProperty(prefix
					+ classIndex + ".num"));
			for (int insIndex = 0; insIndex < insNum; insIndex++) {
				String site = properties.getProperty(prefix + classIndex + "."
						+ insIndex + ".site");
				int line = Integer
						.parseInt(site.substring(0, site.indexOf(',')));
				int column = Integer
						.parseInt(site.substring(site.indexOf(',') + 1));
				String type = properties.getProperty(prefix + classIndex + "."
						+ insIndex + ".type");
				int sp = Integer.parseInt(properties.getProperty(prefix
						+ classIndex + "." + insIndex + ".sp"));
				String instance = properties.getProperty(prefix + classIndex
						+ "." + insIndex + ".instance");

				// candidates.put(name,
				// new InstrumentInfo(name,id,lineNumber,tag));

			}
		}
	}

	@Override
	protected byte[] doInstrument(byte[] classData) {

		ClassReader cr = new ClassReader(classData);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		try {
			ClassVisitor cv = new LineBasedCVFactory(cw,
					new LineBasedMVFactory(candidates.get(currentClass)));
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
