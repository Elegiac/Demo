package std.demo;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.docx4j.Docx4J;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;

public class DocumentTemplateUtil {

	public static final String DEST = "C:/Users/sinoadmin/Desktop/测试导出/test.html";

	public static void main(String[] args) throws Exception {
		ClassLoader classLoader = DocumentTemplateUtil.class.getClassLoader();

		InputStream in = classLoader.getResourceAsStream("temp.docx");

		
		URL url = classLoader.getResource("temp.docx");
		
		
		System.out.println(url.getFile());
		
		if(1==1){
			return;
		}
		
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(in);

		Mapper fontMapper = wordMLPackage.getFontMapper();
		System.out.println(fontMapper.getFontMappings());
		PhysicalFonts.addPhysicalFonts("SimSun", new URL("file:/C:/Users/Administrator/Desktop/fonts/simsun.ttf"));

		// //宋体&新宋体
		// PhysicalFont simsunFont = PhysicalFonts.get("SimSun");
		// fontMapper.put("SimSun", simsunFont);
		// System.out.println(fontMapper.getFontMappings());
		// fontMapper.put("宋体",PhysicalFonts.get("SimSun"));
		// System.out.println(fontMapper.getFontMappings());

		// MainDocumentPart part = wordMLPackage.getMainDocumentPart();
		// List<Text>texts=getAllElementFromObject(part, Text.class);
		// for(Text t:texts){
		// System.out.println(t.getValue());
		// }

		// 设置文件默认字体
		// RFonts rfonts = Context.getWmlObjectFactory().createRFonts();
		// rfonts.setAsciiTheme(null);
		// rfonts.setAscii("simsun");
		// RPr rpr =
		// wordMLPackage.getMainDocumentPart().getPropertyResolver().getDocumentDefaultRPr();
		// rpr.setRFonts(rfonts);

		// List<R>rs=getAllElementFromObject(part, R.class);
		// for(R r:rs){
		// RPr rp = r.getRPr();
		// RFonts f = rp.getRFonts();
		// System.out.println(f.getEastAsia());
		//
		// }

		OutputStream os = new FileOutputStream(DEST);
		// Docx4J.toPDF(wordMLPackage, os);

		Docx4J.toHTML(wordMLPackage, null, null, os);

		// Docx4J.save(wordMLPackage, new
		// File("C:/Users/Administrator/Desktop/test222.docx"));
	}

	@SuppressWarnings("unchecked")
	public static <E> List<E> getAllElementFromObject(Object obj, Class<E> toSearch) {
		List<E> result = new ArrayList<E>();
		if (obj instanceof JAXBElement)
			obj = ((JAXBElement<?>) obj).getValue();
		if (obj.getClass().equals(toSearch))
			result.add((E) obj);
		else if (obj instanceof ContentAccessor) {
			List<?> children = ((ContentAccessor) obj).getContent();
			for (Object child : children) {
				result.addAll(getAllElementFromObject(child, toSearch));
			}
		}
		return result;
	}

}