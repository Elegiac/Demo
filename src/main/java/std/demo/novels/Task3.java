package std.demo.novels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import std.demo.local.jdbc.JDBCUtil;

public class Task3 {

	static AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAJ2W6D6Q6SI6BJQ4A",
			"SBlT1oFadqfk0WgimvwellvThrTjWl4f3IZXVI/y");

	static AmazonS3Client s3Client = new AmazonS3Client(awsCredentials);

	static JDBCUtil living = new JDBCUtil("com.mysql.cj.jdbc.Driver",
			"jdbc:mysql://10.2.1.23:3306/contentdb?useUnicode=true&characterEncoding=utf8", "gktdb_write",
			"12qwasxz3edc");

	static String SQL_SEARCH = "SELECT * FROM t_novel WHERE createTime > '2018-07-23'";

	static String SQL_SEARCH_CHAPTER = "SELECT * FROM t_chapter WHERE novel_id = ?";

	public static void main(String[] args) throws IOException {

		File target = new File("C:\\Users\\yeahmobi\\Desktop\\newnew");

		for (Map<String, Object> m : living.queryForList(SQL_SEARCH)) {

			String novelId = m.get("identification").toString();

			for (Map<String, Object> mm : living.queryForList(SQL_SEARCH_CHAPTER, novelId)) {

				String link = mm.get("link").toString();

				System.out.println("Renew " + link);

				String fileName = link.substring(link.lastIndexOf("/") + 1);

				String key = link.replace("http://justdownit.s3.amazonaws.com/", "");

				S3Object obj = s3Client.getObject(new GetObjectRequest("justdownit", key));

				InputStream in = obj.getObjectContent();

				byte[] bs = new byte[10240];

				int readLen = -1;

				File f = new File(target, fileName);
				OutputStream out = new FileOutputStream(f);

				while ((readLen = in.read(bs)) != -1) {

					String s = new String(bs, 0, readLen, "GBK");

					out.write(s.getBytes("UTF-8"));
				}

				in.close();
				obj.close();
				out.close();

				s3Client.putObject(
						new PutObjectRequest("justdownit", "ContentManage/novel/content/" + novelId + "/" + fileName, f)
								.withCannedAcl(CannedAccessControlList.PublicRead));

			}

		}

	}

}
