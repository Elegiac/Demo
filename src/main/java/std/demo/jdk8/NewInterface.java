//package std.demo.jdk8;
//
//import java.util.Comparator;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.function.Predicate;
//import java.util.function.Supplier;
//
//
//public class NewInterface {
//	public static void main(String[] args) {
//		//Predicate 接口只有一个参数，返回boolean类型。该接口包含多种默认方法来将Predicate组合成其他复杂的逻辑(比如：与，或，非)
//		Predicate<String> predicate = (s) -> s.length() > 0;
//
//		predicate.test("foo");              // true
//		predicate.negate().test("foo");     // false
//
//		Predicate<Boolean> nonNull = Objects::nonNull;
//		Predicate<Boolean> isNull = Objects::isNull;
//
//		Predicate<String> isEmpty = String::isEmpty;
//		Predicate<String> isNotEmpty = isEmpty.negate();
//		
//		
//		//Function 接口有一个参数并且返回一个结果，并附带了一些可以和其他函数组合的默认方法(compose, andThen)
//		Function<String, Integer> toInteger = Integer::valueOf;
//		Function<String, String> backToString = toInteger.andThen(String::valueOf);
//		backToString.apply("123");     // "123"
//		
//		
//		//Supplier 接口返回一个任意范型的值，和Function接口不同的是该接口没有任何参数
//		Supplier<Object> personSupplier = Object::new;
//		personSupplier.get();   // new Object
//		
//		//Consumer 接口表示执行在单个参数上的操作
//		Consumer<Integer> greeter = (i) -> System.out.println(i);
//		greeter.accept(20);
//		
//		
//		//Optional 不是函数是接口，这是个用来防止NullPointerException异常的辅助类型
//		//Optional 被定义为一个简单的容器，其值可能是null或者不是null。在Java 8之前一般某个函数应该返回非空对象但是偶尔却可能返回了null，而在Java 8中，不推荐你返回null而是返回Optional
//	
//		Optional<String> optional = Optional.of("bam");
//
//		optional.isPresent();           // true
//		optional.get();                 // "bam"
//		optional.orElse("fallback");    // "bam"
//
//		optional.ifPresent((s) -> System.out.println(s.charAt(0)));  
//		
//		
//		//Stream 接口 
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		
//		//Comparator,Map
//	
//	}
//}
