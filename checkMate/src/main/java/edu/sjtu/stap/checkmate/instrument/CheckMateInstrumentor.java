package edu.sjtu.stap.checkmate.instrument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import edu.sjtu.stap.checkmate.framework.instrument.Instrumentor;

public class CheckMateInstrumentor extends Instrumentor {

	private List<String> assoicatedClasses;
	
	public CheckMateInstrumentor(Properties p) {
		super(p);
		initiateData();
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
		CheckMateModifier modifier=new CheckMateModifier(currentClass,assoicatedClasses);
		try {
			classData=modifier.modifyClass();
			
			//modifier.modifyClass2File();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Fail to instrument, program shall run with original codes");
		}
		return classData;
	}

	@Override
	final protected void initiateData() {
		//get associated classes which are separated by comma if more than one in properties file.
		String[] strArr=properties.getProperty("annotation.associate.class").split(",");
		assoicatedClasses=new ArrayList<String>();
		assoicatedClasses=Arrays.asList(strArr);
	}

}
