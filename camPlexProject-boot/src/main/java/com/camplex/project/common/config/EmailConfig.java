package com.camplex.project.common.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:/config.properties")
public class EmailConfig {
	
	@Value("${spring.mail.username}")
	private String username;
	
	@Value("${spring.mail.password}")
	private String password;
	
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		// 메일 관련 설정
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		
		Properties prop = new Properties();
		
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.starttls.enable", "true");
		prop.setProperty("mail.debug", "true");
		prop.setProperty("mail.smtp.ssl.trust","smtp.gmail.com");
		prop.setProperty("mail.smtp.ssl.protocols","TLSv1.2");
		
		mailSender.setJavaMailProperties(prop);


		
		return mailSender; 
		
		
	}
	

	
}
