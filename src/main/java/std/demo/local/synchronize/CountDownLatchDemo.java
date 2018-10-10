package std.demo.local.synchronize;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch 
 * 线程调用await()方法后会阻塞 
 * 在初始化计数值减为0时所有调用await()的线程才会继续执行
 * 
 * @author yeahmobi
 *
 */
public class CountDownLatchDemo {

	public static void main(String[] args) throws InterruptedException {
		demo2();
	}

	/**
	 * 多线程并行
	 * 
	 * @throws InterruptedException
	 */
	public static void demo1() throws InterruptedException {
		// 构造方法初始化计数器值
		CountDownLatch latch = new CountDownLatch(1);

		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					String threadName = Thread.currentThread().getName();
					System.out.println(String.format("线程%s完成执行准备,等待开始执行", threadName));
					// 等待计数器值为0再继续执行
					latch.await();

					TimeUnit.SECONDS.sleep(1);

					System.out.println(String.format("线程%s执行完毕", threadName));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}

		for (int i = 5; i > 0; i--) {
			TimeUnit.SECONDS.sleep(1);
			System.out.println(String.format("执行倒计时:%d", i));
		}

		System.out.println("开始执行");
		// 计数器值减一
		// 减为0则 调用 await()的进程被唤醒
		latch.countDown();
	}

	/**
	 * 主线程等待多个线程执行完毕再继续执行
	 * 
	 * @throws InterruptedException
	 */
	public static void demo2() throws InterruptedException {
		// 定义线程数
		int threadCount = 5;
		// 构造方法初始化计数器值
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			new Thread(() -> {
				try {
					String threadName = Thread.currentThread().getName();
					System.out.println(String.format("线程%s开始执行", threadName));

					TimeUnit.SECONDS.sleep(1);

					System.out.println(String.format("线程%s执行结束", threadName));

					latch.countDown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}

		System.out.println("主线程等待子线程执行完毕...");
		latch.await();
		System.out.println("主线程继续执行");
	}
}
