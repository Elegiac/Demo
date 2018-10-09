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

public class WuXiaWorldNovel {

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

		Element div = doc.select(".fr-view").get(0);

		if (doc.select("div.caption h4").isEmpty()) {
			// title不存在 跳过
			return null;
		}

		String c_title = doc.select("div.caption h4").text();

		File file = new File(path, start + ".txt");

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		if (!file.exists()) {
			file.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriterWithEncoding(file, "UTF-8"));

		Elements paragraph = div.select("p");
		for (Element p : paragraph) {

			String line = p.text().trim();

			if ("".equals(line.trim())) {
				continue;
			}

			if (line.contains("Can't wait until tomorrow to see more? Want to show your support?")) {
				// 过滤非小说内容数据
				continue;
			}

			writer.write(line);
			writer.newLine();
		}

		jdbc.executeUpdate(sql, url, title, c_title, start, nIndex, null, type);

		writer.close();

		if (!doc.select("li.next.pull-right a").isEmpty())
			next = "https://www.wuxiaworld.com" + doc.select("li.next.pull-right a").attr("href");

		start++;
		return next;
	}

}
