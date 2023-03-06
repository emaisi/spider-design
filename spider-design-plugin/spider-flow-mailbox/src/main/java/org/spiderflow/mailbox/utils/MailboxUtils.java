package org.spiderflow.mailbox.utils;

import java.util.Properties;

import org.spiderflow.mailbox.model.Mail;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailboxUtils {

	public static JavaMailSenderImpl createMailSender(Mail mail) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(mail.getHost());
		sender.setPort(mail.getPort());
		sender.setUsername(mail.getUsername());
		sender.setPassword(mail.getPassword());
		sender.setDefaultEncoding("Utf-8");
		Properties p = new Properties();
		p.setProperty("mail.smtp.timeout", mail.getTimeout() + "");
		p.setProperty("mail.smtp.auth", "true");
		sender.setJavaMailProperties(p);
		return sender;
	}
}
