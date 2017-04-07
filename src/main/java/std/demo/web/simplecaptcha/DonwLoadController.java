package std.demo.web.simplecaptcha;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import std.demo.web.SpringContextUtils;

@Controller
public class DonwLoadController {
	@RequestMapping("downLoad")
	public void downLoad(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String fileName = "文件导出.txt";
		// 设置响应信息
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("gbk"), "ISO-8859-1"));
		response.setContentType("text/plain");

		OutputStream out = response.getOutputStream();

		out.write("尼玛".getBytes());

		System.out.println(SpringContextUtils.getBean("context"));
	}
}
