package std.demo;

import java.io.File;

import javax.swing.filechooser.FileSystemView;

public class SystemUtils {

	public static final File DESKTOP;

	static {
		// 获取桌面路径
		DESKTOP = FileSystemView.getFileSystemView().getHomeDirectory();
	}
}
