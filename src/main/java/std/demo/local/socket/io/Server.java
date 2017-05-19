package std.demo.local.socket.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	static final List<ClientHandlerTask> CLIENTS = new ArrayList<>();

	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(5);
		try (ServerSocket server = new ServerSocket(8888)) {
			System.out.println("on listenerling");
			while (true) {
				Socket client = server.accept();
				ClientHandlerTask task = new ClientHandlerTask(client);
				CLIENTS.add(task);
				pool.execute(task);
				System.out.println("accepted " + client.getInetAddress());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class ClientHandlerTask implements Runnable {
		private Socket client;
		private InputStream in;
		private OutputStream out;

		public ClientHandlerTask(Socket client) throws IOException {
			this.client = client;
			this.in = client.getInputStream();
			this.out = client.getOutputStream();
		}

		public Socket getClient() {
			return client;
		}

		public void setClient(Socket client) {
			this.client = client;
		}

		public InputStream getIn() {
			return in;
		}

		public void setIn(InputStream in) {
			this.in = in;
		}

		public OutputStream getOut() {
			return out;
		}

		public void setOut(OutputStream out) {
			this.out = out;
		}

		@Override
		public void run() {
			try {
				while (true) {
					int length = in.available();
					if (length > 0) {
						byte[] bytes = new byte[length];
						in.read(bytes);
						String message = new String(bytes, "utf-8");
						if (message.equals("exit")) {
							System.out.println("client "
									+ client.getInetAddress() + " exited");
							break;
						}
						sendMessage("client " + client.getInetAddress()
								+ " say:" + message, CLIENTS);
						System.out.println("client say :" + message);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					out.close();
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	synchronized static void sendMessage(String message,
			List<ClientHandlerTask> clients) throws IOException {
		Iterator<ClientHandlerTask> it = clients.iterator();
		while (it.hasNext()) {
			ClientHandlerTask clientHander = it.next();
			if (clientHander.getClient().isClosed()) {
				it.remove();
				continue;
			}
			OutputStream out = clientHander.getOut();
			out.write(message.getBytes("utf-8"));
		}
	}
}
