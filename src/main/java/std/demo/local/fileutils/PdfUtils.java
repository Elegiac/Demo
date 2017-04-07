package std.demo.local.fileutils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class PdfUtils {
	public static void main1(String[] args) throws FileNotFoundException,
			IOException {
		PdfReader reader = new PdfReader(
				"C:\\Users\\sinoadmin\\Desktop\\Helloworld.PDF");
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		int pages = reader.getNumberOfPages();

		TextExtractionStrategy strategy;
		for (int i = 1; i <= pages; i++) {
			strategy = parser.processContent(i,
					new SimpleTextExtractionStrategy());
			System.out.println(strategy.getResultantText());
		}
	}

	public static void main(String[] args) throws DocumentException,
			MalformedURLException, IOException {
		// ①建立com.lowagie.text.Document对象的实例。
		Document document = new Document();

		// ②建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
		PdfWriter.getInstance(document, new FileOutputStream(
				"C:\\Users\\sinoadmin\\Desktop\\Helloworld.PDF"));

		// ③打开文档。
		document.open();

		// ④向文档中添加内容。
		// document.add(new Paragraph("Hello World"));

		PdfPTable table = new PdfPTable(3);

		for (int i = 1; i <= 30; i++) {
			table.addCell("你好" + i);

		}

//		Image image = Image
//				.getInstance("C:\\Users\\sinoadmin\\Desktop\\u=4207155834,2391068821&fm=23&gp=0.jpg");
//		// PdfImage pdfImage = new PdfImage(image, null, null);
//
//		document.add(image);
		document.add(table);

		// ⑤关闭文档。
		document.close();
	}
}
