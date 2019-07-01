package com.rohan.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MailUtil {
	
	@Value("${fromEmailId}")
	private String fromMailId;
	@Value("${fromName}")
	private String fromName;
	@Value("${pass}")
	private String pass;
	@Value("${mailhost}")
	private String host;
	@Value("${mailport}")
	private String port;
	
	@Autowired
	private ThymeleafUtil thymeleafUtil;
	
	@Async
	public void sendMailViaGmail(String toEmailId, String subject, EmailDto emailDto) {
		
		
		
		String to = toEmailId;
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.socketFactory.port", port);
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", port);
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromMailId, pass);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			helper.setTo(to);
			helper.setFrom(new InternetAddress(fromMailId, fromName));
			helper.setTo(new InternetAddress(to));
			helper.setSubject(subject);
			String html = thymeleafUtil.getProcessedHtml(new ObjectMapper().convertValue(emailDto, Map.class), "profile_update");
			helper.setText(html, true);
			Transport.send(message);
		} catch (MessagingException | UnsupportedEncodingException mex) {
			mex.printStackTrace();
			System.out.println("mail not sent on email id:::" + toEmailId);
					
		}
	}

}
