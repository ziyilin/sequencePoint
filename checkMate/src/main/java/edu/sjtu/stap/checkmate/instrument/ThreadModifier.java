package edu.sjtu.stap.checkmate.instrument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.illinois.jacontebe.asm.Constants;
import edu.illinois.jacontebe.asm.MethodInfor;

/**
 * Instrument java.lang.Thread class and store instrumented class into
 * <i>boot_classes</i> directory. Specify which methods to instrument in
 * {@link #addMethods2Transform(List)}
 * 
 * @author Ziyi
 *
 */
public class ThreadModifier extends ClassModifier{
	
	private static final String OUTPUT_BASE="boot_classes";
	private String bootPath;
	
	public static void main(String[] args) throws IOException {
		ThreadModifier modifier=new ThreadModifier();
		modifier.modifyClass2File();
	}

	/**
	 * Store instrumented classes into the default output directory: [project_home]/boot_classes/
	 */
	public ThreadModifier(){
		this.bootPath=getDefaultBootPath();
		initMvFactory();
	}
	
	/**
	 * 
	 * @param bootPath path where instrumented classes are located. End with "/". 
	 */
	public ThreadModifier(String bootPath){
		this.bootPath=bootPath;
		initMvFactory();
	}
	
	final protected void initMvFactory(){
		mvfactory=new ThreadMVFactory();
	}
	
	private String getDefaultBootPath() {
		return System.getProperty("user.dir")+File.separator+OUTPUT_BASE+File.separator;
	}
	
	protected Map<String, Object> specificConfig() {
		String qualifiedClassName = "java.lang.Thread";
		Map<String, Object> properties = basicConfig(qualifiedClassName);
		String classPath=parseClassPath(qualifiedClassName);
		String outputDirectory = bootPath + classPath;
		String classFile=parseClassFile(qualifiedClassName);
		String outputFile = classFile;
		properties.put(Constants.OUTPUT_FILENAME, outputFile);
		properties.put(Constants.OUTPUT_DIRECTORY, outputDirectory);
		return properties;
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
			return qualifiedClassName.substring(0, lastDot).replace('.', File.separatorChar);
		}
		return null;
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
	
	protected List<MethodInfor> addMethods2Transform() {
		List<MethodInfor> methods2Tran=new ArrayList<MethodInfor>();
		methods2Tran.add(new MethodInfor("start", "()v"));
		methods2Tran.add(new MethodInfor("join", "()v"));
		return methods2Tran;
	}
}
