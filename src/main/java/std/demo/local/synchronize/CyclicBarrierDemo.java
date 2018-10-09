package std.demo.local.synchronize;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo {

	public static void main(String[] args) {
		demo1();
	}

	public static void demo1() {
		int threadCount = 5;
		// 构造方法初始化需要到达屏障的线程数
		CyclicBarrier barrier = new CyclicBarrier(threadCount);
		// 只有指定数量的线程调用了await()方法 所有调用await()方法的线程才能解除阻塞
		for (int i = 0; i < threadCount; i++) {
			new Thread(() -> {
				try {
					String threadName = Thread.currentThread().getName();
					System.out.println(String.format("线程%s开始执行", threadName));

					TimeUnit.SECONDS.sleep(1);

					System.out.println(String.format("线程%s执行完毕", threadName));
					// 阻塞当前线程直到指定数目的线程调用了await方法
					barrier.await();

					System.out.println(String.format("所有线程执行完毕,%s执行结束", threadName));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
}
