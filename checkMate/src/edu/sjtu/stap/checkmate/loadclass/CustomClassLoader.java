package edu.sjtu.stap.checkmate.loadclass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import edu.sjtu.stap.checkmate.type.InstrumentInfo;

/**
 * Custom ClassLoader
 * 
 * @author Yilei
 *
 */
public class CustomClassLoader extends ClassLoader {
	private static final String[] classpath = System.getProperty(
			"java.class.path").split(";");

	private static final Hashtable<String, InstrumentInfo> candidateInstrumentationPool = ClassLoaderHelper
			.getInstrumentCandidates();

	private final ClassLoader parent;

	public CustomClassLoader(ClassLoader parent) {
		super(parent);
		this.parent = parent;
		// for (String s : classpath) {
		// System.out.println("Classpath: " + s);
		// }
	}

	@Override
	public synchronized Class<?> loadClass(String name)
			throws ClassNotFoundException {
		System.out.println("Try load class: " + name);
		// First, check if the class has already been loaded
		Class clazz = findLoadedClass(name);

		if (clazz != null)
			return clazz;

		// Try loading class from classpath
		clazz = loadClassFromClasspath(name);
		if (clazz != null) {
			// System.out.println("Succeed loading Class " + name
			// + " from classpath.");
			return clazz;
		}

		// Load class from jar
		clazz = loadClassFromJar(name);
		if (clazz != null) {
			// Define a new class with instrumentation.
		}
		// this.defineClass(name, b, off, len)
		return super.loadClass(name);
	}

	/**
	 * Manually load class from jar
	 * 
	 * @param name
	 * @return
	 */
	private Class loadClassFromJar(String name) {
		for (String s : classpath)
			if (s.endsWith(".jar")) {
				String jarUrl;
				try {
					jarUrl = URLDecoder.decode(s, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					// e.printStackTrace();
					return null;
				}
				File fileJar = new File(jarUrl);
				JarFile jarFile = null;
				try {
					jarFile = new JarFile(fileJar);
					
					// Replace "." with "/" instead of "\\".
					String slashName = name.replace(".", "/") + ".class";
					JarEntry entry = jarFile.getJarEntry(slashName);
					if (entry != null) {
						InputStream is = jarFile.getInputStream(entry);
						if (is != null) {
							byte[] classData = getClassDataByte(is);
							// Check if the class need to be instrumentated.
							InstrumentInfo instrumentInfo = candidateInstrumentationPool.get(name);
							if (instrumentInfo != null) {
								// System.out.println("Need Instrumation: " + name);
								classData = ClassLoaderHelper.lineBasedInstrumentation(
										classData, instrumentInfo.getLines());
							}
							return defineClass(name, classData, 0, classData.length);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					return null;
				} finally {
					try {
						jarFile.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}
				}

			}
		return null;
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

		String slashName = name.replace(".", "/");
		// String fullQualifiedName = s + "\\" + slashName + ".class";
		// Do NOT Need Full Path
		String fullQualifiedName = "\\" + slashName + ".class";

		InputStream is = getResourceAsStream(fullQualifiedName);
		if (is != null) {
			byte[] classData = getClassDataByte(is);
			// Check if the class need to be instrumentated.
			InstrumentInfo instrumentInfo = candidateInstrumentationPool.get(name);
			if (instrumentInfo != null) {
				// System.out.println("Need Instrumation: " + name);

				classData = ClassLoaderHelper.lineBasedInstrumentation(
						classData, instrumentInfo.getLines());
			}

			return defineClass(name, classData, 0, classData.length);
		}
		return null;
	}

	private byte[] getClassDataByte(InputStream is) {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int data;
		try {
			data = is.read();
			while (data != -1) {
				buffer.write(data);
				data = is.read();
			}
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}
		return buffer.toByteArray();
	}

}
