package std.demo;

import java.util.Arrays;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class frrefer {

	public static void main(String[] args) throws ScriptException,
			NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException {
		// TODO Auto-generated method stub
		ScriptEngineManager maneger = new ScriptEngineManager();

		ScriptEngine engine = maneger.getEngineByName("JavaScript");

		Invocable invoke = (Invocable) engine;
		//
		// Object result = engine.eval(
		// "function sum(a,b){"
		// + "return a+b;"
		// + "}"
		// );
		//
		// System.out.println(invoke.invokeFunction("sum", 3,4));

		String[] allChar = { "\\", "/", ":", "*", "?", "\"", "<", ">", "|" };

		List<String> chars = Arrays.asList(allChar);

		String s = "ws:df\"e\\";
		StringBuilder b = new StringBuilder(s);

		System.out.println(s);

		for (int i = 0; i < s.length(); i++) {
			if (chars.contains(String.valueOf(s.charAt(i)))) {
				b.replace(i, i, "");
			}
		}
		System.out.println(b);
		System.out.println(b.replace(1, 1, "a"));

		System.out.println(s.replaceAll(
				"(\\\\)|(\\/)|(\\:)|(\\*)|(\\?)|(\\\")|(\\<)|(\\>)|(\\|)", ""));

	}
}
