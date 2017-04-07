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

		String to = "liulingzn@163.com";

		String subject = "test";
		String text = "test";
		File file = new File("C:\\Users\\sinoadmin\\Desktop\\选颜色.zip");

		String htmlText = "<body><p>Hello Html Email</p><img src='cid:file'/></body>";
		Map<String, File> dataMap = new HashMap<>();
		dataMap.put("file", file);
		s.sendSimpleMessage(subject, text, to);
		//s.sendMessageWithAttachment(to, subject, text, file, file.getName());
		
		//s.sendRichMessage(to, subject, htmlText, dataMap);

	}
}
