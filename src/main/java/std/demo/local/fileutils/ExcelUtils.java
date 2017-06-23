package std.demo.local.fileutils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtils {

	public static void saveMapToExcel(List<Map<String, String>> dataList, OutputStream out) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 创建sheet
		// xls
		HSSFSheet sheet = workbook.createSheet("sheet1");

		if (dataList != null && !dataList.isEmpty()) {

			Map<String, String> firstElement = dataList.get(0);

			// 作为标题栏
			Set<String> keys = firstElement.keySet();

			// 表格第一行 title
			HSSFRow titleRow = sheet.createRow(0); // --->创建一行

			int i = 0;
			for (String title : keys) {
				HSSFCell cell = titleRow.createCell(i);
				cell.setCellValue(title);
				i++;
			}

			// 表格内容行
			i = 1;
			for (Map<String, String> params : dataList) {
				System.out.println("CURRENT ROW:"+i);
				HSSFRow dataRow = sheet.createRow(i);
				int c = 0;
				for (Entry<String, String> entry : params.entrySet()) {
					HSSFCell dataCell = dataRow.createCell(c);
					dataCell.setCellValue(entry.getValue());
					c++;
				}
				i++;

			}

		}

		workbook.write(out);
		workbook.close();

	}

	public static void main(String[] args) throws IOException, SQLException {

		// FileOutputStream out = new
		// FileOutputStream("C:\\Users\\yeahmobi\\Desktop\\ie\\export.xls");
		//
		// List<Map<String, String>> dataList = new ArrayList<>();
		// Map<String,String>map1 = new LinkedHashMap<>();
		// map1.put("t1", "lalal");
		// map1.put("t2", "hahaha");
		// map1.put("t3", "gugugu");
		//
		// Map<String,String>map2 = new LinkedHashMap<>();
		// map2.put("t1", "lalal2");
		// map2.put("t2", "hahaha2");
		// map2.put("t3", "gugugu2");
		//
		// Map<String,String>map3 = new LinkedHashMap<>();
		// map3.put("t1", "lalal3");
		// map3.put("t2", "hahaha3");
		// map3.put("t3", "gugugu3");
		//
		// dataList.add(map1);dataList.add(map2);dataList.add(map3);
		//
		// saveMapToExcel(dataList, out);

		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://10.2.1.22:3306/paytransdb?useUnicode=true&characterEncoding=utf8";
		String username = "gktdb_read";
		String password = "gktdb1qaz2ws";
		Connection conn = null;
		try {
			Class.forName(driver); // classLoader,加载对应驱动
			conn = (Connection) DriverManager.getConnection(url, username, password);
			
			String sql = "select * from t_pay_nth_sms_mo where locate('stop',content) > 0";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();
			
			List<Map<String,String>>dataList =  new ArrayList<>();
			
			while(rs.next()){
				Map<String,String>map = new LinkedHashMap<>();
				dataList.add(map);
				for(int i=0;i<metaData.getColumnCount();i++){
					map.put(metaData.getColumnName(i+1), rs.getString(i+1));
				}
			}
			
			int size  = dataList.size();
			
			int currentIndex = 1;
			
			Iterator<Map<String, String>> it = dataList.iterator();
			
			while(it.hasNext()){
				Map<String, String> current = it.next();
				
				sql = "select count(1) from t_pay_nth_sms_mo where sessionId = ?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, current.get("sessionId"));
				
				rs = pstmt.executeQuery();
				
				rs.next();
				
				int count = rs.getInt(1);
				
				if(count > 1 ){
					it.remove();
				}
				
				System.out.println("CURRENT SESSIONID IS "+current.get("sessionId") + " ,COUNT IS "+count+"("+currentIndex+" of "+size+")");
				
				currentIndex++;
			}
			
			
			FileOutputStream out = new FileOutputStream("C:\\Users\\yeahmobi\\Desktop\\ie\\export.xls");
			
			
			
			saveMapToExcel(dataList, out);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

	}
}
