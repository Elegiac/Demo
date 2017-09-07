package std.demo.local.subjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
		FileUtils.createFile("C:\\Users\\yeahmobi\\Desktop\\", "dataUri.txt", DATA_URI_PREFIX+s);
		//getImgByDataURI(s);
	}

	public static String getImgDataURI() throws FileNotFoundException,
			IOException {
		FileImageInputStream imageinput = new FileImageInputStream(new File(
				"C:\\Users\\yeahmobi\\Desktop\\qrcode_for_gh_fb60111b11a1_430.jpg"));
		byte[] by = new byte[(int) imageinput.length()];
		imageinput.read(by);

		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encodeBuffer(by);
	}

	public static void getImgByDataURI(String dataUri)
			throws FileNotFoundException, IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] b = decoder.decodeBuffer(dataUri);
		FileImageOutputStream imageOutput = new FileImageOutputStream(new File(
				"C:\\Users\\sinoadmin\\Desktop\\image.png"));
		imageOutput.write(b, 0, b.length);
		imageOutput.close();
	}
}
