package std.demo.local.net.io.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
	public static void main(String[] args) {
		try (DatagramSocket server = new DatagramSocket(8888);) {

			byte[] bytes = new byte[1024];

			DatagramPacket recvPacket = new DatagramPacket(bytes, bytes.length);

			server.receive(recvPacket);

			String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());

			System.out.println("client:" + recvStr);

			int port = recvPacket.getPort();
			InetAddress addr = recvPacket.getAddress();
			String sendStr = "received";
			byte[] sendBuf;
			sendBuf = sendStr.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
			server.send(sendPacket);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
