package com.camplex.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CamPlexProjectBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamPlexProjectBootApplication.class, args);
	}

}
