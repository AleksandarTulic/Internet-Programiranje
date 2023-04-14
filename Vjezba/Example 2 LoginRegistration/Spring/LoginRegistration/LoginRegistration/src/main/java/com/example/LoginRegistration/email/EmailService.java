package com.example.LoginRegistration.email;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender{
	@Autowired private final JavaMailSender mailSender = null;
	private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	
	@Override
	@Async
	public void send(String to, String email) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject("Confirm your email");
			helper.setFrom("hello@amigoscode.com");
			
			mailSender.send(mimeMessage);
		}catch (Exception e) {
			LOGGER.error("Failed to send emain.", e);
			throw new IllegalStateException("Failed to send email.");
		}
	}

}
