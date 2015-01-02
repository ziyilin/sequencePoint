package edu.sjtu.stap.checkmate.instrument;

import java.util.List;
import java.util.Map;

import edu.illinois.jacontebe.asm.MethodInfor;

public class CheckMateModifier extends ClassModifier {

	private String qualifiedClassName;
	
	public CheckMateModifier(String className){
		initMvFactory();
		qualifiedClassName=className;
	}
	
	@Override
	protected Map<String, Object> specificConfig() {
		return basicConfig(qualifiedClassName);
	}

	@Override
	protected List<MethodInfor> addMethods2Transform() {
		//Should all methods be instrumented
		return null;
	}

	@Override
	protected void initMvFactory() {
		// TODO Auto-generated method stub
		
	}

}
