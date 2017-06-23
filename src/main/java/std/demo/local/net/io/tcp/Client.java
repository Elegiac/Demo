package std.demo.local.net.io.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * IO 客户端
 * 
 * @author yeahmobi
 *
 */
public class Client {
	public static void main(String args[]) {
		// 连接服务端
		try (Socket server = new Socket("127.0.0.1", 8888);
				// 获取输出流
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				// 获取输入流
				DataInputStream in = new DataInputStream(server.getInputStream());) {

			// 写出数据
			out.writeUTF("hello");
			// 接收数据
			System.out.println("server:" + in.readUTF());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
