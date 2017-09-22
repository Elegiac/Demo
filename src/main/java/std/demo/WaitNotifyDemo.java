package std.demo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WaitNotifyDemo {
	static Object consumeLock = new Object();
	static Object produceLock = new Object();
	static Queue<Object> queue = new LinkedList<>();

	static void printInfo(String content) {
		String format = "%s-%s";
		System.err.println(String.format(format, Thread.currentThread().getName(), content));
	}

	synchronized static boolean consume() {
		if (queue.size() > 0) {
			queue.poll();
			printInfo("消費一個產品");
			return true;
		}
		return false;
	}

	synchronized static boolean produce() {
		if (queue.size() < 10) {
			queue.offer(new Object());
			printInfo("生產一個產品");
			return true;
		}
		return false;
	}

	static class Consumer implements Runnable {

		private long consumeTime;

		public Consumer(long consumeTime) {
			super();
			this.consumeTime = consumeTime;
		}

		@Override
		public void run() {
			while (true) {
				try {

					TimeUnit.MILLISECONDS.sleep(consumeTime);

					if (!consume()) {

						synchronized (produceLock) {
							produceLock.notifyAll();
						}

						synchronized (consumeLock) {
							consumeLock.wait();
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class Producer implements Runnable {

		private long produceTime;

		public Producer(long produceTime) {
			super();
			this.produceTime = produceTime;
		}

		@Override
		public void run() {
			while (true) {
				try {

					TimeUnit.MILLISECONDS.sleep(produceTime);

					if (!produce()) {

						synchronized (produceLock) {
							produceLock.wait();
						}

					}

					synchronized (consumeLock) {
						consumeLock.notifyAll();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		// 生產者數量
		int producerCount = 2;
		// 消費者數量
		int consumerCount = 10;
		// 生產一個產品的時間
		long produceTime = 2000;
		// 消費一個產品的時間
		long consumeTime = 500;

		for (int i = 1; i <= producerCount; i++) {
			threadPool.execute(new Producer(produceTime));
		}

		for (int i = 1; i <= consumerCount; i++) {
			threadPool.execute(new Consumer(consumeTime));
		}

		threadPool.shutdown();
	}

}
