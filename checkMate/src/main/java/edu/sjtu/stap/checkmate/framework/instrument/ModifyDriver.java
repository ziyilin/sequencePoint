package edu.sjtu.stap.checkmate.framework.instrument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * 
 * @author Ziyi Lin
 * 
 */
public class ModifyDriver {

	/**
	 * Transform a specified method of a class, and return the transformed class
	 * in byte array.<br>
	 * These properties are mandatory to put into the properties:
	 * <ul>
	 * <li><b>Constants.QUALIFIED_CLASS_NAME</b>, String : qualified class name,
	 * for example, java.lang.String</li>
	 * <li><b>Constants.METHOD_INFOR_LIST</b>, List&lt;MethodInfor&gt;: a list
	 * of method names and method descriptions. This property is for the
	 * situation of more than one method to transform</li>
	 * <li><b>Constants.METHOD_NAME</b>, String : name of the method to
	 * transform. Only available for one method to transform.</li>
	 * <li><b>Constants.METHOD_DESC</b>, String : method description, for
	 * example, (I)V. Only available for one method to transform.</li>
	 * </ul>
	 * When there is only one method to modify, either use
	 * <b>Constants.METHOD_INFOR_LIST</b> or the combination of
	 * <b>Constants.METHOD_NAME</b> and <b>Constants.METHOD_DESC</b> is fine,
	 * but not both. <br>
	 * On the other hand, when <b>none</b> of these three properties is set,
	 * <b>all</b> methods will be instrumented.
	 * 
	 * @param properties
	 * @param mvFactory
	 * @return
	 * @throws IOException
	 */
	public static byte[] doModify(Map<String, Object> properties,
			MvFactory mvFactory) throws IOException {
		String className = (String) properties
				.get(Constants.QUALIFIED_CLASS_NAME);
		List<MethodInfor> methodInforList = (List<MethodInfor>) properties
				.get(Constants.METHOD_INFOR_LIST);
		// User can put method name and description directly into property
		// instead of using List<MethodInfor> when there is only one method
		// need to transform.
		// On the other hand, when method name, description and
		// List<MethodInfor> are all null,
		// we will instrument all methods.
		if (methodInforList == null) {
			String methodName = (String) properties.get(Constants.METHOD_NAME);
			String methodDesc = (String) properties.get(Constants.METHOD_DESC);
			if (methodName != null || methodDesc != null) {
				MethodInfor mi = new MethodInfor(methodName, methodDesc);
				methodInforList = new ArrayList<MethodInfor>();
				methodInforList.add(mi);
			}
		}
		boolean mix = properties.get(Constants.MIX_MODE) == null ? false
				: (Boolean) properties.get(Constants.MIX_MODE);
		System.out.println("Instrumenting class " + className + "...");
		ClassReader cr = new ClassReader(className);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassVisitor cv = new TransformMethodAdapter(cw, methodInforList,
				mvFactory, mix);
		cr.accept(cv, 0);
		System.out.println("Instrumenting completed.");
		return cw.toByteArray();
	}

	/**
	 * Transform a specified method of a class, and store the transformed class
	 * to disk at specified location.<br>
	 * These properties are mandatory to put into the properties:
	 * <ul>
	 * <li><b>Constants.OUTPUT_DIRECTORY</b>, String : Absolute path of output
	 * directory. End with "/".</li>
	 * <li><b>Constants.OUTPUT_FILENAME</b>, String : output file name.</li>
	 * <li><b>Constants.QUALIFIED_CLASS_NAME</b>, String : qualified class name,
	 * for example, java.lang.String</li>
	 * <li><b>Constants.METHOD_INFOR_LIST</b>, List&lt;MethodInfor&gt;: a list
	 * of method names and method descriptions. This property is for the
	 * situation of more than one method to transform</li>
	 * <li><b>Constants.METHOD_NAME</b>, String : name of the method to
	 * transform. Only available for one method to transform.</li>
	 * <li><b>Constants.METHOD_DESC</b>, String : method description, for
	 * example, (I)V. Only available for one method to transform.</li>
	 * </ul>
	 * When there is only one method to modify, either use
	 * <b>Constants.METHOD_INFOR_LIST</b> or the combination of
	 * <b>Constants.METHOD_NAME</b> and <b>Constants.METHOD_DESC</b> is fine,
	 * but not both.<br>
	 * On the other hand, when <b>none</b> of these three properties is set,
	 * <b>all</b> methods will be instrumented.
	 * 
	 * @param properties
	 * @param mvFactory
	 * @throws IOException
	 */
	public static void modify2File(Map<String, Object> properties,
			MvFactory mvFactory) throws IOException {
		String outputDirectory = (String) properties
				.get(Constants.OUTPUT_DIRECTORY);
		String outputFile = (String) properties.get(Constants.OUTPUT_FILENAME);
		File dir = new File(outputDirectory);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(outputDirectory
				+ outputFile);
		byte[] output = doModify(properties, mvFactory);
		fos.write(output);
		fos.close();
	}
}
