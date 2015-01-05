package edu.sjtu.stap.checkmate.instrument;

import java.util.List;
import java.util.Map;

import edu.sjtu.stap.checkmate.framework.instrument.Constants;
import edu.sjtu.stap.checkmate.framework.instrument.MethodInfor;

public class CheckMateModifier extends ClassModifier {

	private String qualifiedClassName;
	
	public CheckMateModifier(String className){
		initMvFactory();
		qualifiedClassName=className;
	}
	
	@Override
	protected Map<String, Object> specificConfig() {
		Map<String, Object> config = basicConfig(qualifiedClassName);
		config.put(Constants.MIX_MODE, true);
		//main method applies a different rule from other methods.
		config.put(Constants.METHOD_NAME, "main");
		config.put(Constants.METHOD_DESC, "([Ljava/lang/String;)V");
		config.put(Constants.OUTPUT_DIRECTORY, ".");
		config.put(Constants.OUTPUT_FILENAME, qualifiedClassName+".class");
		return config;
	}

	@Override
	protected List<MethodInfor> addMethods2Transform() {
		//Should all methods be instrumented
		return null;
	}

	@Override
	protected void initMvFactory() {
		mvfactory=new CheckmateMVFactory();
		
	}

}
