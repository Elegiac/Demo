package std.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParseFileToMap {

	public static void main(String[] args) {

		ParseFileToMap parse = new ParseFileToMap();
		System.out
				.println(parse
						.parseFileToMap("C:\\Users\\yeahmobi\\Desktop\\autoUploadAPKinfo.txt"));

	}

	public Map<String, String> parseFileToMap(String filePath) {

		Map<String, String> result = new HashMap<String, String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(filePath)));
			String line = null;
			String key = "";
			StringBuilder value = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				if ("".equals(line.trim())) {
					continue;
				}
				if (line.contains("&")) {
					if (!"".equals(key) && value.length() > 0) {
						result.put(key, value.toString());
						key = "";
						value = new StringBuilder();
					}

					String[] parts = line.split("&");
					key = parts[0];
					value.append(parts[1]);
				} else {
					value.append("\r\n"+line);
				}
			}

			if (!"".equals(key) && value.length() > 0) {
				result.put(key, value.toString());
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
		return result;
	}

}
