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

	/*
	 * Set parent classloader to bootstrap. 
	 */
	public CustomClassLoader() {
		super(null);
	}
	
	public CustomClassLoader(ClassLoader parent) {
		super(parent);
	}

	@Override
	public synchronized Class<?> loadClass(String name) throws ClassNotFoundException {
		System.out.println("Loading class :" + name + "; Count = " + count++);
		
		// First, check if the class has already been loaded
		Class clazz = findLoadedClass(name);
		
		if ( clazz == null ) {
			// try load from bootstrap 
		}
		if (clazz == null ) {
			// Define a new class with instrumentation.
		}
		return clazz;
	}


	private byte[] getClassData(String name) {

		return null;
	}

}
