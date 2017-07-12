package std.demo.local.net.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.springframework.web.client.RestTemplate;

public class Test {

	public static void main(String[] args) {
		method1();
	}

	public static void method1() {
		try {
			Charset charset = Charset.forName("GBK");// Java.nio.charset.Charset处理了字符转换问题。它通过构造CharsetEncoder和CharsetDecoder将字符序列转换成字节和逆转换。
			CharsetDecoder decoder = charset.newDecoder();

			RandomAccessFile raf = new RandomAccessFile("C:\\Users\\yeahmobi\\Desktop\\test.txt", "rw");
			FileChannel fc = raf.getChannel();

			ByteBuffer buffer = ByteBuffer.allocate(512);
			CharBuffer cb = CharBuffer.allocate(512);

			int count = fc.read(buffer);
			while (count != -1) {
				System.out.println("count = " + count);
				buffer.flip();
				decoder.decode(buffer, cb, false);
				cb.flip();
				while (cb.hasRemaining()) {
					System.out.print(cb.get());
				}
				System.out.println();
				buffer.clear();
				cb.clear();
				count = fc.read(buffer);
			}
			raf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
