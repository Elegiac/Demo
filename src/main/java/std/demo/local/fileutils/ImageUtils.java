package std.demo.local.fileutils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * 图片裁剪
 * 
 * @author admin
 *
 */
public class ImageUtils {

	public static BufferedImage getSubImage(InputStream input, int x, int y,
			int width, int height) throws IOException {
		BufferedImage image = ImageIO.read(input);

		// int imageWidth = image.getWidth();
		// int imageHeight = image.getHeight();
		//
		// if (x + width > imageWidth) {
		// width = width - (x + width - imageWidth);
		// }
		//
		// if (y + height > imageHeight) {
		// height = height - (y + height - imageHeight);
		// }

		return image.getSubimage(x, y, width, height);
	}

	public static void main(String[] args) throws Exception {
		String source = "C:\\Users\\sinoadmin\\Desktop\\003.jpg";

		String target = "C:\\Users\\sinoadmin\\Desktop\\test2.jpg";

		String suffix = source.substring(source.lastIndexOf(".") + 1);

		InputStream input = new FileInputStream(source);

		BufferedImage newImage = getSubImage(input, 0, 0, 200, 200);
		
		ImageIO.write(newImage, suffix, new File(target));
	}
}
