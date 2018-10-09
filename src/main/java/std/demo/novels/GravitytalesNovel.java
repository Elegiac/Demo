package std.demo.novels;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import std.demo.local.jdbc.JDBCUtil;

public class GravitytalesNovel {

	static String title;
	static String path = "C:\\Users\\yeahmobi\\Desktop\\novels\\new2\\";
	static int start = 1;
	static int nIndex;
	static String type;

	static JDBCUtil jdbc = new JDBCUtil("com.mysql.cj.jdbc.Driver",
			"jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
			"test", "test");

	static String sql = "INSERT INTO novel (source,novel_name,title,c_index,n_index,remarks,type) VALUES (?,?,?,?,?,?,?)";

	public static void main(String[] args) throws IOException {
		title = args[0];
		type = args[1];
		nIndex = Integer.parseInt(args[2]);
		String url = args[3];

		if (args.length > 4)
			start = Integer.parseInt(args[4]);

		path = String.format("%s%s%s", path, title, File.separator);

		while (url != null) {
			System.err.println(start + ":" + url);
			url = task(url);
		}

		jdbc.close();
	}

	public static String task(String url) throws IOException {
		String next = null;

		Document doc = Jsoup.connect(url).get();

		Element div = doc.getElementById("chapterContent");

		String c_title = null;

		File file = new File(path, start + ".txt");

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		if (!file.exists()) {
			file.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		Elements paragraph = div.select("p");

		int index = 0;
		for (Element p : paragraph) {

			String line = p.text().trim();

			if ("".equals(line)) {
				continue;
			}

			index++;

			if (line.startsWith("Translator:") || line.startsWith("Editor:") || line.startsWith("Note:")
					|| line.startsWith("Translated by:") || line.startsWith("Edited by:")
					|| line.startsWith("Translator/Editor:") || line.startsWith("[Chapter")) {
				continue;
			}

			if (line.startsWith("Current release rate:")) {
				continue;
			}

			if (index == 1) {
				c_title = line;
				continue;
			}

			writer.write(line);
			writer.newLine();

		}

		// System.err.println(c_title);

		jdbc.executeUpdate(sql, url, title, c_title, start, nIndex, "NEW", type);

		writer.close();

		Element navigation = doc.select(".chapter-navigation").get(0);

		for (Element e : navigation.select("a")) {
			if ("Next Chapter".equals(e.text())) {
				next = e.attr("href");
				break;
			}
		}

		start++;
		return next;
	}

}
