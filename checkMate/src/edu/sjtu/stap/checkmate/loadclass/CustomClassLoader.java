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
	private static int count = 0;

	public CustomClassLoader(ClassLoader parent) {
		super(parent);
		// this.parent = parent;
	}

	// public CustomClassLoader() {
	// super(CustomClassLoader.class.getClassLoader());
	// }

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if (name.contains("Person"))
			System.out.println("TEST");
		System.out.println("Loading class :" + name + "; Count = " + count++);
		// return loadClass(name, false);

		Class clazz = findLoadedClass(name);
		if (clazz == null) {
			clazz = super.loadClass(name);
		}
		System.out.println(clazz.getName());
		return clazz;

	}

	// @Override
	// protected Class<?> loadClass(String name, boolean resolve)
	// throws ClassNotFoundException {
	// Class clazz = findLoadedClass(name);
	// if ( clazz == null ) {
	// clazz = super.loadClass(name, false);
	// }
	// System.out.println(clazz.getName());
	// return clazz;
	// }

	private byte[] getClassData(String name) {

		return null;
	}

}
