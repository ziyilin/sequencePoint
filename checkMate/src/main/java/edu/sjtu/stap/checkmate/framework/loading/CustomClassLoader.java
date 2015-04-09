package edu.sjtu.stap.checkmate.framework.loading;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Custom ClassLoader
 * 
 * @author Yilei
 * 
 */
public class CustomClassLoader extends ClassLoader {
	private static final String[] classpath = System.getProperty(
			"java.class.path").split(";");

	public CustomClassLoader(ClassLoader parent) {
		super(parent);
	}

	@Override
	public synchronized Class<?> loadClass(String name)
			throws ClassNotFoundException {
		System.out.println("Try to load class: " + name);
		// First, check if the class has already been loaded
		Class<?> clazz = findLoadedClass(name);

		if (clazz != null)
			return clazz;

		// Try loading class
		clazz = loadClassFromClasspath(name);
		if (clazz != null) {
			// System.out.println("Succeed loading Class " + name+
			// " from classpath.");
			return clazz;
		}

		// Load class from jar
		clazz = loadClassFromJar(name);
		if (clazz != null) {
			// Define a new class with instrumentation.
		}
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

					String slashName = name.replace(".", File.separator)
							+ ".class";
					JarEntry entry = jarFile.getJarEntry(slashName);
					if (entry != null) {
						InputStream is = jarFile.getInputStream(entry);
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

							// Check if the class need to be instrumented, if
							// so, do the instrumentation.
							classData = ClassLoaderHelper.instrument(name,
									classData);
							return defineClass(name, classData, 0,
									classData.length);
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
	private Class<?> loadClassFromClasspath(String name) {
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

				// Check if the class need to be instrumented, if so, do the
				// instrumentation.
				classData = ClassLoaderHelper.instrument(name, classData);
				return defineClass(name, classData, 0, classData.length);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
