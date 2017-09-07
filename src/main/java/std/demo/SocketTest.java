package std.demo;

import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.springframework.web.socket.WebSocketSession;

public class SocketTest implements Runnable {

	private WebSocketSession session;

	public SocketTest(WebSocketSession session) {
		super();
		this.session = session;
	}

	@Override
	public void run() {
		Socket socket = null;
		InputStream input = null;
		try {
			socket = new Socket("towel.blinkenlights.nl", 23);

			input = socket.getInputStream();
			// OutputStream output = socket.getOutputStream();

			// BufferedReader r = new BufferedReader(new InputStreamReader(input,
			// "iso-8859-1"));
			//
			// String readLine = null;
			//
			// while ((readLine = r.readLine()) != null) {
			//
			// System.err.println("Message is " + readLine + ", length = " +
			// readLine.length());
			//
			// if(readLine.length()==0) {
			// continue;
			// }
			//
			// TextMessage returnMessage = new TextMessage(readLine);
			//
			// session.sendMessage(returnMessage);
			//
			// }
			// r.close();

			byte[] b = new byte[1024000];

			int length = -1;
			while ((length = input.read(b)) != -1) {

				String str = new String(b, 0, length);

				System.err.println(str);
				// str = str.replaceAll("\r\n", "<br>");

				// System.err.println(str.replaceAll("\r|\n", "<br>"));
				// TextMessage returnMessage = new TextMessage(str);
				// session.sendMessage(returnMessage);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(socket);
		}

	}

	public static void main(String[] args) {
		SocketTest t = new SocketTest(null);

		t.run();

	}

}
