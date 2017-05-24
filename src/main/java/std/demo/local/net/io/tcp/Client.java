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
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				DataInputStream in = new DataInputStream(server.getInputStream());) {

			out.writeUTF("hello");

			System.out.println("server:" + in.readUTF());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
