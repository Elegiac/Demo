package std.demo.local.nio;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class ClientInfo {

	private SocketAddress address;

	private ByteBuffer readBuffer;
	private ByteBuffer writeBuffer;

	public ClientInfo(SocketAddress address, ByteBuffer readBuffer, ByteBuffer writeBuffer) {
		super();
		this.address = address;
		this.readBuffer = readBuffer;
		this.writeBuffer = writeBuffer;
	}

	public SocketAddress getAddress() {
		return address;
	}

	public void setAddress(SocketAddress address) {
		this.address = address;
	}

	public ByteBuffer getReadBuffer() {
		return readBuffer;
	}

	public void setReadBuffer(ByteBuffer readBuffer) {
		this.readBuffer = readBuffer;
	}

	public ByteBuffer getWriteBuffer() {
		return writeBuffer;
	}

	public void setWriteBuffer(ByteBuffer writeBuffer) {
		this.writeBuffer = writeBuffer;
	}

	@Override
	public String toString() {
		return "Client [address=" + address + "]";
	}

}
