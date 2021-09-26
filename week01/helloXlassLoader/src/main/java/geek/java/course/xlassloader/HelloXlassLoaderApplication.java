package geek.java.course.xlassloader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloXlassLoaderApplication extends ClassLoader {

	/**
	 * class name
	 */
	final static String HELLO_CLASS_NAME = "Hello";

	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		HelloXlassLoaderApplication classLoader = new HelloXlassLoaderApplication();
		try {
			Class HelloClazz = classLoader.findClass(HELLO_CLASS_NAME);
			for (Method m : HelloClazz.getMethods()) {
				System.out.println(HelloClazz.getName() + "." + m.getName());
			}
			Object instance = HelloClazz.getDeclaredConstructor().newInstance();
			Method helloMethod = instance.getClass().getMethod("hello");
			helloMethod.invoke(instance);
		} catch (ClassNotFoundException | NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * find Hello class
	 * @param name Hello
	 * @return Hello.class
	 * @throws ClassNotFoundException
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try (InputStream inputStream =
					 this.getClass().getClassLoader().getResourceAsStream("Hello.xlass")) {
			int length = inputStream.available();
			byte[] helloXlassBytes = new byte[length];
			inputStream.read(helloXlassBytes);
			for (int i = 0; i < length; i++) {
				helloXlassBytes[i] = (byte) (255 - helloXlassBytes[i]);
			}
			return defineClass(HELLO_CLASS_NAME, helloXlassBytes, 0, length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new ClassNotFoundException();
	}
}
