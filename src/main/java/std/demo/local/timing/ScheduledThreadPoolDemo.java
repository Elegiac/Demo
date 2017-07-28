package std.demo.local.timing;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolDemo {

	public static void main(String[] args) {

		// 取代Timer
		// Timer缺陷：
		// 1.单线程
		// 2.定时任务抛异常会导致整个Timer终止
		// 执行周期任务时依赖系统时间

		ScheduledExecutorService service = Executors.newScheduledThreadPool(5);

		// service.schedule(new Runnable() {
		//
		// @Override
		// public void run() {
		// System.out.println("called " + LocalDateTime.now());
		// }
		// }, 3, TimeUnit.SECONDS);

		// 任务执行间隔 = 任务执行时间 + delay
		// service.scheduleWithFixedDelay(new Runnable() {
		//
		// @Override
		// public void run() {
		// System.out.println("[" + Thread.currentThread().getName() + "] WithFixedDelay
		// " + LocalDateTime.now());
		// }
		// }, 1, 1, TimeUnit.SECONDS);

		// 任务执行时间小于period
		// 任务执行间隔 = period
		// 任务执行时间大于period
		// 任务执行间隔 = 任务执行时间

		service.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				System.out.println("[" + Thread.currentThread().getName() + "] AtFixedRate\t " + LocalDateTime.now());
			}
		}, 1, 1, TimeUnit.SECONDS);

		// service.shutdown();
	}

}
