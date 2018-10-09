package std.demo.novels;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import std.demo.local.jdbc.JDBCUtil;

public class OtherNovel {

	static String title;
	static String path = "C:\\Users\\yeahmobi\\Desktop\\novels\\old\\";
	static int start = 1;
	static int nIndex;
	static String type;

	static JDBCUtil jdbc = new JDBCUtil("com.mysql.cj.jdbc.Driver",
			"jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
			"test", "test");

	static String sql = "INSERT INTO novel (source,novel_name,title,c_index,n_index,remarks,type) VALUES (?,?,?,?,?,?,?)";

	public static void main(String[] args) throws IOException {

		title = "My Wife is a Beautiful CEO";
		type = "";
		nIndex = 9;
		String url = "http://liberspark.com/read/my-wife-is-a-beautiful-ceo/chapter-231";

		start = 340;

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

		String firstLine = doc.getElementById("reader-title").text();

		String c_title = doc.getElementById("reader-chapter").text();

		c_title = c_title + " " + firstLine;

		File file = new File(path, start + ".txt");

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		if (!file.exists()) {
			file.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriterWithEncoding(file, "UTF-8"));

		// String html = div.html();
		// html = html.replaceAll("<br>", "\r\n");
		// html = html.replace("<script>chaptererror();</script>", "");
		// writer.write(html);

		Element div = doc.getElementById("reader-content");

		Elements paragraph = div.select("p");

		for (Element p : paragraph) {

			String line = p.text().trim();

			if ("".equals(line.trim())) {
				continue;
			}

			writer.write(line);

			writer.newLine();
		}

		jdbc.executeUpdate(sql, url, title, c_title, start, nIndex, null, type);

		writer.close();

		for (Element a : doc.select("a")) {
			if (a.text().contains("Next Chapter")) {
				next = "http://liberspark.com" + a.attr("href");
				break;
			}
		}
		start++;
		return next;
	}
}
