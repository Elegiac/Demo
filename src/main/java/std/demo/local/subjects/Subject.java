package std.demo.local.subjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class Subject {

	public static void main(String[] args) {
		//Subject_1();
		Subject_3();
	}

	/**
	 * 将字符串中ios(不区分大小写)转换为android
	 */
	public static void Subject_1() {
		String s = "aaabcioSeefIOsjjkkIoSoooppqioIOSios";

		s = s.replaceAll("(?i)ios", "android");

		System.out.println(s);

	}

	/**
	 * 找到最先连续出现指定次数的字符
	 */
	public static void Subject_2() {
		String s = "aaabbbbbccddsdsadfgggggg";
		// 要求的字符出现次数
		int maxCount = 5;
		// 字符连续出现的次数
		int currentCount = 0;
		// 上一个字符
		Character lastChar = null;
		// 当前字符
		Character currChar = null;

		for (int i = 0; i < s.length(); i++) {
			currChar = s.charAt(i);
			if (currChar == lastChar) {
				currentCount++;
			} else {
				lastChar = currChar;
				currentCount = 1;
			}
			if (currentCount >= maxCount) {
				System.out.println(currChar);
				break;
			}
		}
	}

	/**
	 * 将4位数字转换为汉字
	 */
	public static void Subject_3() {
		class NumberUtil {
			public String changeNumber2Characters(char number) {
				switch (number) {
				case '0':
					return "零";
				case '1':
					return "一";
				case '2':
					return "二";
				case '3':
					return "三";
				case '4':
					return "四";
				case '5':
					return "五";
				case '6':
					return "六";
				case '7':
					return "七";
				case '8':
					return "八";
				case '9':
					return "九";
				default:
					return null;
				}
			}
		}

		System.out.println("输入不多于4位的正整数:");
		// 接收控制台输入数字
		Scanner scan = new Scanner(System.in);
		String inputNum = scan.next();
		scan.close();
		try {
			// 输入非数字 数字大于9999或小于0 返回
			int num = Integer.parseInt(inputNum);
			if (num > 9999 || num < 0)
				throw new NumberFormatException();
			inputNum = String.valueOf(num);
		} catch (NumberFormatException e) {
			System.out.println("输入有误");
			return;
		}

		NumberUtil util = new NumberUtil();

		StringBuilder build = new StringBuilder(inputNum);
		// 反转输入数字
		build.reverse();

		StringBuilder appender = new StringBuilder();

		switch (build.length()) {
		case 4:
			appender.append(util.changeNumber2Characters(build.charAt(3)));
			if (build.charAt(3) != '0')
				appender.append("千");
		case 3:
			appender.append(util.changeNumber2Characters(build.charAt(2)));
			if (build.charAt(2) != '0')
				appender.append("百");
		case 2:
			appender.append(util.changeNumber2Characters(build.charAt(1)));
			if (build.charAt(1) != '0')
				appender.append("十");
		case 1:
			appender.append(util.changeNumber2Characters(build.charAt(0)));
		}

		// 连续的"零"替换为1个"零"
		String result = appender.toString();
		while (result.indexOf("零零") != -1) {
			result = result.replace("零零", "零");
		}
		// 删掉末尾的"零"
		if (result.endsWith("零") && result.length() > 1)
			result = result.substring(0, result.length() - 1);
			
		if (result.startsWith("一十"))
			result = result.substring(1);
	
		System.out.println(result);
	}

	/**
	 * 将分隔符分隔的多个号码(有重复) 去重并按出现次数排序
	 */
	public static void Subject_4() {
		String numbersStr = "13333333333|13333333333|13333333333|13333333332|13333333331|13333333331";

		String[] phoneArr = numbersStr.split("\\|");

		Map<String, Integer> countMap = new HashMap<>();

		for (String number : phoneArr) {
			if (countMap.containsKey(number)) {
				countMap.put(number, countMap.get(number) + 1);
			} else {
				countMap.put(number, 1);
			}
		}

		Set<Entry<String, Integer>> entries = countMap.entrySet();

		ArrayList<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(
				entries);

		Collections.sort(list, new Comparator<Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> arg0,
					Entry<String, Integer> arg1) {
				return -(arg0.getValue() - arg1.getValue());
			}
		});

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			builder.append(list.get(i).getKey()).append("|");
		}

		if (builder.length() > 0)
			builder.deleteCharAt(builder.length() - 1);

		System.out.println(builder);

	}

}
