package std.demo.local.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.Scanner;

public class PipeDemo {

	private Pipe pipe;

	public void startPipe() {
		try {
			pipe = Pipe.open();
			// 启动读线程
			startReadThread();
			// 启动写线程
			startWriteThread();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * 读线程
	 */
	public void startReadThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Pipe.SourceChannel sourceChannel = pipe.source();

					ByteBuffer buffer = ByteBuffer.allocate(1024);

					while (sourceChannel.read(buffer) != -1) {
						buffer.flip();

						System.out.println("接收端接收:" + new String(buffer.array(), "utf-8"));

						buffer.clear();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 写线程
	 */
	public void startWriteThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Scanner scan = null;
				try {
					// 获取写通道
					Pipe.SinkChannel sinkChannel = pipe.sink();
					scan = new Scanner(System.in);
					while (true) {
						String readLine = scan.nextLine();
						sinkChannel.write(ByteBuffer.wrap(readLine.getBytes("utf-8")));
						System.out.println("发送端发送:" + readLine);
					}
				} catch (IOException e) {
					e.printStackTrace();

				} finally {
					if (scan != null) {
						scan.close();
					}
				}

			}
		}).start();
	}

	public static void main(String[] args) {
		PipeDemo demo = new PipeDemo();
		demo.startPipe();
	}
}
