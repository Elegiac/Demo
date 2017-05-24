package std.demo.local.net.io.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
	public static void main(String[] args) {
		try (DatagramSocket client = new DatagramSocket();) {
			String sendStr = "hellow";
			byte[] sendBuf = sendStr.getBytes();
			InetAddress addr = InetAddress.getByName("127.0.0.1");
			int port = 8888;
			DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
			client.send(sendPacket);
			byte[] recvBuf = new byte[1024];
			DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
			client.receive(recvPacket);
			String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());
			System.out.println("server:" + recvStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
