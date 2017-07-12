package std.demo.local.net.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;
import java.util.Iterator;

import std.demo.local.net.nio.ByteBufferDemo;

public class Server {
	private static final int BUF_SIZE = 1024;
	private static final int PORT = 8888;
	private static final int TIMEOUT = 3000;

	static Charset charset = Charset.forName("UTF-8");
	static CharsetDecoder decoder = charset.newDecoder();

	public static void main(String[] args) {
		selector();
	}

	public static String readToString(SocketChannel channel, ByteBuffer buffer) throws IOException {
		
		int bytesRead = channel.read(buffer);

		if (bytesRead == -1) {
			return null;
		}

		CharBuffer cb = CharBuffer.allocate(buffer.capacity());

		StringBuilder builder = new StringBuilder();

		while (bytesRead > 0) {
			buffer.flip();
			
			
			// while (buffer.hasRemaining()) {
			//
			// byte b = buffer.get();
			//
			// System.out.println(b+","+(char)b);
			// builder.append((char)b);
			// }

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

	public static void writeString(SocketChannel channel, String msg) throws IOException {
		//ByteBuffer buffer = ByteBuffer.allocate(1024);

		ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes("utf-8"));

		buffer.put(msg.getBytes("utf-8"));

		buffer.flip();

		while (buffer.hasRemaining()) {

			channel.write(buffer);
		}
	}

	public static void onAccept(SelectionKey key) throws IOException {
		System.out.println("onAccept");
		ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
		SocketChannel sc = ssChannel.accept();
		sc.configureBlocking(false);
		//sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
		sc.register(key.selector(), SelectionKey.OP_READ);
		
	}

	public static void onRead(SelectionKey key) throws IOException {
		System.out.println("onRead");
		SocketChannel channel = (SocketChannel) key.channel();

		ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);

		String readLine = readToString(channel, buffer);

		if (readLine == null) {
			channel.close();
			return;
		}

		System.out.println("client:" + readLine);

		if ("exit".equalsIgnoreCase(readLine)) {
			// channel.close();
			return;
		}

		writeString(channel, readLine);
	}

	public static void onWrite(SelectionKey key) throws IOException {
		System.out.println("onWrite");
//		ByteBuffer buffer = ByteBuffer.wrap("hellow guys".getBytes("utf-8"));
//		buffer.flip();
//		SocketChannel sc = (SocketChannel) key.channel();
//		while (buffer.hasRemaining()) {
//			sc.write(buffer);
//		}
	}

	public static void selector() {
		Selector selector = null;
		ServerSocketChannel ssc = null;
		try {
			selector = Selector.open();
			ssc = ServerSocketChannel.open();
			
			ssc.socket().setReuseAddress(true);  
			ssc.socket().bind(new InetSocketAddress(PORT));
			ssc.configureBlocking(false);
			ssc.register(selector, SelectionKey.OP_ACCEPT);

			while (true) {
				if (selector.select(TIMEOUT) == 0) {
					// System.out.println("==");
					continue;
				}
				Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

				while (iter.hasNext()) {

					SelectionKey key = iter.next();

					try {

						System.out.println("isValid:"+key.isValid());
						System.out.println("isConnectable:"+key.isConnectable());
						System.out.println("isAcceptable:"+key.isAcceptable());
						System.out.println("isReadable:"+key.isReadable());
						System.out.println("isWritable:"+key.isWritable());
						
						if (key.isAcceptable()) {
							onAccept(key);
						}
						if (key.isReadable()) {
							onRead(key);
						}
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

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (selector != null) {
					selector.close();
				}
				if (ssc != null) {
					ssc.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
