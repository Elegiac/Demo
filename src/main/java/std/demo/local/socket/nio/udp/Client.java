package std.demo.local.socket.nio.udp;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client {

	public static void main(String[] args) {
		try (DatagramChannel channel = DatagramChannel.open()) {
			channel.connect(new InetSocketAddress("127.0.0.1", 8888));

			ByteBuffer buf = ByteBuffer.wrap("hellow".getBytes());

			channel.write(buf);

			ByteBuffer buf2 = ByteBuffer.allocate(1024);

			channel.read(buf2);

			System.out.println("server:" + new String(buf2.array()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
