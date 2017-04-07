package std.demo.local.fileutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilsOwn {
	public static void main(String[] args) throws Exception {
//		 File file = new File(
//		 "C:\\Users\\sinoadmin\\Desktop\\excelTest\\workbook.xls");
//		
//		 if (!file.getParentFile().exists()) {
//		 file.getParentFile().mkdirs();
//		 }
//		 if (file.exists()) {
//		 file.delete();
//		 }
//		 file.createNewFile();
//		
//		 FileOutputStream fileOut = new FileOutputStream(file);
//		
//		 List<String> title = new ArrayList<String>();
//		
//		 title.add("value1");
//		 title.add("value2");
//		 title.add("value3");
//		 title.add("value4");
//		
//		 List<List<String>> data = new ArrayList<List<String>>();
//		 List<String> list1 = new ArrayList<String>();
//		 List<String> list2 = new ArrayList<String>();
//		 List<String> list3 = new ArrayList<String>();
//		 List<String> list4 = new ArrayList<String>();
//		
//		 list1.add("l11");
//		 list1.add("l12");
//		 list1.add("l13");
//		 list1.add("l14");
//		 list2.add("l21");
//		 list2.add("l22");
//		 list2.add("l23");
//		 list2.add("l24");
//		 list3.add("l31");
//		 list3.add("l32");
//		 list3.add("l33");
//		 list3.add("l34");
//		 list4.add("l41");
//		 list4.add("l42");
//		 list4.add("l43");
//		 list4.add("l44");
//		 data.add(list1);
//		 data.add(list2);
//		 data.add(list3);
//		 data.add(list4);
//		 createWorkBook(title, data, fileOut);
//		
//		 fileOut.close();

		readWorkBook();
	}

	// 使用POI创建excel工作簿
	public static void createWorkBook(List<String> title,
			List<List<String>> data, OutputStream out) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook(); // --->创建了一个excel文件
		HSSFSheet sheet = wb.createSheet("测试excel"); // --->创建了一个工作簿

		// 表格第一行 title
		HSSFRow titleRow = sheet.createRow(0); // --->创建一行

		int i = 0;
		for (String s : title) {
			HSSFCell cell = titleRow.createCell(i);
			cell.setCellValue(s);
			i++;
		}

		// 表格内容行
		i = 1;
		for (List<String> dataList : data) {
			HSSFRow dataRow = sheet.createRow(i);
			int c = 0;
			for (String dataValue : dataList) {
				HSSFCell dataCell = dataRow.createCell(c);
				dataCell.setCellValue(dataValue);
				c++;
			}
			i++;
		}

		wb.write(out);
		wb.close();
	}

	// 使用POI读入excel工作簿文件
	public static void readWorkBook() throws Exception {
		String filePath = "C:\\Users\\sinoadmin\\Desktop\\excelTest\\workbook.xls";
		// poi读取excel
		// 创建要读入的文件的输入流
		InputStream inp = new FileInputStream(filePath);
		// 根据上述创建的输入流 创建工作簿对象
		Workbook wb = null;
		
		try {
			wb = new HSSFWorkbook(inp);
		} catch (Exception e) {
			inp = new FileInputStream(filePath);
			wb = new XSSFWorkbook(inp);
		}

		// 获取总页数
		// int sheetNumber = wb.getNumberOfSheets();

		// 得到第一页 sheet
		// 页Sheet是从0开始索引的
		Sheet sheet = wb.getSheetAt(0);
		// 总行数
		int rowNumber = sheet.getPhysicalNumberOfRows();

		for (int i = 0; i < rowNumber; i++) {
			Row row = sheet.getRow(i);
			// 该行列数
			int cellNumber = row.getPhysicalNumberOfCells();

			for (int t = 0; t < cellNumber; t++) {
				System.out.println(row.getCell(t).toString());
			}
		}
		wb.close();
		// 关闭输入流
		inp.close();
	}
}
