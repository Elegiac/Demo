package std.demo.local.net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

	private Selector selector;

	public void start(int port, IOHandler messageHandler) throws IOException {
		// 绑定监听
		ServerSocketChannel server = ServerSocketChannel.open();
		server.socket().bind(new InetSocketAddress(port));
		// 设置为非阻塞模式
		server.configureBlocking(false);

		// 获取selector
		selector = Selector.open();

		// 绑定服务端连接事件
		// ServerSocketChannel只能绑定ACCEPT事件
		server.register(selector, SelectionKey.OP_ACCEPT, messageHandler);

		System.out.println("Listening 8888 port");
		// 监听事件
		Listening();
	}

	private void Listening() {
		while (true) {
			try {
				// 方法阻塞 直到有注册的事件发生
				selector.select();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 获取所有触发监听的事件的SelectionKey
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> it = keys.iterator();

			while (it.hasNext()) {
				SelectionKey key = it.next();

				it.remove();

				// 自定义处理流程实现类
				IOHandler customerHandler = (IOHandler) key.attachment();

				if (key.isAcceptable()) {
					// 获取服务端通道
					ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();

					try {
						// 获取客户端通道
						SocketChannel clientChannel = serverChannel.accept();
						// 设置成非阻塞
						clientChannel.configureBlocking(false);
						// 监听客户端发消息事件
						clientChannel.register(selector, SelectionKey.OP_READ, customerHandler);
						// 自定义处理流程
						customerHandler.onActive(clientChannel);
					} catch (IOException e) {
						e.printStackTrace();

					}

				} else if (key.isReadable()) {
					SocketChannel clientChannel = (SocketChannel) key.channel();

					customerHandler.onRead(clientChannel);
				}

			}
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		// 自己实现的handler
		IOHandler handler = new IOHandler() {

			@Override
			public void onActive(SocketChannel channel) {
				System.out.println("client on active");
			}

			@Override
			public void onRead(SocketChannel channel) {

				try {
					ByteBuffer buffer = ByteBuffer.allocate(10);
					channel.read(buffer);
					byte[] data = buffer.array();
					String msg = new String(data).trim();
					System.out.println("clietn say:" + msg);
					ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
					// 回显消息
					channel.write(outBuffer);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		};

		Server server = new Server();
		server.start(8888, handler);
	}
}