package std.demo.local.net.tcp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		
		BufferedReader buf = null;
		
		InputStream input = null;

		try {
			// 连接服务端
			server = new Socket("towel.blinkenlights.nl", 23);
			// 获取输出流
			//out = new DataOutputStream(server.getOutputStream());
			// 获取输入流
			//in = new DataInputStream(server.getInputStream());

//			scan = new Scanner(System.in);
//
//			while (true) {
//				// 获取键盘输入
//				String writeLine = scan.nextLine();
//				// 写出数据
//				out.writeUTF(writeLine);
//				// 接收数据
//				System.out.println("server:" + in.readUTF());
//
//				if ("exit".equalsIgnoreCase(writeLine)) {
//					break;
//				}
//			}
			
			input = server.getInputStream();
			
			byte[] b = new byte[1024000];

			int length = -1;
			while ((length = input.read(b)) != -1) {

				String str = new String(b, 0, length);

				System.err.println(str);
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(buf!=null) {
					buf.close();
				}
				
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
