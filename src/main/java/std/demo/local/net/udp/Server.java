package std.demo.local.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
	public static void main(String[] args) {
		// 构建接收/发送套接字
		DatagramSocket server = null;
		try {
			// 监听端口
			server = new DatagramSocket(8888);

			byte[] bytes = new byte[1024];

			// 构造接收包
			DatagramPacket recvPacket = new DatagramPacket(bytes, bytes.length);

			// 阻塞接收 直到有数据包到达
			server.receive(recvPacket);

			String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());

			System.out.println("client:" + recvStr);

			// 通过数据包获取到发送方端口
			int port = recvPacket.getPort();
			// 通过数据包获取到发送方IP
			InetAddress addr = recvPacket.getAddress();
			String sendStr = "received";
			byte[] sendBuf;
			sendBuf = sendStr.getBytes();
			// 打包数据包
			DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
			// 发送数据包
			server.send(sendPacket);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				// 解除端口占用
				server.close();
			}
		}
	}
}
