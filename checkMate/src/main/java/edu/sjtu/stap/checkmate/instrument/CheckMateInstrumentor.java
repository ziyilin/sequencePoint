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
		if(currentClass.startsWith("edu.sjtu.stap.checkmate")){
			return false;
		}
		return true;
	}

	@Override
	protected byte[] doInstrument(byte[] classData) {
		CheckMateModifier modifier=new CheckMateModifier(currentClass);
		try {
			classData=modifier.modifyClass();
		//	modifier.modifyClass2File();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to instrument, program shall run with original codes");
		}
		return classData;
	}

	@Override
	final protected void initiateData() {
		
	}

}
