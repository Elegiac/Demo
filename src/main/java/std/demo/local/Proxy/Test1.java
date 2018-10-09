package std.demo.local.Proxy;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import std.demo.SystemUtils;
import sun.misc.ProxyGenerator;

//https://www.ibm.com/developerworks/cn/java/j-lo-proxy1/
//http://www.cnblogs.com/flyoung2008/archive/2013/08/11/3251148.html
public class Test1 {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		//createProxyClassFile();

		//System.exit(0);

		// 方法 1: 该方法用于获取指定代理对象所关联的调用处理器
		// static InvocationHandler getInvocationHandler(Object proxy)

		// 方法 2：该方法用于获取关联于指定类装载器和一组接口的动态代理类的类对象
		// static Class getProxyClass(ClassLoader loader, Class[] interfaces)

		// 方法 3：该方法用于判断指定类对象是否是一个动态代理类
		// static boolean isProxyClass(Class cl)

		// 方法 4：该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例
		// static Object newProxyInstance(ClassLoader loader, Class[] interfaces,
		// InvocationHandler h)

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();

		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println(method.getDeclaringClass() + " " + method);

				return null;
			}
		};
		Object proxy = Proxy.newProxyInstance(classLoader,
				new Class[] { TestInterface.class, TestInterface2.class, TestInterface3.class }, handler);

		// 继承了java.lang.reflect.Proxy类
		// System.err.println(proxy.getClass().getSuperclass());
		// 实现了接口TestInterface TestInterface2
		// System.err.println(Arrays.toString(proxy.getClass().getInterfaces()));

		System.err.println(proxy.getClass().getName());

		System.out.println(((TestInterface) proxy).test1());
		System.out.println(((TestInterface2) proxy).test2());
		System.out.println(((TestInterface3) proxy).test3());
	}

	private interface TestInterface3 {
		String test3();
	}

	public static void createProxyClassFile() {
		String name = "ProxySubject";
		String path = SystemUtils.DESKTOP.getAbsolutePath() + File.separator;

		byte[] data = ProxyGenerator.generateProxyClass(name,
				new Class[] { TestInterface.class, TestInterface2.class, TestInterface3.class });
		try {
			FileOutputStream out = new FileOutputStream(path + name + ".class");
			out.write(data);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
