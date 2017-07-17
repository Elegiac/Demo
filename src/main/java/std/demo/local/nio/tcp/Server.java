package std.demo.local.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import std.demo.local.nio.ChannelInteractor;

public class Server {
	// 选择器
	private Selector selector = null;
	// TCP服务端通道
	private ServerSocketChannel channel = null;

	boolean shutdown = false;

	public Server(int port) {
		try {
			// 打开选择器
			selector = Selector.open();
			// 打开通道
			channel = ServerSocketChannel.open();
			// 端口监听
			channel.socket().bind(new InetSocketAddress(8888));
			// 设置为非阻塞(异步)
			channel.configureBlocking(false);
			// 将通道注册到selector上 监听ACCEPT事件
			// ACCEPT事件只能由ServerSocketChannel触发
			// ServerSocketChannel能且仅能触发ACCEPT事件
			channel.register(selector, SelectionKey.OP_ACCEPT);

			// 轮询选择器
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

			System.out.println("on listening");
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

			// 迭代触发监听事件的SelectionKey集合
			Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

			while (iter.hasNext()) {

				SelectionKey key = iter.next();

				try {
					// 处理ACCEPT事件
					if (key.isAcceptable()) {
						onAccept(key);
					}

					// 处理READ事件
					if (key.isReadable()) {
						onRead(key);
					}

					// 处理WRITE事件
					if (key.isValid() && key.isWritable()) {
						onWrite(key);
					}
				} catch (Exception e) {
					e.printStackTrace();
					key.channel().close();
				} finally {
					iter.remove();
				}
			}
		}
	}

	/**
	 * ACCEPT事件处理
	 * 
	 * @param key
	 * @throws IOException
	 */
	public void onAccept(SelectionKey key) throws IOException {
		System.out.print("onAccept->");
		// 通过SelectionKey获取到通道
		ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
		// accept()获取与客户端通信的通道
		SocketChannel sc = ssChannel.accept();
		// 设置为非阻塞(异步)
		sc.configureBlocking(false);
		// 将与客户端通信的通道注册到selector上 监听READ事件
		// 并为其分配一个ChannelInteractor实例 在后续的处理中可以通过SelectionKey获取该ChannelInteractor
		ChannelInteractor interactor = new ChannelInteractor(sc.getRemoteAddress(), 1024, "UTF-8");
		System.out.println(interactor);
		// 监听读事件
		sc.register(key.selector(), SelectionKey.OP_READ, interactor);
	}

	/**
	 * READ事件处理
	 * 
	 * @param key
	 * @throws IOException
	 */
	public void onRead(SelectionKey key) throws IOException {
		System.out.print("onRead->");
		// 获取与客户端通信的通道
		SocketChannel channel = (SocketChannel) key.channel();
		// 获取之前为该通道分配的ChannelInteractor
		ChannelInteractor interactor = (ChannelInteractor) key.attachment();
		System.out.print(interactor);
		String readLine = interactor.read(channel);
		// 有读事件但读取数据长度为-1 说明客户端连接断开
		if (readLine == null) {
			System.out.println(" closed");
			channel.close();
			return;
		}
		System.out.println(":" + readLine);
		if ("exit".equalsIgnoreCase(readLine)) {
			return;
		}
		// 回显
		interactor.setWriteMessage(readLine);
		// 监听读写事件
		key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
	}

	/**
	 * WRITE事件处理
	 * 
	 * @param key
	 * @throws IOException
	 */
	public void onWrite(SelectionKey key) throws IOException {
		System.out.print("onWrite->");
		// 获取与客户端通信的通道
		SocketChannel channel = (SocketChannel) key.channel();
		// 获取之前为该通道分配的ClientInfo
		ChannelInteractor interactor = (ChannelInteractor) key.attachment();
		System.out.print(interactor);

		String writeMessgae = interactor.getWriteMessage();

		interactor.write(channel, writeMessgae);

		System.out.println(writeMessgae);

		// 监听读事件
		key.interestOps(SelectionKey.OP_READ);
	}

	public void close() {
		shutdown = true;
		selector.wakeup();
		try {
			if (selector != null) {
				selector.close();
			}
			if (channel != null) {
				channel.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new Server(8888);
	}
}
