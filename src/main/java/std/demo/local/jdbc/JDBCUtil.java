package std.demo.local.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtil {

	private Connection conn;

	public JDBCUtil(String driver, String url, String username, String password) {
		try {
			Class.forName(driver); // classLoader,加载对应驱动
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Map<String, Object>> queryForList(String sql) {

		if (sql == null) {
			throw new NullPointerException("SQL can not be null");
		}

		sql = sql.trim();

		if (sql.length() < 6) {
			throw new IllegalArgumentException("Invalid SQL length");
		}

		if (!"select".equalsIgnoreCase(sql.substring(0, 6))) {
			throw new IllegalArgumentException("not a select SQL");
		}

		List<Map<String, Object>> resultList = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();

			int col = metaData.getColumnCount();

			while (rs.next()) {

				Map<String, Object> line = new HashMap<>();

				for (int i = 1; i <= col; i++) {
					line.put(metaData.getColumnName(i), rs.getObject(i));
				}

				resultList.add(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
			}
		}
		return resultList;
	}

	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	public static void main(String[] args) {
		JDBCUtil jdbc = new JDBCUtil("com.mysql.cj.jdbc.Driver", "jdbc:mysql://172.30.10.81:3306/paytransdb",
				"gktdbuser", "gktdb123654");

		List<Map<String, Object>> resultList = jdbc.queryForList("select * from t_application limit 10");
		
		for(Map<String, Object>map:resultList) {
			System.out.println(map);
		}

		jdbc.close();

	}
}
