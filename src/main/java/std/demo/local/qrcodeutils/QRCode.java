package std.demo.local.qrcodeutils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class QRCode {

	private static final String DEFAULT_FORMAT = "png";
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 500;

	/**
	 * 解析二维码图片文本内容
	 * 
	 * @param filePath
	 *            图片路径
	 * @return
	 * @throws IOException
	 * @throws NotFoundException
	 */
	public String parseQRCodeImg(String filePath) throws IOException,
			NotFoundException {
		BufferedImage image = ImageIO.read(new File(filePath));
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		Binarizer binarizer = new HybridBinarizer(source);
		BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
		Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
		Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
		return result.getText();
	}

	/**
	 * 生成二维码图片
	 * 
	 * @param width
	 *            图片宽
	 * @param height
	 *            图片高
	 * @param content
	 *            文本内容
	 * @return
	 * @throws WriterException
	 */
	private BitMatrix generateQRCodeImg(int width, int height, String content)
			throws WriterException {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		// 生成矩阵
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, width, height, hints);
		return bitMatrix;

	}

	/**
	 * 输出二维码图片到流
	 * 
	 * @param width
	 *            图片宽
	 * @param height
	 *            图片高
	 * @param content
	 *            文本内容
	 * @param format
	 *            图片格式
	 * @param stream
	 *            输出流
	 * @throws WriterException
	 * @throws IOException
	 */
	public void writeImgToStream(int width, int height, String content,
			String format, OutputStream stream) throws WriterException,
			IOException {
		BitMatrix bitMatrix = generateQRCodeImg(width, height, content);
		MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
	}

	/**
	 * 输出图片到路径
	 * 
	 * @param width
	 *            图片宽
	 * @param height
	 *            图片高
	 * @param content
	 *            文本内容
	 * @param format
	 *            图片格式
	 * @param filePath
	 *            生成路径
	 * @param fileName
	 *            图片名称
	 * @throws WriterException
	 * @throws IOException
	 */
	public void writeImgToPath(int width, int height, String content,
			String format, String filePath, String fileName)
			throws WriterException, IOException {
		BitMatrix bitMatrix = generateQRCodeImg(width, height, content);
		Path path = FileSystems.getDefault().getPath(filePath, fileName);
		MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
	}

	/**
	 * 输出二维码图片到流
	 * 
	 * @param content
	 *            文本内容
	 * @param stream
	 *            输出流
	 * @throws WriterException
	 * @throws IOException
	 */
	public void writeImgToStream(String content, OutputStream stream)
			throws WriterException, IOException {
		BitMatrix bitMatrix = generateQRCodeImg(DEFAULT_WIDTH, DEFAULT_HEIGHT,
				content);
		MatrixToImageWriter.writeToStream(bitMatrix, DEFAULT_FORMAT, stream);
	}

	public void writeImgToPath(String content, String filePath, String fileName)
			throws WriterException, IOException {
		BitMatrix bitMatrix = generateQRCodeImg(DEFAULT_WIDTH, DEFAULT_HEIGHT,
				content);
		Path path = FileSystems.getDefault().getPath(filePath, fileName);
		MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_FORMAT, path);// 输出图像
	}

	public void writeImgToPath(int width, int height, String content,
			String filePath, String fileName) throws WriterException,
			IOException {
		BitMatrix bitMatrix = generateQRCodeImg(width, height, content);
		Path path = FileSystems.getDefault().getPath(filePath, fileName);
		MatrixToImageWriter.writeToPath(bitMatrix, DEFAULT_FORMAT, path);// 输出图像
	}

	public static void main(String[] args) throws WriterException, IOException,
			NotFoundException {
		String filePath = "C:\\Users\\sinoadmin\\Desktop\\";
		String fileName = "test.png";
		String content = "扫扫扫，有什么好扫的";
		QRCode q = new QRCode();
		 q.writeImgToPath(content, filePath, fileName);

		//System.out.println(q.parseQRCodeImg(filePath + fileName));
	}
}
