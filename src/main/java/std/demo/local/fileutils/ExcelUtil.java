package std.demo.local.fileutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	public static Workbook getWorkbook(File file)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		return WorkbookFactory.create(file);
	}

	public static void read(File file) throws EncryptedDocumentException, InvalidFormatException, IOException {

		Workbook workbook = getWorkbook(file);

		Iterator<Sheet> sheetIterator = workbook.sheetIterator();

		while (sheetIterator.hasNext()) {
			Sheet sheet = sheetIterator.next();

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.iterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					System.out.print(cell);
					System.out.print("\t");

				}
				System.out.println();
			}
		}

		workbook.close();
	}

	public static void write(OutputStream out) throws IOException {
		// HSSFWorkbook xls
		// XSSFWorkbook xlsx
		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet sheet = workbook.createSheet("sheet1");

		// 测试 创建5行5列
		for (int i = 0; i < 5; i++) {
			XSSFRow row = sheet.createRow(i);
			for (int t = 0; t < 5; t++) {
				XSSFCell cell = row.createCell(t);
				cell.setCellValue("test");
			}
		}

		workbook.write(out);
		workbook.close();
	}

	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException {

		String REGEX_CHINESE = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(REGEX_CHINESE);

		// title type index url
		String fmat = "java -jar C:\\Users\\yeahmobi\\Desktop\\run2.jar \"%s\" %s %d %s";

		Workbook workbook = getWorkbook(new File("C:\\Users\\yeahmobi\\Desktop\\novel-contentzhua-qu-lie-biao.xlsx"));
		Sheet sheet = workbook.getSheetAt(1);

		Iterator<Row> rowIt = sheet.rowIterator();

		rowIt.next();
		rowIt.next();
		while (rowIt.hasNext()) {
			Row row = rowIt.next();
			Cell indexCell = row.getCell(1);
			Cell titleCell = row.getCell(2);
			Cell urlCell = row.getCell(5);
			Cell typeCell = row.getCell(6);

			if (indexCell == null) {
				break;
			}

			String url = urlCell.getStringCellValue().trim();

			if (!url.contains("gravitytales.com")) {
				continue;
			}

			String type = typeCell.getStringCellValue().trim();
			int index = (int) indexCell.getNumericCellValue();
			String title = titleCell.getStringCellValue();

			Matcher mat = pat.matcher(title);

			if (mat.find()) {
				title = title.substring(0, mat.start()).trim();
			}

			String commond = String.format(fmat, title, type, index, url);

			System.err.println(commond);

			// System.out.println("Execute commond:" + commond);

			// Process process = Runtime.getRuntime().exec(commond);

			// showResult(process);
		}

		workbook.close();
	}

	static void showResult(Process process) {
		try {
			process.waitFor();
			BufferedReader bReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"));

			String line = null;
			while ((line = bReader.readLine()) != null)
				System.err.println(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
