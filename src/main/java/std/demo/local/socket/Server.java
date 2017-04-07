package std.demo.local.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(8880);
			Socket client = server.accept();

			InputStream in = client.getInputStream();
			OutputStream out = client.getOutputStream();

//			BufferedReader d = new BufferedReader(new InputStreamReader(in,
//					"utf-8"));

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out,
					"utf-8"), true);

			
			pw.println("hellow, new guys!");
			
			try {
				Thread.sleep(1500);
				
				pw.println("i konw you must be come back");
				
				Thread.sleep(1500);
				
				pw.println("so i wait you");
				
				Thread.sleep(1500);
				
				pw.println("finally,did you think you hava time to escape?");
				
				Thread.sleep(1500);
				
				pw.println("now the end is coming");
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			System.out.println("client：" + d.readLine());
//
//			pw.println("receiving testing one");
//
//			System.out.println("client：" + d.readLine());
//
//			pw.println("receiving testing two");
//
//			System.out.println("client：" + d.readLine());
//
//			pw.println("receiving testing three");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null)
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
