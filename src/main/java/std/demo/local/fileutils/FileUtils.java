package std.demo.local.fileutils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 创建文件
 * 
 * @author zy
 *
 */
public class FileUtils {

	public static void main(String[] args) throws IOException {
		// 生成号码文件名
		String fileName = "fileName.txt";
		// 创建文件对象
		String path = "C:\\Users\\sinoadmin\\Desktop\\TestPath\\TestPath2\\";
		String content = "just some content";

		createFile(path, fileName, content);
	}

	public static void createFile(String path, String fileName, String content)
			throws IOException {
		File file = new File(path, fileName);

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (file.exists()) {
			file.delete();
		}
		// 创建文件
		file.createNewFile();

		// 将内容写入文件
		FileWriter writer = new FileWriter(file);
		writer.write(content);
		writer.flush();
		writer.close();
	}
}
