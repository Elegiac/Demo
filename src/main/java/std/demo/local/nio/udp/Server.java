package std.demo.local.nio.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server {

	public static void main(String[] args) {
		DatagramChannel channel = null;
		try {
			// 打开通道
			channel = DatagramChannel.open();
			// 绑定端口
			channel.socket().bind(new InetSocketAddress(8888));
			// 创建缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);
			// 阻塞模式 阻塞直到收到UDP包
			SocketAddress address = channel.receive(buf);

			System.out.println("client:" + new String(buf.array()));

			// 通过通道将UDP数据发送出去
			channel.send(ByteBuffer.wrap("received".getBytes()), address);

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
