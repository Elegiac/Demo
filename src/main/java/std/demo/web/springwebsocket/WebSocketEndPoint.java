package std.demo.web.springwebsocket;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import std.demo.SocketTest;

public class WebSocketEndPoint extends TextWebSocketHandler {

	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		// 收到消息
		System.out.println("on text message：" + message.getPayload());
		TextMessage returnMessage = new TextMessage(message.getPayload()
				+ " received at server");
		session.sendMessage(returnMessage);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		// 链接建立
		System.out.println("connection established");
		
		SocketTest test = new SocketTest(session);

		Thread t = new Thread(test);
		t.start();
		
	}

	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		// 通讯异常
		if (session.isOpen()) {
			session.close();
		}
		System.out.println("transport error：" + exception.getMessage());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception {
		// 连接关闭
		System.out.println("connection closed：" + status);
	}

}
