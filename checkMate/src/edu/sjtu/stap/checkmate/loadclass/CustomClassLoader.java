package edu.sjtu.stap.checkmate.loadclass;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Custom ClassLoader
 * 
 * @author Yilei
 *
 */
public class CustomClassLoader extends ClassLoader {
	// private final ClassLoader parent;

	public CustomClassLoader(ClassLoader classLoader) {
		super(classLoader);
		// parent = this.getParent()
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		System.out.println("Loading class :" + name);
		Class clazz = this.findLoadedClass(name);
		if (clazz == null) { // Check if the class has already been loaded.
			byte[] classData = getClassData(name);
			if (classData == null)
				throw new ClassNotFoundException();
			clazz = defineClass(name, classData, 0, classData.length); // Return
																		// the
																		// class
																		// instance;
		}
		return clazz;
	}

	private byte[] getClassData(String name) {

		return null;
	}

}
