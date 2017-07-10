package std.demo.local.net.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Client {

	private Selector selector;

	public SocketChannel start(String ip, int port, IOHandler messageHandler) throws IOException {
		// 连接服务端
		SocketChannel channel = SocketChannel.open();

		// 设置为非阻塞模式
		channel.configureBlocking(false);
		// 获取selector
		selector = Selector.open();

		// 绑定客户端连接事件
		channel.register(selector, SelectionKey.OP_CONNECT, messageHandler);
		// 绑定完连接 事件才能正常监听
		channel.connect(new InetSocketAddress(ip, port));

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				Listening();
			}
		});

		t.start();

		return channel;
	}

	private void Listening() {
		while (true) {
			try {
				// 方法阻塞 直到有注册的事件发生
				selector.select();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> it = keys.iterator();

			while (it.hasNext()) {
				SelectionKey key = it.next();

				it.remove();

				// 自定义处理流程实现类
				IOHandler customerHandler = (IOHandler) key.attachment();

				if (key.isConnectable()) {
					try {
						SocketChannel clientChannel = (SocketChannel) key.channel();

						if (clientChannel.isConnectionPending()) {
							clientChannel.finishConnect();

						}
						clientChannel.configureBlocking(false);
						clientChannel.register(selector, SelectionKey.OP_READ, customerHandler);

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

	public static void main(String[] args) throws IOException {

		// 自己实现的handler
		IOHandler handler = new IOHandler() {

			@Override
			public void onActive(SocketChannel channel) {

				System.out.println("onActive");

			}

			@Override
			public void onRead(SocketChannel channel) {
				try {
					ByteBuffer buffer = ByteBuffer.allocate(10);
					int readLength = channel.read(buffer);
					if (readLength == -1) {
						System.out.println("服务端断开连接");
						channel.close();
						return;
					}
					byte[] data = buffer.array();
					String msg = new String(data).trim();
					System.out.println("Server say :" + msg);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		};

		Client client = new Client();
		SocketChannel channel = client.start("127.0.0.1", 8888, handler);

		// 控制台输入
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		for (;;) {
			String line = in.readLine();
			if (line == null) {
				continue;
			}
			
			if("exit".equals(line)){
				break;
			}

			ByteBuffer outBuffer = ByteBuffer.wrap(line.getBytes());
			channel.write(outBuffer);
		}
		
		channel.close();
	}
}
