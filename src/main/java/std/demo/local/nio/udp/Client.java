package std.demo.local.nio.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client {

	public static void main(String[] args) {
		DatagramChannel channel = null;
		try {
			// 打开通道
			channel = DatagramChannel.open();
			// 指定通道目标地址
			channel.connect(new InetSocketAddress("127.0.0.1", 8888));

			// 构建缓冲区
			ByteBuffer buf = ByteBuffer.wrap("hellow".getBytes());

			// 写出数据包
			channel.write(buf);

			ByteBuffer buf2 = ByteBuffer.allocate(1024);

			// 接收数据包
			channel.read(buf2);

			System.out.println("server:" + new String(buf2.array()));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
				}
			}
		}

	}

}
