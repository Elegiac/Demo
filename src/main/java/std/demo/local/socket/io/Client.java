package std.demo.local.socket.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String args[]) {
		try (Socket server = new Socket("127.0.0.1", 8888);
				OutputStream out = server.getOutputStream();
				Scanner scan = new Scanner(System.in);) {
			Thread t = new Thread(new ListenerWork(server));
			t.start();
			while (true) {
				String message = scan.next();
				out.write(message.getBytes("utf-8"));
				if ("exit".equals(message)) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class ListenerWork implements Runnable {
		private Socket server;
		private InputStream in;

		public ListenerWork(Socket server) throws IOException {
			this.server = server;
			this.in = server.getInputStream();
		}

		@Override
		public void run() {
			try {
				while (true) {
					if (server.isClosed()) {
						break;
					}
					int length = in.available();
					if (length > 0) {
						byte[] bytes = new byte[length];
						in.read(bytes);
						String message = new String(bytes, "utf-8");
						System.out.println(message);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
