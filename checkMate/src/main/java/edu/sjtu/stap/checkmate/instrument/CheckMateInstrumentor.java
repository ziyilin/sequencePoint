package edu.sjtu.stap.checkmate.instrument;

import java.io.IOException;
import java.util.Properties;

import edu.sjtu.stap.checkmate.framework.instrument.Instrumentor;

public class CheckMateInstrumentor extends Instrumentor {

	public CheckMateInstrumentor(Properties p) {
		super(p);
	}

	@Override
	protected boolean needInstru(String className) {
		currentClass=className;
		return true;
	}

	@Override
	protected void doInstrument(byte[] classData) {
		CheckMateModifier modifier=new CheckMateModifier(currentClass);
		try {
			classData=modifier.mofigyClass();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to instrument, program shall run with original codes");
		}
	}

	@Override
	final protected void initiateData() {
		
	}

}
