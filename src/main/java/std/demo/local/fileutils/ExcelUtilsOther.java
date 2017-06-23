package std.demo.local.fileutils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtilsOther {

	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";

	/**
	 * 获取Workbook对象
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static Workbook getWorkbok(String filePath) throws Exception {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("文件不存在");
		}
		if (!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file
				.getName().endsWith(EXCEL_XLSX)))) {
			throw new Exception("文件不是Excel");
		}
		Workbook wb = WorkbookFactory.create(new FileInputStream(filePath));
		return wb;
	}

	/**
	 * 默认excel第一行为标题
	 * 
	 * @param workbook
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public static <E> List<E> read(Workbook workbook, Class<E> c)
			throws Exception {
		Iterator<Row> iterator = workbook.getSheetAt(0).rowIterator();
		// 记录标题与单元格索引关系
		Map<String, Integer> titleIndex = new HashMap<>();

		if (!iterator.hasNext()) {
			return null;
		}

		Row row = iterator.next();

		int cellNumber = row.getPhysicalNumberOfCells();

		for (int i = 0; i < cellNumber; i++) {
			Cell cell = row.getCell(i);
			titleIndex.put(cell.getStringCellValue(), i);
		}

		return read(iterator, titleIndex, c);
	}

	/**
	 * excel中无标题，需要传入参数
	 * 
	 * @param title
	 * @param workbook
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public static <E> List<E> read(String[] title, Workbook workbook, Class<E> c)
			throws Exception {
		// 传入的title长度超过excel宽度会出错

		Iterator<Row> iterator = workbook.getSheetAt(0).rowIterator();
		// 记录标题与单元格索引关系
		Map<String, Integer> titleIndex = new HashMap<>();
		for (int i = 0; i < title.length; i++) {
			titleIndex.put(title[i], i);
		}
		return read(iterator, titleIndex, c);
	}

	private static <E> List<E> read(Iterator<Row> iterator,
			Map<String, Integer> titleIndex, Class<E> c) throws Exception {
		List<E> result = new ArrayList<>();
		Field[] fields = c.getDeclaredFields();
		while (iterator.hasNext()) {
			E obj = c.newInstance();
			Row row = iterator.next();
			for (int j = 0; j < fields.length; j++) {
				Field field = fields[j];
				Integer fieldIndex = titleIndex.get(field.getName());
				if (fieldIndex == null) {
					continue;
				}
				field.setAccessible(true);
				field.set(obj, row.getCell(fieldIndex).getStringCellValue());
			}
			result.add(obj);
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		String s = "C:\\Users\\sinoadmin\\Desktop\\excelTest\\workbook.xls";

		Workbook wo = getWorkbok(s);

		System.out.println(read(wo, TestBean.class));

	}

}
