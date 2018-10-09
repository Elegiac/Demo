package std.demo.local.jdbc;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JDBCDemo {
	public static class MetaData {
		private String columnName;
		private String propertyName;
		private Class<?> propertyClass;
		private String getOrSetPropertyName;

		public String getPropertyName() {
			return propertyName;
		}

		public void setPropertyName(String propertyName) {
			this.propertyName = propertyName;
		}

		public Class<?> getPropertyClass() {
			return propertyClass;
		}

		public void setPropertyClass(Class<?> propertyClass) {
			this.propertyClass = propertyClass;
		}

		public String getGetOrSetPropertyName() {
			return getOrSetPropertyName;
		}

		public void setGetOrSetPropertyName(String getOrSetPropertyName) {
			this.getOrSetPropertyName = getOrSetPropertyName;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		@Override
		public String toString() {
			return "MetaData [columnName=" + columnName + ", propertyName=" + propertyName + ", propertyClass="
					+ propertyClass + ", getOrSetPropertyName=" + getOrSetPropertyName + "]";
		}

	}

	private static Connection getConn() {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/paytransdb?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
		String username = "test";
		String password = "test";
		Connection conn = null;
		try {
			Class.forName(driver); // classLoader,加载对应驱动
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void main(String[] args) throws SQLException {
		Connection conn = getConn();
		DatabaseMetaData dbMetaData = conn.getMetaData();
		String[] types = { "TABLE" };
		ResultSet tabs = dbMetaData.getTables("", "", "", types);

		while (tabs.next()) {
			String tableName = tabs.getObject("TABLE_NAME").toString();
			if(tableName.endsWith("_mo")||tableName.endsWith("_dn")||tableName.endsWith("_mt")) {
				tableName = tableName.replace("_pay", "");
				String channelName = tableName.split("_")[1];
				System.err.println(channelName);
			}
		}

		conn.close();

		// generateBeanFromDataBase("market_customer", "com.dzdc.bean",
		// "C:\\Users\\sinoadmin\\Desktop\\", true);
	}

	public static void generateBeanFromDataBase(String tableName, String packageName, String createPath,
			boolean excludeProjectName) {
		Connection conn = getConn();

		String sql = "select * from " + tableName + " where 1=2";

		PreparedStatement pstmt;

		try {
			pstmt = conn.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();

			int col = metaData.getColumnCount();
			// String[] columnNames = new String[col];

			List<MetaData> metaDataInfo = new ArrayList<>();
			Set<String> importPackages = new HashSet<>();

			for (int i = 0; i < col; i++) {
				Class<?> clz = Class.forName(metaData.getColumnClassName(i + 1));
				if (!"java.lang".equals(clz.getPackage().getName())) {
					importPackages.add(clz.getName());
				}

				MetaData data = new MetaData();
				data.setColumnName(metaData.getColumnName(i + 1));
				data.setPropertyName(camelCase(metaData.getColumnName(i + 1)));
				data.setPropertyClass(clz);
				data.setGetOrSetPropertyName(camelCase(metaData.getColumnName(i + 1), false));
				metaDataInfo.add(data);
			}

			if (excludeProjectName) {
				tableName = tableName.substring(tableName.indexOf("_") + 1);
			}

			String generateClassName = camelCase(tableName, false);

			StringBuilder builder = new StringBuilder();

			builder.append("package ");
			builder.append(packageName);
			builder.append(";\r\n\r\n");

			if (!importPackages.isEmpty()) {
				for (String importName : importPackages) {
					builder.append("import ");
					builder.append(importName);
					builder.append(";\r\n");
				}
				builder.append("\r\n");
			}

			builder.append("public class ");
			builder.append(generateClassName);
			builder.append(" {\r\n");

			for (MetaData data : metaDataInfo) {
				builder.append("\tprivate ");
				builder.append(data.getPropertyClass().getSimpleName());
				builder.append(" ");
				builder.append(data.getPropertyName());
				builder.append(";\r\n");
			}

			for (MetaData data : metaDataInfo) {
				builder.append("\tpublic ");
				builder.append(data.getPropertyClass().getSimpleName());
				builder.append(" get");
				builder.append(data.getGetOrSetPropertyName());
				builder.append("() {\r\n");
				builder.append("\t\treturn ");
				builder.append(data.getPropertyName());
				builder.append(";\r\n");
				builder.append("\t}\r\n");

				builder.append("\tpublic void set");
				builder.append(data.getGetOrSetPropertyName());
				builder.append("(");
				builder.append(data.getPropertyClass().getSimpleName());
				builder.append(" ");
				builder.append(data.getPropertyName());
				builder.append(") {\r\n");
				builder.append("\t\tthis.");
				builder.append(data.getPropertyName());
				builder.append(" = ");
				builder.append(data.getPropertyName());
				builder.append(";\r\n");
				builder.append("\t}\r\n");
			}

			builder.append("}");

			File file = new File(createPath, generateClassName + ".java");

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			// 创建文件
			file.createNewFile();

			// 将内容写入文件
			FileWriter writer = new FileWriter(file);
			writer.write(builder.toString());
			writer.flush();
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static String upperCaseFirstLetter(String word) {
		StringBuilder sbd = new StringBuilder(word);
		String firstLetter = sbd.substring(0, 1);
		sbd.replace(0, 1, firstLetter.toUpperCase());
		return sbd.toString();
	}

	public static String lowerCaseFirstLetter(String word) {
		StringBuilder sbd = new StringBuilder(word);
		String firstLetter = sbd.substring(0, 1);
		sbd.replace(0, 1, firstLetter.toLowerCase());
		return sbd.toString();
	}

	public static String camelCase(String word) {
		return camelCase(word, true);
	}

	public static String camelCase(String word, boolean lowerCaseFirstLetter) {
		String[] parts = word.split("_");

		StringBuilder builder = new StringBuilder();

		for (String part : parts) {
			builder.append(upperCaseFirstLetter(part));
		}
		if (lowerCaseFirstLetter) {
			return lowerCaseFirstLetter(builder.toString());
		}
		return builder.toString();
	}
}
