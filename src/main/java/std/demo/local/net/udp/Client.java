package std.demo.local.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
	public static void main(String[] args) {
		// 构建接收/发送套接字
		DatagramSocket client = null;
		try {
			client = new DatagramSocket();
			String sendStr = "hellow";
			byte[] sendBuf = sendStr.getBytes();
			// 设置目的地IP
			InetAddress addr = InetAddress.getByName("127.0.0.1");
			// 设置目的地端口
			int port = 8888;
			// 打包数据包
			DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
			// 发送数据包
			client.send(sendPacket);
			byte[] recvBuf = new byte[1024];
			// 构造接收包
			DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
			// 阻塞接收 直到有数据包到达
			client.receive(recvPacket);
			String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());
			System.out.println("server:" + recvStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
			}
		}
	}
}
