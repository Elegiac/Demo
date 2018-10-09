package std.demo.local.subjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

import std.demo.local.fileutils.FileUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DataImgUtil {
	public static final String DATA_URI_PREFIX = "data:image/png;base64,";

	/**
	 * data协议图片转换
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String s = getImgDataURI();
		s=s.replaceAll("\r\n", "");
		FileUtils.createFile("C:\\Users\\yeahmobi\\Desktop\\", "dataUri.txt", DATA_URI_PREFIX+s);
	}

	public static String getImgDataURI() throws FileNotFoundException,
			IOException {
//		FileImageInputStream imageinput = new FileImageInputStream(new File(
//				"C:\\Users\\yeahmobi\\Desktop\\images\\image1.png"));
//		byte[] by = new byte[(int) imageinput.length()];
		
		
		InputStream imageinput = new FileInputStream("C:\\Users\\yeahmobi\\Desktop\\fate\\20180428_125813.jpg");
		byte[] by = new byte[imageinput.available()];
		imageinput.read(by);
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encodeBuffer(by);
	}

	public static void getImgByDataURI(String dataUri)
			throws FileNotFoundException, IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b = decoder.decodeBuffer(dataUri);
		FileImageOutputStream imageOutput = new FileImageOutputStream(new File(
				"C:\\Users\\yeahmobi\\Desktop\\img.png"));
		imageOutput.write(b, 0, b.length);
		imageOutput.close();
	}
}
