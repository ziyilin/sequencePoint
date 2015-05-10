package edu.sjtu.stap.checkmate.framework.instrument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * You should extend this class to define your own instrumentor which describes
 * how to use properties file (instrument.properties) to pass application level
 * parameters to your instrumentor and manipulate the classes.<br>
 * You must specify your instrumentor class' name with <b>"instumentorClass"</b>
 * property which is the only mandatory property at system level, so the system
 * shall know which intrumentor to employ. And then, you are free to define the
 * properties file at will.
 *     
 * @author Ziyi
 *
 */
public abstract class Instrumentor {

	//This is "instrument.properties" file.
	protected Properties properties;
	protected String currentClass;
	
	public Instrumentor(Properties p){
		this.properties=p;
	}
	
	abstract protected boolean needInstru(String className);
	abstract protected byte[] doInstrument(byte[] classData);
	
	/**
	 * Initiate data read load from properties file. 
	 */
	abstract protected void initiateData();
	
	public byte[] instrument(String className, byte[] classData){
		if(needInstru(className)){
			return doInstrument(classData);
		}else{
			return classData;
		}
	}
	
	public void setProperties(Properties prop) {
		properties=prop;
	}

	public static void output2File(byte[] output, String fileName) throws IOException {
		String outputDirectory = System.getProperty("user.dir") + System.getProperty("file.separator") + "target" + System.getProperty("file.separator");
		File dir = new File(outputDirectory);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(outputDirectory
				+ fileName + ".class");
		fos.write(output);
		fos.close();
	}
}
