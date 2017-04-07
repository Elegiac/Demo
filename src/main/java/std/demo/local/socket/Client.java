package std.demo.local.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) {
		Socket server = null;
		try {
			server = new Socket("127.0.0.1", 8880);
			OutputStream out = server.getOutputStream();
			InputStream in = server.getInputStream();

			BufferedReader d = new BufferedReader(new InputStreamReader(in,
					"utf-8"));

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out,
					"utf-8"), true);

			pw.println("testing one online....");

			System.out.println("server：" + d.readLine());

			pw.println("testing two online....");

			System.out.println("server：" + d.readLine());

			pw.println("testing three online....");

			System.out.println("server：" + d.readLine());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
