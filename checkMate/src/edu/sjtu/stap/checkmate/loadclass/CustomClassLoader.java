package edu.sjtu.stap.checkmate.loadclass;

/**
 * Custom ClassLoader
 * 
 * @author Yilei
 *
 */
public class CustomClassLoader extends ClassLoader {
	
    public CustomClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println("Loading class :" + name);
        return super.loadClass(name);
    }

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		Class c = super.loadClass(name, resolve);
		System.out.println(c.getName());
		return c;
	}

}
