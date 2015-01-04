package edu.sjtu.stap.checkmate.framework.loading;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import edu.sjtu.stap.checkmate.framework.instrument.Instrumentor;

public class ClassLoaderHelper {

	private static Properties prop;
	private static Instrumentor instru;

	public static byte[] instrument(String className, byte[] classData) {
		if (prop == null) {
			getInstrumentationSettings();
		}
		if (instru == null) {
			generateInstrumentor();
		}
		if (instru != null) {
			classData=instru.instrument(className, classData);
		} else {
			System.err
					.println("Fail to initiate Instrumentor instance, program shall run without instrumenting");
		}
		return classData;
	}

	private static void generateInstrumentor() {
		String instumentorClass = prop.getProperty("instumentorClass");
		try {
			Class<Instrumentor> clazz = (Class<Instrumentor>) Class
					.forName(instumentorClass);
			Constructor<Instrumentor> con = clazz
					.getConstructor(Properties.class);
			instru = con.newInstance(prop);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private static void getInstrumentationSettings() {
		try {

			prop = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream stream = loader
					.getResourceAsStream("instrument.properties");

			prop.load(stream);
		} catch (IOException e) {
			System.err
					.println("Could not load instrument.properties file, program shall exit");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
