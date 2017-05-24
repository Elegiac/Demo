package std.demo.local.net.nio;

import java.nio.channels.SocketChannel;

public interface IOHandler {
	void onActive(SocketChannel channel);

	void onRead(SocketChannel channel);
}
