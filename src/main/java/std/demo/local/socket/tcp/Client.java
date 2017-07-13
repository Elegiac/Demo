package std.demo.local.socket.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * socket 客户端
 * 
 * @author yeahmobi
 *
 */
public class Client {
	public static void main(String args[]) {
		Socket server = null;
		DataOutputStream out = null;
		DataInputStream in = null;

		Scanner scan = null;

		try {
			// 连接服务端
			server = new Socket("127.0.0.1", 8888);
			// 获取输出流
			out = new DataOutputStream(server.getOutputStream());
			// 获取输入流
			in = new DataInputStream(server.getInputStream());

			scan = new Scanner(System.in);

			while (true) {
				// 获取键盘输入
				String writeLine = scan.nextLine();
				// 写出数据
				out.writeUTF(writeLine);
				// 接收数据
				System.out.println("server:" + in.readUTF());

				if ("exit".equalsIgnoreCase(writeLine)) {
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (scan != null) {
					scan.close();
				}
				if (server != null) {
					server.close();
				}
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
