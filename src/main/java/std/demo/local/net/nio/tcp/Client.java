package std.demo.local.net.nio.tcp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.Scanner;

public class Client {
	Charset charset = Charset.forName("UTF-8");
	CharsetDecoder decoder = charset.newDecoder();

	private Selector selector = null;
	private SocketChannel socketChannel = null;

	public Client(String ip, int port) {
		try {

			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);

			// 获取selector
			selector = Selector.open();

			// 绑定客户端连接事件
			socketChannel.register(selector, SelectionKey.OP_CONNECT);

			socketChannel.connect(new InetSocketAddress(ip, port));

			System.out.println("connected");

			// 轮询selector
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						pollingSelector();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void pollingSelector() throws IOException {
		while (selector.select() > 0) {

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

	public void onConnectable(SelectionKey key) throws IOException {
		System.out.println("onConnectable");
		final SocketChannel sChannel = (SocketChannel) key.channel();

		if (sChannel.isConnectionPending()) {
			sChannel.finishConnect();
		}

		sChannel.configureBlocking(false);

		sChannel.register(key.selector(), SelectionKey.OP_READ);

	}

	public void onRead(SelectionKey key) throws IOException {
		System.out.println("onRead");
		SocketChannel channel = (SocketChannel) key.channel();

		String readLine = readToString(channel);

		if (readLine == null) {
			channel.close();
			return;
		}

		System.out.println("server:" + readLine);

		// channel.write(ByteBuffer.wrap(readLine.getBytes("UTF-8")));
	}

	public String readToString(SocketChannel channel) throws IOException {

		ByteBuffer buffer = ByteBuffer.allocate(1024);

		int bytesRead = channel.read(buffer);

		if (bytesRead == -1) {
			return null;
		}

		CharBuffer cb = CharBuffer.allocate(buffer.capacity());

		StringBuilder builder = new StringBuilder();

		while (bytesRead > 0) {
			buffer.flip();

			decoder.decode(buffer, cb, false);

			cb.flip();

			while (cb.hasRemaining()) {
				builder.append(cb.get());
			}

			cb.clear();
			buffer.clear();
			bytesRead = channel.read(buffer);
		}

		return builder.toString();
	}

	public void write(String msg) throws UnsupportedEncodingException, IOException {
		this.socketChannel.write(ByteBuffer.wrap(msg.getBytes("utf-8")));
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
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scan.close();
		}

	}

}
