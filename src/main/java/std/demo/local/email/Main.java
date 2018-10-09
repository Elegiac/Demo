/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package std.demo.local.email;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author Administrator
 */
public class Main {
	public static String send(String pwd, String subject, String content) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.qq.com");
		mailInfo.setMailServerPort("587");
		mailInfo.setValidate(true);
		mailInfo.setUserName("1024925667@qq.com");
		mailInfo.setPassword(pwd);// 您的邮箱密码
		mailInfo.setFromAddress("1024925667@qq.com");
		mailInfo.setToAddress("1024925667@qq.com");
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		// 这个类主要来发送邮件
		// System.out.println(mailInfo.toString());
		SimpleMailSender sms = new SimpleMailSender();
		boolean flag = sms.sendTextMail(mailInfo);// 发送文体格式
		// sms.sendHtmlMail(mailInfo);//发送html格式
		if (flag) {
			return "发送成功----------" + mailInfo.toString();
		}
		return "发送失败";
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		send("", "测试发送邮件", "测试内容");
	}
}
