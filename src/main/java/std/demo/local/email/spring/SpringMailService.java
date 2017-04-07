package std.demo.local.email.spring;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SpringMailService {

	JavaMailSenderImpl javaMailSender;

	public SpringMailService() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"servlet-context.xml");
		javaMailSender = ac.getBean(JavaMailSenderImpl.class);
	}

	/**
	 * 发送简单邮件
	 * 
	 * @param subject
	 *            邮件标题
	 * @param text
	 *            邮件文本
	 * @param to
	 *            收件人地址
	 */
	public void sendSimpleMessage(String subject, String text, String to) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom(javaMailSender.getUsername());
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(text);
		javaMailSender.send(mail);
	}

	/**
	 * 发送带附件的邮件
	 * 
	 * @param to
	 *            收件人地址
	 * @param subject
	 *            邮件标题
	 * @param text
	 *            邮件文本
	 * @param file
	 *            附件
	 * @param fileName
	 *            附件名称
	 * @throws MessagingException
	 */
	public void sendMessageWithAttachment(String to, String subject,
			String text, File file, String fileName) throws MessagingException {
		// 使用JavaMail的MimeMessage，支付更加复杂的邮件格式和内容
		MimeMessage msg = javaMailSender.createMimeMessage();
		// 创建MimeMessageHelper对象，处理MimeMessage的辅助类
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		// 使用辅助类MimeMessage设定参数
		helper.setFrom(javaMailSender.getUsername());
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);
		// 加入附件
		helper.addAttachment(fileName, file);
		// 发送邮件
		javaMailSender.send(msg);
	}

	/**
	 * 发送富文本邮件
	 * 
	 * @param to
	 *            收件人地址
	 * @param subject
	 *            邮件标题
	 * @param htmlText
	 *            邮件内容HTML
	 * @param dataMap
	 *            替换HTML中cid的map
	 * @throws MessagingException
	 */
	public void sendRichMessage(String to, String subject, String htmlText,
			Map<String, File> dataMap) throws MessagingException {
		MimeMessage msg = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(msg, true);

		helper.setFrom(javaMailSender.getUsername());
		helper.setTo(to);
		helper.setSubject(subject);
		// 第二个参数true，表示text的内容为html，然后注意<img/>标签，src='cid:file'，'cid'是contentId的缩写，'file'是一个标记，
		// 需要在后面的代码中调用MimeMessageHelper的addInline方法替代成文件
		helper.setText(htmlText, true);

		for (Entry<String, File> entry : dataMap.entrySet()) {
			helper.addInline(entry.getKey(), entry.getValue());
		}
		javaMailSender.send(msg);
	}
}
