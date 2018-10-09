package std.demo.local.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.jdbc.BadSqlGrammarException;

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

	public int getCount(String sql, Object... params) {
		List<Map<String, Object>> resultList = queryForList(sql, params);
		if (resultList.size() != 1) {
			throw new IllegalArgumentException();
		}

		Map<String, Object> resultMap = resultList.get(0);

		if (resultMap.size() != 1) {
			throw new IllegalArgumentException();
		}

		for (String key : resultMap.keySet()) {
			return Integer.parseInt(Objects.toString(resultMap.get(key), "0"));
		}

		return 0;
	}

	public int executeUpdate(String sql, Object... params) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			for (Object param : params) {
				pstmt.setObject(index++, param);
			}

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
			}
		}
		return 0;
	}

	public Map<String, Object> queryForMap(String sql, Object... params) {
		List<Map<String, Object>> resultList = queryForList(sql, params);

		if (resultList.isEmpty()) {
			return null;
		}

		if (resultList.size() > 1) {
			throw new IllegalArgumentException("More than one result!!");
		}

		return resultList.get(0);

	}

	public List<Map<String, Object>> queryForList(String sql, Object... params) {

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

			for (int i = 1; i <= params.length; i++) {
				pstmt.setObject(i, params[i - 1]);
			}

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
		JDBCUtil jdbc = new JDBCUtil("com.mysql.cj.jdbc.Driver", "jdbc:mysql://10.2.1.22:3306/paytransdb", "gktdb_read",
				"gktdb1qaz2ws");

		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) from t_pay_tpay_merged_dn ORDER BY createTime");

		List<Map<String, Object>> resultList = jdbc.queryForList(sql.toString());

		for (Map<String, Object> map : resultList) {
			System.out.println(map);
		}

		jdbc.close();

	}
}
