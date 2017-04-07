package std.demo;

import java.util.HashSet;
import java.util.Set;

public class Test {

	public static void main(String[] args) {
		Set<Integer> redBalls = new HashSet<>();

		while (redBalls.size() < 6) {
			redBalls.add((int) (Math.random() * 33 + 1));
		}

		Integer blueBall = (int) (Math.random() * 15 + 1);

		System.out.println(redBalls.toString() + ',' + blueBall);
	}

}
