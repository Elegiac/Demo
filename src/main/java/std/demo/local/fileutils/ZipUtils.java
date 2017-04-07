package std.demo.local.fileutils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

public class ZipUtils {
	static final int BUFFER = 2048;

	public static void main(String[] args) throws IOException, RarException {
		// File file = new File(
		// "C:\\Users\\sinoadmin\\Desktop\\测试压缩\\5000000号码测试.phone");
		// String directoryPath = "C:\\Users\\sinoadmin\\Desktop\\阿斯蒂芬\\";
		// String target = "C:\\Users\\sinoadmin\\Desktop\\myfigs.zip";
		// // zip(target, file);
		//zip("C:\\Users\\sinoadmin\\Desktop\\e.zip", "C:\\Users\\sinoadmin\\Desktop\\mydate\\");

		unZip("C:\\Users\\sinoadmin\\Desktop\\5000000号码测试.rar", null);

	}

	public static void zip(String target, String directoryPath)
			throws IOException {
		File directory = new File(directoryPath);
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException(directoryPath + "不是一个有效文件夹");
		}
		File[] files = directory.listFiles();
		zip(target, files);
	}

	public static void zip(String target, File... files) throws IOException {
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
				new FileOutputStream(target)));
		// out.setMethod(ZipOutputStream.DEFLATED);

		zip(null, target, out, files);

		out.close();
	}

	public static void zip(String parentPath, String target,
			ZipOutputStream out, File... files) throws IOException {
		for (File file : files) {
			if (file.isDirectory()) {
				// 是文件夹
				// 获取此文件夹下文件列表
				File[] listFiles = file.listFiles();
				if (listFiles.length > 0) {
					// 文件列表不为空 开始递归
					zip(file.getName(), target, out, file.listFiles());
				} else {
					// 文件列表为空 直接创建空文件夹
					String emptyDirectoryPath = file.getName();
					if (parentPath != null) {
						emptyDirectoryPath = parentPath + File.separator
								+ emptyDirectoryPath;
					}
					emptyDirectoryPath += File.separator;
					out.putNextEntry(new ZipEntry(emptyDirectoryPath));
				}
				continue;
			}

			byte data[] = new byte[BUFFER];
			FileInputStream fi = new FileInputStream(file);
			BufferedInputStream origin = new BufferedInputStream(fi, BUFFER);
			ZipEntry entry = new ZipEntry(parentPath == null ? file.getName()
					: parentPath + File.separator + file.getName());
			out.putNextEntry(entry);
			int count;
			while ((count = origin.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			origin.close();
		}
	}

	public static void unZip(String source, String target) throws IOException, RarException {

		Archive a = new Archive(new File(source));
		
		List<FileHeader>headers = a.getFileHeaders();
		System.out.println(headers);
		
		
		
		FileHeader header =null;
		
		header=a.nextFileHeader();

		System.out.println(header);
	InputStream in = a.getInputStream(header);
	
	System.out.println(in.available());
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String tempString = null;

		while ((tempString = reader.readLine()) != null) {
			System.out.println(tempString);
		}

		reader.close();
		
		a.close();
		// ZipFile zipFile = new ZipFile(source);
		// ZipEntry entry = null;
		// BufferedOutputStream dest = null;
		// BufferedInputStream is = null;
		//
		// Enumeration<? extends ZipEntry> entries = zipFile.entries();
		//
		// while (entries.hasMoreElements()) {
		// entry = (ZipEntry) entries.nextElement();
		// System.out.println("Extracting: " + entry);
		// if (entry.getName().startsWith(".")) {
		// System.out.println("continue ");
		// continue;
		// }
		// is = new BufferedInputStream(zipFile.getInputStream(entry));
		// byte data[] = new byte[BUFFER];
		// File targetFile = new File(target, entry.getName());
		// FileOutputStream fos = new FileOutputStream(targetFile);
		// dest = new BufferedOutputStream(fos, BUFFER);
		// int count;
		// while ((count = is.read(data, 0, BUFFER)) != -1) {
		// dest.write(data, 0, count);
		// }
		// dest.flush();
		// dest.close();
		// is.close();
		// }
		// zipFile.close();
	}
}
