package std.demo.local.net.io.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * IO 服务端
 * 
 * @author yeahmobi
 *
 */
public class Server {

	public static void main(String[] args) {
		// 5个线程的线程池
		ExecutorService threadPool = Executors.newFixedThreadPool(5);
		// 绑定监听端口
		try (ServerSocket server = new ServerSocket(8888);) {
			while (true) {
				// 阻塞等待客户端连接
				Socket client = server.accept();
				// 与客户端交互交给工作线程去处理
				threadPool.execute(new ServerTaskRun(client));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 与客户端交互类
	 * 
	 * @author yeahmobi
	 *
	 */
	static class ServerTaskRun implements Runnable {
		private Socket client;

		public ServerTaskRun(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			try (DataOutputStream out = new DataOutputStream(client.getOutputStream());
					DataInputStream in = new DataInputStream(client.getInputStream());) {

				System.out.println("client:" + in.readUTF());

				out.writeUTF("recevied!");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
