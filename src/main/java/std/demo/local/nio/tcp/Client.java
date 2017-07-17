package std.demo.local.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

import std.demo.local.nio.ChannelInteractor;

public class Client {
	private Selector selector;
	private SocketChannel socketChannel;

	private ChannelInteractor interactor;

	private boolean shutdown = false;

	public Client(String ip, int port) {
		try {
			// 打开通道
			socketChannel = SocketChannel.open();
			// 设置为非阻塞(异步)
			socketChannel.configureBlocking(false);
			// 打开选择器
			selector = Selector.open();
			// 将通道注册到selector上 监听CONNECT事件
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			InetSocketAddress address = new InetSocketAddress(ip, port);
			// 连接服务端
			socketChannel.connect(new InetSocketAddress(ip, port));

			interactor = new ChannelInteractor(address, 1024, "UTF-8");

			// 轮询selector
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						pollingSelector();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pollingSelector() throws IOException {
		while (!shutdown) {
			// 阻塞直到有监听的事件发生
			if (selector.select() == 0) {
				continue;
			}

			Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

			while (iter.hasNext()) {

				SelectionKey key = iter.next();

				iter.remove();

				if (key.isConnectable()) {
					onConnectable(key);
				}
				if (key.isReadable()) {
					onRead(key);
				}
			}
		}
	}

	/**
	 * CONNECT事件处理
	 * 
	 * @param key
	 * @throws IOException
	 */
	public void onConnectable(SelectionKey key) throws IOException {
		System.out.println("onConnectable");
		SocketChannel sChannel = (SocketChannel) key.channel();

		if (sChannel.isConnectionPending()) {
			sChannel.finishConnect();
		}

		key.interestOps(SelectionKey.OP_READ);
	}

	/**
	 * READ事件处理
	 * 
	 * @param key
	 * @throws IOException
	 */
	public void onRead(SelectionKey key) throws IOException {
		System.out.print("onRead->");
		SocketChannel channel = (SocketChannel) key.channel();

		String readLine = interactor.read(channel);

		if (readLine == null) {
			System.out.println("server closed");
			channel.close();
			return;
		}

		System.out.println(":" + readLine);
	}

	public void close() {

		try {

			shutdown = true;

			selector.wakeup();

			if (selector != null) {
				selector.close();
			}
			if (socketChannel != null) {
				socketChannel.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void write(String msg) throws IOException {
		interactor.write(socketChannel, msg);
	}

	public static void main(String[] args) {
		Client client = new Client("127.0.0.1", 8888);

		Scanner scan = new Scanner(System.in);
		try {
			while (true) {

				// 获取键盘输入
				String writeLine = scan.nextLine();

				// 写出数据
				client.write(writeLine);

				if ("exit".equalsIgnoreCase(writeLine)) {
					client.close();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scan.close();
		}
	}
}
