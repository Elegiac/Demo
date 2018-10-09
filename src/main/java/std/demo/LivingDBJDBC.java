package std.demo;

import java.io.IOException;
import java.text.ParseException;

import std.demo.local.jdbc.JDBCUtil;

public class LivingDBJDBC {

	static JDBCUtil jdbc_statistics = new JDBCUtil("com.mysql.cj.jdbc.Driver",
			"jdbc:mysql://10.2.1.23:3306/statisticsdb?useUnicode=true&characterEncoding=utf8&autoReconnect=true",
			"gktdb_write", "12qwasxz3edc");

	static JDBCUtil jdbc_payment = new JDBCUtil("com.mysql.cj.jdbc.Driver",
			"jdbc:mysql://10.2.1.23:3306/paytransdb?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull",
			"gktdb_write", "12qwasxz3edc");

	static JDBCUtil jdbc_content = new JDBCUtil("com.mysql.cj.jdbc.Driver",
			"jdbc:mysql://10.2.1.23:3306/contentdb?useUnicode=true&characterEncoding=utf8", "gktdb_write",
			"12qwasxz3edc");

	// static JDBCUtil jdbc2 = new JDBCUtil("com.mysql.cj.jdbc.Driver",
	// "jdbc:mysql://127.0.0.1:3306/contentdb?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC",
	// "test",
	// "test");

	public static void main(String[] args) throws IOException, ParseException {

		// String s = "select max(createTime) from t_comic_chapter";

		// System.out.println(jdbc.queryForList(s));

		jdbc_statistics.close();
		jdbc_payment.close();
		jdbc_content.close();
	}
}
