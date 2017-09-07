package std.demo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Test {

	public static void main(String[] args) {

		List<Integer> list1 = new LinkedList<>();
		List<Integer> list2 = new LinkedList<>();

		for (int i = 0; i < 2000000; i++) {
			list1.add(i);
		}

		for (int i = 750000; i < 2000000; i++) {
			list2.add(i);
		}

		System.out.println(String.format("list1:%d,list2:%d", list1.size(), list2.size()));

		long t1 = System.currentTimeMillis();

		// list1.removeAll(list2);

		list1.removeAll(new HashSet<>(list2));

		System.out.println(System.currentTimeMillis() - t1);

		System.out.println(String.format("list1:%d,list2:%d", list1.size(), list2.size()));

	}

}
