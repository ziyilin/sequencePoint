package edu.sjtu.stap.checkmate.loadclass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Hashtable;

import edu.sjtu.stap.checkmate.type.InstrumentInfo;

/**
 * Custom ClassLoader
 * 
 * @author Yilei
 *
 */
public class CustomClassLoader extends ClassLoader {
	
	private static final Hashtable<String, InstrumentInfo> candidateClassesPool = ClassLoaderHelper
			.getInstrumentCandidates();


	public CustomClassLoader(ClassLoader parent) {
		super(parent);
	}

	@Override
	public synchronized Class<?> loadClass(String name)
			throws ClassNotFoundException {
		System.out.println("Try load class: " + name);
		// First, check if the class has already been loaded
		Class<?> clazz = findLoadedClass(name);

		if (clazz != null)
			return clazz;

		// Try loading class from classpath
		clazz = loadClassFromClasspath(name);
		if (clazz != null) {
			System.out.println("Succeed loading Class " + name
					+ " from classpath.");
			return clazz;
		}

		// Load class from jar
		// clazz = loadClassFromJar(name);
		if (clazz != null) {
			// Define a new class with instrumentation.
		}
		return super.loadClass(name);
	}

	/**
	 * Manually load class from classpath
	 * 
	 * @param name
	 * @return
	 */
	private Class loadClassFromClasspath(String name) {
		// for (String s : classpath) {
		// System.out.println("Classpath: " + s);
		// }

		String slashName = name.replace(".", File.separator);
		String fullQualifiedName = File.separator + slashName + ".class";

		try {
			InputStream is = getResourceAsStream(fullQualifiedName);
			if (is != null) {
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				int data;
				data = is.read();
				while (data != -1) {
					buffer.write(data);
					data = is.read();
				}
				is.close();
				byte[] classData = buffer.toByteArray();

				// Check if the class need to be instrumented, if so, do the instrumentation.
				InstrumentInfo instrumentInfo = candidateClassesPool.get(name);
				if (instrumentInfo != null) {
					System.out.println("Need Instrumation: " + name);

					classData = ClassLoaderHelper.lineBasedInstrumentation(
							classData, instrumentInfo.getLines());
				}

				return defineClass(name, classData, 0, classData.length);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return null;
	}

}
