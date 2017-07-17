package std.demo.local.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class Test {

	public static void main(String[] args) throws IOException {
		// method1();

		method2();
	}

	public static void method2() throws IOException {

		ChannelInteractor i = new ChannelInteractor(null, 5, "utf-8");

		FileInputStream in = new FileInputStream("C:\\Users\\yeahmobi\\Desktop\\test.txt");

		FileChannel fci = in.getChannel();

		String msg = i.read(fci);

		File file = new File("C:\\Users\\yeahmobi\\Desktop\\test\\", "test.txt");

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (file.exists()) {
			file.delete();
		}

		file.createNewFile();

		FileOutputStream out = new FileOutputStream(file);

		FileChannel fco = out.getChannel();

		i.write(fco, msg);

		in.close();
		out.close();
	}

	public static void method1() {
		try {
			Charset charset = Charset.forName("GBK");// Java.nio.charset.Charset处理了字符转换问题。它通过构造CharsetEncoder和CharsetDecoder将字符序列转换成字节和逆转换。
			CharsetDecoder decoder = charset.newDecoder();

			RandomAccessFile raf = new RandomAccessFile("C:\\Users\\yeahmobi\\Desktop\\test.txt", "rw");
			FileChannel fc = raf.getChannel();

			ByteBuffer buffer = ByteBuffer.allocate(128);
			CharBuffer cb = CharBuffer.allocate(128);

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
				buffer.compact();
				cb.compact();
				count = fc.read(buffer);
			}
			raf.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
