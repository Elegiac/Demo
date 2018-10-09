package std.demo.novels;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import std.demo.local.jdbc.JDBCUtil;

public class WebNovelNovel {

	static String title;
	static String path = "C:\\Users\\yeahmobi\\Desktop\\novels\\old\\";
	static int nIndex;
	static String type;
	static String apiUrl = "https://www.webnovel.com/apiajax/chapter/GetContent?_csrfToken=mCsTyf0pMcCUxP4XILAeaHu1U19SWOVDDw4hxLKk&bookId=%s&chapterId=%s&_=1518335267610";

	static RestTemplate rt = new RestTemplate();

	static JDBCUtil jdbc = new JDBCUtil("com.mysql.cj.jdbc.Driver",
			"jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
			"test", "test");

	static String sql = "INSERT INTO novel (source,novel_name,title,c_index,n_index,remarks,type) VALUES (?,?,?,?,?,?,?)";

	static String searchSql = "SELECT COUNT(1) FROM novel WHERE source LIKE '%webnovel%' and n_index = ? AND c_index = ?";

	public static void main(String[] args) throws IOException {
		String bookId = "6831980302001405";
		String chapterId = "28570106447584599";

		title = "The Sacred Ruins";
		type = "";
		nIndex = 26;

		path = String.format("%s%s%s", path, title, File.separator);

		apiUrl = String.format(apiUrl, bookId, "%s");

		String url = String.format(apiUrl, chapterId);

		while (url != null) {

			System.err.println(url);
			url = task(url);

		}

		jdbc.close();
	}

	public static String task(String url) throws IOException {

		HttpHost proxy = new HttpHost("zproxy.lum-superproxy.io", 22225);
		String res = Executor.newInstance().auth(proxy, "lum-customer-yeahmobi-zone-gkt-country-us", "DDbaz1u456o5x")
				.execute(Request.Get(url).viaProxy(proxy)).returnContent().asString(Charset.forName("UTF-8"));

		String next = null;

		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Object> map = objectMapper.readValue(res, new TypeReference<Map<String, Object>>() {
		});

		Map<String, Object> data = (Map<String, Object>) map.get("data");

		Map<String, Object> chapterInfo = (Map<String, Object>) data.get("chapterInfo");

		String chapterIndex = chapterInfo.get("chapterIndex").toString();

		String c_title = chapterInfo.get("chapterName").toString();

		next = Objects.toString(chapterInfo.get("nextChapterId"), null);

		String content = chapterInfo.get("content").toString();

		File file = new File(path, chapterIndex + ".txt");

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		if (!file.exists()) {
			file.createNewFile();
		}

		BufferedWriter writer = new BufferedWriter(new FileWriterWithEncoding(file, "UTF-8"));

		content = content.replaceAll("<em>", "");
		content = content.replaceAll("</em>", "");
		content = content.replaceAll("<p>", "");
		content = content.replaceAll("</p>", "\r\n");
		content = content.replaceAll("<br>", "\r\n");

		writer.write(content);

		if (jdbc.getCount(searchSql, nIndex, chapterIndex) < 1) {
			jdbc.executeUpdate(sql, url, title, c_title, chapterIndex, nIndex, null, type);
		}

		writer.close();

		if (next != null && !"-1".equals(next)) {
			return String.format(apiUrl, next);
		}

		return null;
	}

}
