package std.demo.jdk8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

	public static void main(String[] args) {
		// 1.接口有默认实现(default关键字)以及可以含有静态方法
		// Collection

		// 2.函数式接口 指仅包含一个抽象方法的接口 @FunctionalInterface注解标识
		// 添加@FunctionalInterface注解的接口 不满足函数式接口条件 会编译不通过
		// 但java并不强制要求要为函数式接口标记此注解。实际上一个接口是否是函数式接口由虚拟机来判断
		// 包含Runnable,Comparator等接口以及1.8新加入的一些接口

		// 3.Lambda 表达式 取代匿名对象方式
		// 不能脱离上下文单独存在，必须要有一个明确的目标类型，即函数式接口

		Runnable r = new Runnable() {
			@Override
			public void run() {
				System.out.println();
			}
		};
		
		Runnable run = () -> {
			//TODO
		};

//		 Comparator<Integer> comp = new Comparator<Integer>() {
//		 @Override
//		 public int compare(Integer i1, Integer i2) {
//		 return i1 - i2;
//		 }
//		 };
		 
		 
		Comparator<Integer> comp = (i1, i2) -> {
			return i1 - i2;
		};

		// 4.方法与构造函数引用
		// :: 关键字来传递方法或者构造函数引用
		// 只能应用于函数式接口
		// 可认为是只执行一条语句的Lambda表达式
		// 静态方法
		FunctionInterfaceTest test1 = Integer::parseInt; // (s)->{return Integer.parseInt(s);};
		// 构造方法
		FunctionInterfaceTest test2 = Integer::new; // (s)->{return new Integer(s);};
		// 成员方法
		FunctionInterfaceTest test3 = String::length; // (s)->{return s.length();};

		// Stream 的创建需要指定一个数据源，比如 java.util.Collection的子类，List或者Set，
		// Map不支持。Stream的操作可以串行执行或者并行执行。
		// 生成器函数（Generator function）
		//Stream.generate(Math::random).limit(5).forEach(System.out::println);

		// Stream基本操作
		List<Integer> list = Arrays.asList(13, 11, 10, 9, 8, 7, 6, 5, 5, 4, 3,
				2, 1, 1);
		list.stream()
		// 自然序排序
				.sorted()
				// 从开始跳过元素
				.skip(3)
				// 操作元素
				.map(i -> i + 1)
				// 过滤
				.filter(i -> i % 2 == 0)
				// 去重
				.distinct()
				// 从开始截取元素
				.limit(3)
				// 遍历
				.forEach(System.out::println);

		List<Integer> numbers1 = Arrays.asList(1, 2, 3, 5, 7, 9, 11, 13);
		List<Integer> numbers2 = Arrays.asList(2, 4, 6, 7, 8, 9, 10, 12, 14);
		List<Integer> numbers3 = Arrays.asList(100, 101);
		List<List<Integer>> numbersAll = Arrays.asList(numbers1, numbers2,
				numbers3);

		numbersAll.stream()
		// 扁平化
				.flatMap(e -> e.stream()).forEach(System.out::println);

		List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		// 全符合
		list2.stream().allMatch(i -> i > 1);
		// 任一符合
		list2.stream().anyMatch(i -> i > 1);
		// 全不符合
		list2.stream().noneMatch(i -> i > 1);
		// 计数
		list2.stream().count();
		// 最大值
		list2.stream().max((i, j) -> (i - j));
		// 最小值
		list2.stream().min((i, j) -> (i - j));
		// 获取任一流中的元素(并行流体现)
		list2.stream().findAny();
		// 获取第一个流中的元素
		list2.stream().findFirst();

		// 收集器Collector
		List<Integer> list3 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		// 求平均值averagingDouble/averagingInt/averagingLong
		list3.stream().collect(Collectors.averagingInt(i->i));

		// 求和summingDouble/summingInt/summingIntLong
		list3.stream().collect(Collectors.summingInt(i->i));

		// 统计各项 summarizingDouble/summarizingInt/summarizingLong
		// 返回包含集合信息的对象(包含元素个数，总和，最小值，最大值，平均值)
		list3.stream().collect(Collectors.summarizingInt(i->i));
		// 结果IntSummaryStatistics
		// IntSummaryStatistics{count=10, sum=55, min=1, average=5.500000,
		// max=10}

		// toList将流中元素收集到一个ArrayList中并返回
		list3.stream().collect(Collectors.toList());

		// groupingBy将流中元素分组 返回一个HashMap
		list3.stream().collect(Collectors.groupingBy((Integer i) -> {
			if (i > 0 && i < 4) {
				return "1~3";
			}
			if (i > 3 && i < 8) {
				return "4~7";
			}
			if (i > 7 && i < 11) {
				return "8~10";
			}
			return ">10";
		}));
		// 结果:
		// Map<String,List>
		// {1~3=[1, 2, 3], 4~7=[4, 5, 6, 7], 8~10=[8, 9, 10]}

		// 重载方法
		// 实现复杂分组
		list3.stream().collect(Collectors.groupingBy((Integer i) -> {
			if (i < 6) {
				return "<6";
			} else {
				return ">5";
			}
		}, Collectors.groupingBy((Integer i) -> {
			if (i > 0 && i < 4) {
				return "1~3";
			}
			if (i > 3 && i < 8) {
				return "4~7";
			}
			if (i > 7 && i < 11) {
				return "8~10";
			}
			return ">10";
		})));

		// Map<String,Map<String,List>>
		// {>5={4~7=[6, 7], 8~10=[8, 9, 10]}, <6={1~3=[1, 2, 3], 4~7=[4, 5]}}

		// join 拼接元素 返回字符串
		// 重载方法:加分隔符/前缀/后缀
		list3.stream().map(Object::toString).collect(Collectors.joining());
		// 结果:
		// 12345678910
		
		
		//中间操作(map(),filter(),)
		//中间操作是lazy的 终结操作是eager
		//调用了终结操作 才会触发中间操作
		//不会出现多次遍历的情况 JDK会将所有的中间操作合并成一个，这个过程被称为熔断操作
	}

	@FunctionalInterface
	interface FunctionInterfaceTest {
		Object get(String s);
	}
}
