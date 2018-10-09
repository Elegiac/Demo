package std.demo.novels;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import std.demo.local.jdbc.JDBCUtil;

public class Import2 {

	static String SQL_SEARCH_NOVEL = "SELECT identification FROM t_novel WHERE novel_title = ?";

	static String SQL_INSERT_CHAPTER = "INSERT INTO t_chapter (identification,createTime,chapter_index,chapter_title,novel_id,link,source_link) VALUES (UUID(),NOW(),?,?,?,?,?)";

	static String SQL_SEARCH = "SELECT * FROM novel WHERE novel_name = ? AND c_index = ?";

	static AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAJ2W6D6Q6SI6BJQ4A",
			"SBlT1oFadqfk0WgimvwellvThrTjWl4f3IZXVI/y");

	static AmazonS3Client s3Client = new AmazonS3Client(awsCredentials);

	static JDBCUtil living = new JDBCUtil("com.mysql.cj.jdbc.Driver",
			"jdbc:mysql://10.2.1.23:3306/contentdb?useUnicode=true&characterEncoding=utf8", "gktdb_write",
			"12qwasxz3edc");

	static JDBCUtil local = new JDBCUtil("com.mysql.cj.jdbc.Driver",
			"jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
			"test", "test");

	public static String upload(String fileName, String novelId, File file) {

		s3Client.putObject(
				new PutObjectRequest("justdownit", "ContentManage/novel/content/" + novelId + "/" + fileName, file)
						.withCannedAcl(CannedAccessControlList.PublicRead));

		String downloadPath = "http://justdownit.s3.amazonaws.com/ContentManage/novel/content/" + novelId + "/"
				+ fileName;

		return downloadPath;
	}

	public static void main(String[] args) {

		String path = args[0];

		File target = new File(path);

		for (File dir : target.listFiles()) {
			String novelName = dir.getName();

			boolean special = false;
			if ("Legends of Ogre Gate".equals(novelName)) {
				// 第2章无 c_index-1
				special = true;
			}

			List<Map<String, Object>> resultNovelList = living.queryForList(SQL_SEARCH_NOVEL, novelName);
			Map<String, Object> resultNovelMap = resultNovelList.get(0);

			String novelId = resultNovelMap.get("identification").toString();

			for (File file : dir.listFiles()) {

				String fileName = file.getName();

				int cIndex = Integer.parseInt(fileName.substring(0, fileName.indexOf(".")));

				System.err.println("current:" + novelName + "-" + cIndex);

				List<Map<String, Object>> resultList = local.queryForList(SQL_SEARCH, novelName, cIndex);

				Map<String, Object> resultMap = resultList.get(0);

				String soucre = resultMap.get("source").toString();
				String title = resultMap.get("title").toString();

				if (special && cIndex > 1) {
					cIndex = cIndex - 1;
				}

				String uploadUrl = upload(cIndex + ".txt", novelId, file);

				living.executeUpdate(SQL_INSERT_CHAPTER, cIndex, title, novelId, uploadUrl, soucre);

			}

		}
	}

}
