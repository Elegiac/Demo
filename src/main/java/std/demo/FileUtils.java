package std.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

	public void readFile(String filePath) {

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(filePath)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				// TODO

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
