package std.demo.local.nio;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class ClientInfo {

	private SocketAddress address;

	private ByteBuffer buffer;

	public ClientInfo(SocketAddress address, ByteBuffer buffer) {
		super();
		this.address = address;
		this.buffer = buffer;
	}

	public SocketAddress getAddress() {
		return address;
	}

	public void setAddress(SocketAddress address) {
		this.address = address;
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(ByteBuffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public String toString() {
		return "Client [address=" + address + "]";
	}

}
