package std.demo.local.redis.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

public class SpringRedisDemo {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("redis.xml");
		StringRedisTemplate template = ac.getBean(StringRedisTemplate.class);

		System.out.println(template.opsForHash().keys("*"));
		System.out.println(template.keys("*"));
	}
}
