package std.demo.local.nio;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class ChannelInteractor {

	private Charset charset;
	private CharsetDecoder decoder;
	private CharsetEncoder encoder;

	private SocketAddress address;

	private ByteBuffer byteBuffer;

	private CharBuffer charBuffer;

	public ChannelInteractor(SocketAddress address, int bufferCapacity, String charsetName) {
		this.address = address;
		this.byteBuffer = ByteBuffer.allocate(bufferCapacity);
		this.charBuffer = CharBuffer.allocate(bufferCapacity);
		this.charset = Charset.forName(charsetName);
		this.decoder = charset.newDecoder();
		this.encoder = charset.newEncoder();
	}

	public SocketAddress getAddress() {
		return address;
	}

	public void setAddress(SocketAddress address) {
		this.address = address;
	}

	public ByteBuffer getByteBuffer() {
		return byteBuffer;
	}

	public void setByteBuffer(ByteBuffer byteBuffer) {
		this.byteBuffer = byteBuffer;
	}

	public void write(WritableByteChannel channel, String msg) throws IOException {
		// 遍历字符
		for (int i = 0; i < msg.length(); i++) {
			if (!charBuffer.hasRemaining()) {
				// charBuffer已满 先写出
				writeToChannel(channel);
			}
			// 将字符放入charBuffer
			charBuffer.put(msg.charAt(i));
		}
		writeToChannel(channel);
	}

	private void writeToChannel(WritableByteChannel channel) throws IOException {
		// charBuffer切换到写模式
		charBuffer.flip();
		// 循环写出
		while (charBuffer.hasRemaining()) {
			// 将charBuffer的内容编码到byteBuffer
			encoder.encode(charBuffer, byteBuffer, false);
			// byteBuffer切换到写模式
			byteBuffer.flip();
			// byteBuffer向通道写出数据
			channel.write(byteBuffer);
			byteBuffer.clear();
		}
		charBuffer.clear();
	}

	public String read(ReadableByteChannel channel) throws IOException {
		StringBuilder builder = new StringBuilder();
		boolean readNothing = true;
		// 循环读取
		while (channel.read(byteBuffer) != -1) {
			readNothing = false;
			// byteBuffer切换到写模式
			byteBuffer.flip();
			// 将byteBuffer的内容解码到charBuffer
			decoder.decode(byteBuffer, charBuffer, false);
			// charBuffer切换到写模式
			charBuffer.flip();
			while (charBuffer.hasRemaining()) {
				// 循环拼接字符
				builder.append(charBuffer.get());
			}
			byteBuffer.compact();
			charBuffer.compact();
		}

		if (readNothing) {
			return null;
		}

		return builder.toString();
	}

	@Override
	public String toString() {
		return "ChannelInteractor [address=" + address + "]";
	}
}
