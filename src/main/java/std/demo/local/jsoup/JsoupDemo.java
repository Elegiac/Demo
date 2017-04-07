package std.demo.local.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupDemo {

	public static void main(String[] args) throws IOException {
//		String html = "<html><head><title>First parse</title></head>"
//				+ "<body><p>Parsed HTML into a doc.</p></body></html>";
//		Document doc = Jsoup.parse(html);
//		
//		System.out.println(doc);
		Document doc = Jsoup.connect("http://www.baidu.com/").get();

		System.out.println(doc.select("a"));
	}

}
