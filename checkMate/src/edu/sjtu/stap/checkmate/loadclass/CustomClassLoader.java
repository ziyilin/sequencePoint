package edu.sjtu.stap.checkmate.loadclass;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Custom ClassLoader
 * 
 * @author Yilei
 *
 */
public class CustomClassLoader extends ClassLoader {
	private String[] classpath;

	private final ClassLoader parent;

	// private final ClassLoader parent;
	private static int count = 0;

	public CustomClassLoader(ClassLoader parent) {
		super(parent);
		this.parent = parent;
		classpath = System.getProperty("java.class.path").split(";");
	}

	@Override
	public synchronized Class<?> loadClass(String name)
			throws ClassNotFoundException {
		// First, check if the class has already been loaded
		Class clazz = findLoadedClass(name);

		if (clazz != null)
			return clazz;

		// Load class from classpath
		if ( name.contains("edu"))
			clazz = loadClassFromClasspath(name);
		if (clazz != null) {
			System.out.println("Load Class " + name + " from classpath.");
			return clazz;
		}

		// Load class from jar
		// clazz = loadClassFromJar(name);
		if (clazz != null) {
			// Define a new class with instrumentation.
		}
		// this.defineClass(name, b, off, len)
		return super.loadClass(name);
	}

	/**
	 * Manually load class from classpath
	 * 
	 * @param name
	 * @return
	 */
	private Class loadClassFromClasspath(String name) {
//		for (String s : classpath) {
//			System.out.println("Classpath: " + s);
//		}

		String slashName = name.replace(".", "\\");
		//for (String s : classpath) {
			// String fullQualifiedName = s + "\\" + slashName + ".class";
			// Need NOT Full Path
			String fullQualifiedName = "\\" + slashName + ".class";

			System.out.println("Load Class: " + fullQualifiedName);

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
					return defineClass(name, classData, 0, classData.length);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}

		//}
		return null;
	}

	private byte[] getClassData(String name) {

		return null;
	}

}
