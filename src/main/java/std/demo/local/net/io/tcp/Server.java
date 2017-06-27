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
	// http://blog.csdn.net/he90227/article/details/39184247
	// http://www.cnblogs.com/bizhu/archive/2012/05/12/2497493.html
	// http://www.cnblogs.com/roverliang/p/5176456.html
	public static void main(String[] args) {

		ExecutorService threadPool = Executors.newFixedThreadPool(5);

		ServerSocket server = null;
		try {
			// 绑定监听端口
			server = new ServerSocket(8888);

			while (true) {
				// 阻塞等待客户端连接
				Socket client = server.accept();
				// 与客户端交互交给工作线程去处理
				threadPool.execute(new ServerTaskRun(client));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
			DataOutputStream out = null;
			DataInputStream in = null;
			try {
				// 获取输出流
				out = new DataOutputStream(client.getOutputStream());
				// 获取输入流
				in = new DataInputStream(client.getInputStream());
				// 读取数据
				System.out.println("client:" + in.readUTF());
				// 写出数据
				out.writeUTF("recevied!");

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
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
}
