package std.demo.local.net.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * socket 服务端
 * 
 * @author yeahmobi
 *
 */
public class Server {
	// http://www.importnew.com/19816.html
	// http://blog.csdn.net/he90227/article/details/39184247
	// http://blog.csdn.net/kongxx/article/details/7288896
	// http://www.51cto.com/specbook/11/40196.htm
	// http://blog.csdn.net/u013322876/article/details/50556941?locationNum=2
	// http://www.365mini.com/page/java-nio-course-26.htm
	// http://blog.csdn.net/u010412719/article/details/52809669
	// http://zhhphappy.iteye.com/blog/2032893
	// http://www.cnblogs.com/fengjian/p/5606045.html
	public static void main(String[] args) {

		ExecutorService threadPool = Executors.newFixedThreadPool(5);

		ServerSocket server = null;
		try {
			/** 监听端口 */
			// 绑定监听端口
			server = new ServerSocket(8888);

			while (true) {
				/** 获得连接 */
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

				while (true) {
					// 读入数据
					String readLine = in.readUTF();
					System.out.println("client:" + readLine);

					if ("exit".equalsIgnoreCase(readLine)) {
						// 写出数据
						out.writeUTF("bye!");
						break;
					}

					// 写出数据
					out.writeUTF("recevied!" + readLine);
				}
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
