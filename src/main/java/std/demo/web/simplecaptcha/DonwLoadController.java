package std.demo.web.simplecaptcha;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DonwLoadController {

	@RequestMapping("downLoad/test")
	public void downLoadTest(HttpServletRequest request, HttpServletResponse response) throws IOException {


		// 设置响应信息
		response.setHeader("Content-Disposition",
				"attachment; filename=FingerGames.html");
		response.setContentType("text/html");

		OutputStream out = response.getOutputStream();

		String html = Jsoup.connect("http://wap.gktmobi.com/nl/fingergame/fingergame2_1_1.html").get().html();
		
		out.write(html.getBytes());

		// System.out.println(SpringContextUtils.getBean("context"));
	}
	
	
	
	@RequestMapping("downLoad")
	public void downLoad(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String fileName = "文件导出.txt";
		// 设置响应信息
		response.setHeader("Content-Disposition",
				"attachment; filename=" + new String(fileName.getBytes("gbk"), "ISO-8859-1"));
		response.setContentType("text/plain");

		OutputStream out = response.getOutputStream();

		out.write("TEST".getBytes());

		// System.out.println(SpringContextUtils.getBean("context"));
	}

	@RequestMapping("download")
	public void test(@RequestParam("file") MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response) throws IOException, EncryptedDocumentException, InvalidFormatException {

		List<String> result = parseExcel(uploadFile.getInputStream());

		// 设置响应信息
		response.setHeader("Content-Disposition",
				"attachment; filename=" + new String("images.zip".getBytes("gbk"), "ISO-8859-1"));
		response.setContentType("application/octet-stream");

		ZipOutputStream out = new ZipOutputStream(response.getOutputStream());

		int index = 1;
		String imageFormat = "test%d.png";

		// （2017年12月1号 - 12月12号之间）
		String begin = "2017-12-01";
		String end = "2017-12-12";

		LocalDate beginDate = LocalDate.parse(begin);
		LocalDate endDate = LocalDate.parse(end);

		int dis = endDate.getDayOfYear() - beginDate.getDayOfYear();

		for (String account : result) {

			LocalDate randomDate = beginDate.plusDays(randomInt(0, dis));

			LocalDateTime randomDateTime = randomDate.atTime(randomInt(8, 12), randomInt(0, 59), randomInt(0, 59));

			createImage(account, randomDateTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 上午HH时mm分ss秒")),
					randomStr(4), randomStr(20), String.format(imageFormat, index++), out);
		}

		out.close();
	}

	public void createImage(String account, String time, String number, String order, String fileName,
			ZipOutputStream out) {
		ByteArrayOutputStream bs = null;
		ImageOutputStream imOut = null;
		InputStream input = null;
		try {
			// 加载待处理图片
			BufferedImage source = ImageIO.read(new FileInputStream("C:\\Users\\yeahmobi\\Desktop\\test\\new.png"));
			// 得到Graphics2D 对象
			Graphics2D g2d = (Graphics2D) source.getGraphics();
			// 设置颜色和画笔粗细
			g2d.setColor(Color.GRAY);
			g2d.setStroke(new BasicStroke(5));
			g2d.setFont(new Font("Serif", Font.PLAIN, 12));
			// 绘制图案或文字

			// 用户名
			g2d.drawString(account, 55, 137);
			// 时间

			// "2066年06月06日 下午6时06分06秒"
			g2d.drawString(time, 30, 255);
			// 卡号
			g2d.setColor(Color.DARK_GRAY);
			g2d.drawString(number, 70, 423);
			// 订单号
			g2d.drawString(order, 29, 471);

			bs = new ByteArrayOutputStream();
			imOut = ImageIO.createImageOutputStream(bs);

			ImageIO.write(source, "PNG", imOut);

			input = new ByteArrayInputStream(bs.toByteArray());

			byte data[] = new byte[102400];

			ZipEntry entry = new ZipEntry(fileName);

			out.putNextEntry(entry);
			int count;
			while ((count = input.read(data, 0, 102400)) != -1) {
				out.write(data, 0, count);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bs != null) {
					bs.close();
				}
				if (imOut != null) {
					imOut.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
			}
		}
	}

	public List<String> parseExcel(InputStream in)
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		List<String> result = new ArrayList<>();

		Workbook workbook = WorkbookFactory.create(in);

		Iterator<Sheet> sheetIterator = workbook.sheetIterator();

		while (sheetIterator.hasNext()) {
			Sheet sheet = sheetIterator.next();

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.iterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					result.add(cell.getStringCellValue());

					// 只取第一列
					break;
				}
			}
			// 只取第一页
			break;
		}

		workbook.close();

		return result;
	}

	public String randomStr(int length) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append((int) (Math.random() * 10));
		}

		return builder.toString();
	}

	public int randomInt(int min, int max) {
		Random rand = new Random();
		return rand.nextInt(max - min + 1) + min;
	}
}
