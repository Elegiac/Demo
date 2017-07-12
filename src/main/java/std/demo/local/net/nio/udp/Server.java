package std.demo.local.net.nio.udp;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server {

	public static void main(String[] args) {

		try (DatagramChannel channel = DatagramChannel.open()) {
			channel.socket().bind(new InetSocketAddress(8888));
			
			ByteBuffer buf = ByteBuffer.allocate(1024);

			SocketAddress address = channel.receive(buf);

			System.out.println("client:" + new String(buf.array()));

			channel.send(ByteBuffer.wrap("received".getBytes()), address);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
