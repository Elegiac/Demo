package std.demo.local.fileutils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

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

import std.demo.SystemUtils;

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
		File file = new File(SystemUtils.DESKTOP, "test.xlsx");

		write(new FileOutputStream(file));
	}
}
