package std.demo.local.nio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.io.WriteAbortedException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class Test {

	public static void main(String[] args) throws IOException {
		// method1();

		method2();
	}

	static void write(WritableByteChannel channel,String msg) throws IOException {
		Charset charset = Charset.forName("GBK");
		CharsetEncoder encoder = charset.newEncoder();
		
		ByteBuffer bb = ByteBuffer.allocate(5);
		CharBuffer cb = CharBuffer.allocate(5);

		for (int i = 0; i < msg.length(); i++) {
			if (cb.hasRemaining()) {
				cb.put(msg.charAt(i));
			} else {
				cb.flip();

				while (cb.hasRemaining()) {
					encoder.encode(cb, bb, false);
					bb.flip();
					
					channel.write(bb);
					
					bb.clear();
				}
				
				cb.clear();
			}
		}
	}
	
	
	
	
	
	
	public static void method2() throws IOException {
		
		File file = new File("C:\\Users\\yeahmobi\\Desktop\\test\\", "test.txt");

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (file.exists()) {
			file.delete();
		}
		
		file.createNewFile();
		
		FileInputStream in = new FileInputStream(file);
		
		FileChannel fc = in.getChannel();
		
		write(fc, "绯闻啊所发生的v覅陪我1213213");
		
		in.close();
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
