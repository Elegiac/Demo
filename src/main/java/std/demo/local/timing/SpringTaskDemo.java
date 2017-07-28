package std.demo.local.timing;

import java.time.LocalDateTime;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTaskDemo {

	public static void main(String[] args) {
		// org.springframework.core.task.TaskExecutor
		// org.springframework.scheduling.TaskScheduler
		// org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-task.xml");
	}

	public void run() {
		System.out.println("called " + LocalDateTime.now());
	}

}
