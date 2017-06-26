package std.demo.local.net.io.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * IO 客户端
 * 
 * @author yeahmobi
 *
 */
public class Client {
	public static void main(String args[]) {
		Socket server = null;
		DataOutputStream out = null;
		DataInputStream in = null;

		try {
			// 连接服务端
			server = new Socket("127.0.0.1", 8888);
			// 获取输出流
			out = new DataOutputStream(server.getOutputStream());
			// 获取输入流
			in = new DataInputStream(server.getInputStream());

			// 写出数据
			out.writeUTF("hello");
			// 接收数据
			System.out.println("server:" + in.readUTF());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
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
