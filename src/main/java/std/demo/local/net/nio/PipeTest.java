package std.demo.local.net.nio;

import java.io.IOException;
import java.io.PipedInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeTest {

	public static void main(String[] args) throws IOException {
		Pipe pipe = Pipe.open();

		Pipe.SinkChannel sinkChannel = pipe.sink();

		String newData = "New String to write to file..." + System.currentTimeMillis();

		ByteBuffer buf = ByteBuffer.allocate(48);

		buf.clear();

		buf.put(newData.getBytes());

		buf.flip();

		while (buf.hasRemaining()) {

			sinkChannel.write(buf);

		}

		Pipe.SourceChannel sourceChannel = pipe.source();

		ByteBuffer buf2 = ByteBuffer.allocate(48);

		int bytesRead = sourceChannel.read(buf2);
		
		
		System.out.println(new String(buf2.array()));
		
		
		
	}

}
