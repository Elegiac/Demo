package std.demo.web.springwebsocket;

import java.nio.ByteBuffer;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

public class BinaryEndPoint extends BinaryWebSocketHandler {

	@Override
	protected void handleBinaryMessage(WebSocketSession session,
			BinaryMessage message) throws Exception {
		ByteBuffer buf=message.getPayload();
		// TODO Auto-generated method stub
		super.handleBinaryMessage(session, message);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		// 链接建立
		System.out.println("connection established");
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
