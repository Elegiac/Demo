package std.demo.local.jsoup;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupDemo {

	public static void main(String[] args) throws IOException {
		// String html = "<html><head><title>First parse</title></head>"
		// + "<body><p>Parsed HTML into a doc.</p></body></html>";
		// Document doc = Jsoup.parse(html);
		//
		// System.out.println(doc);
		Document doc = Jsoup.connect("http://www.baidu.com/").get();

		Elements elements = doc.select("a");

		Iterator<Element> it = elements.iterator();

		while (it.hasNext()) {
			Element element = it.next();
			System.out.println(element.attr("href"));
		}
		
	}

}
