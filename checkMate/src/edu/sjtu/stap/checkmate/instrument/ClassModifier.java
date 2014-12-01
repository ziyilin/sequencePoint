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
	protected static final String OUTPUT_BASE="boot_classes";
	protected String bootPath;
	protected MvFactory mvfactory;
	
	public void modifyClass() throws IOException {
		Map<String, Object> properties = specifyConfig();
		ModifyDriver.modify2File(properties, mvfactory);
	}
	
	protected String getDefaultBootPath() {
		return System.getProperty("user.dir")+"/"+OUTPUT_BASE+"/";
	}
	
	protected Map<String, Object> basicConfig(String qualifiedClassName){
		String classPath=parseClassPath(qualifiedClassName);
		String classFile=parseClassFile(qualifiedClassName);
		Map<String, Object> properties = new HashMap<String, Object>();
		List<MethodInfor> mis=addMethods2Transform();
		String outputDirectory = bootPath + classPath;
		String outputFile = classFile;
		properties.put(Constants.QUALIFIED_CLASS_NAME,qualifiedClassName);
		properties.put(Constants.METHOD_INFOR_LIST, mis);
		properties.put(Constants.OUTPUT_DIRECTORY, outputDirectory);
		properties.put(Constants.OUTPUT_FILENAME, outputFile);
		return properties;
	}
	

	/**
	 * Get class file's name from the given qualified class name.
	 * For example, <code>java.lang.Thread</code> will return <code>Thread.class</code>.
	 * @param qualifiedClassName
	 * @return null if could not parse correctly.
	 */
	private String parseClassFile(String qualifiedClassName) {
		int lastDot=qualifiedClassName.lastIndexOf(".");
		if(lastDot>0){
			return qualifiedClassName.substring(lastDot)+".class";
		}
		return null;
	}

	/**
	 * Convert class's package name to the form of directory path.
	 * For example, <code>java.lang.Thread</code> will return <code>java/lang/</code>.
	 * @param qualifiedClassName
	 * @return null if could not parse correctly.
	 */
	private String parseClassPath(String qualifiedClassName) {
		int lastDot=qualifiedClassName.lastIndexOf(".");
		if(lastDot>0){
			return qualifiedClassName.substring(0, lastDot).replace('.', '/');
		}
		return null;
	}
	
	protected abstract Map<String, Object> specifyConfig();
	protected abstract List<MethodInfor> addMethods2Transform();
}
