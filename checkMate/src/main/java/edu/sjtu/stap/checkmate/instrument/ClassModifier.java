package edu.sjtu.stap.checkmate.instrument;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.illinois.jacontebe.asm.Constants;
import edu.illinois.jacontebe.asm.MethodInfor;
import edu.illinois.jacontebe.asm.ModifyDriver;
import edu.illinois.jacontebe.asm.MvFactory;

public abstract class ClassModifier {
	
	protected MvFactory mvfactory;
	
	/**
	 * Instrument one class and store the manipulated output to <code>OUPUT_BASE</code> directory. 
	 * @throws IOException
	 */
	public void modifyClass2File() throws IOException {
		Map<String, Object> properties = specificConfig();
		ModifyDriver.modify2File(properties, mvfactory);
	}
	
	/**
	 * Instrument one class and return the modified class as byte arrays. 
	 * @return
	 * @throws IOException
	 */
	public byte[] modifyClass() throws IOException{
		Map<String, Object> properties = specificConfig();
		return ModifyDriver.doModify(properties, mvfactory);
	}
	
	protected Map<String, Object> basicConfig(String qualifiedClassName){
		Map<String, Object> properties = new HashMap<String, Object>();
		List<MethodInfor> mis=addMethods2Transform();
		properties.put(Constants.QUALIFIED_CLASS_NAME,qualifiedClassName);
		properties.put(Constants.METHOD_INFOR_LIST, mis);
		return properties;
	}
	
	protected abstract Map<String, Object> specificConfig();
	protected abstract List<MethodInfor> addMethods2Transform();
	protected abstract void initMvFactory();
}
