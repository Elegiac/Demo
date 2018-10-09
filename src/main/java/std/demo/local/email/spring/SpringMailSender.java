package std.demo.local.email.spring;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class SpringMailSender {
	static JavaMailSenderImpl javaMailSender;

	/**
	 * JDK8无效
	 * 
	 * @throws MessagingException
	 */
	public static void main(String[] args) throws MessagingException {

		SpringMailService s = new SpringMailService();

		String to = "2564425418@qq.com";

		String subject = "test";
		String text = "test";
		File file = new File("E:\\phpStudy\\WWW\\test\\test.jpg");

		String htmlText = "<body><p>Hello Html Email</p><a href=\"https://qr.alipay.com/c1x066031njpnmqaugbmldb\"><img src='cid:file'/></a></body>";
		Map<String, File> dataMap = new HashMap<>();
		dataMap.put("file", file);
		//s.sendSimpleMessage(subject, text, to);
		//s.sendMessageWithAttachment(to, subject, text, file, file.getName());
		
		s.sendRichMessage(to, subject, htmlText, dataMap);
	}
}
