package std.demo.local.synchronize;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
	public static void main(String[] args) {
		// 构造方法指定支持同时访问的授权数量
		Semaphore semaphore = new Semaphore(5);

		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				try {
					// 获取权限 阻塞
					semaphore.acquire();

					String threadName = Thread.currentThread().getName();
					System.out.println(String.format("线程%s开始执行", threadName));

					TimeUnit.SECONDS.sleep(1);

					System.out.println(String.format("线程%s执行完毕", threadName));
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// 释放权限
					semaphore.release();
				}
			}).start();
		}
	}
}
