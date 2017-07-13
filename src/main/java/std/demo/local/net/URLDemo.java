package std.demo.local.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLDemo {

	public static void main(String[] args) throws IOException {
		URL url = new URL("http://api.mobgkt.com/admin/login.html");

		URLConnection connection = url.openConnection();

		InputStream input = connection.getInputStream();

		BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));

		String readLine = null;

		while ((readLine = reader.readLine()) != null) {
			System.out.println(readLine);
		}

		reader.close();
	}

}
