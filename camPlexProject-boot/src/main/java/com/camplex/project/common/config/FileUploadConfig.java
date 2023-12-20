package com.camplex.project.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.MultipartConfigElement;

@Configuration
@PropertySource("classpath:/config.properties")
public class FileUploadConfig implements WebMvcConfigurer{
										// 스프링에서 웹 관련 요청/응답 모든 설정들을 할 수 있는 메서드를
										// 기본 제공
	
	@Value("${spring.servlet.multipart.file-size-threshold}")
	private long fileSizeThreshold;
	
	@Value("${spring.servlet.multipart.max-file-size}")
	private long maxFileSize;
	
	@Value("${spring.servlet.multipart.max-request-size}")
	private long maxRequestSize;
	
	@Value("${camplex.images.resourcePath}")
	private String resourcePath;
	
	@Bean
	public MultipartConfigElement configElement() {
		
		MultipartConfigFactory factory = new MultipartConfigFactory();
		
		factory.setFileSizeThreshold(DataSize.ofBytes(fileSizeThreshold));
		factory.setMaxFileSize(DataSize.ofBytes(maxFileSize));
		factory.setMaxRequestSize(DataSize.ofBytes(maxRequestSize));
		//DataSize.ofBite : long자료형을 bytes형으로 
		
		return factory.createMultipartConfig();
		
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		// 파일은 파일로, 텍스트는 텍스트로 자동 구분
		
		return new StandardServletMultipartResolver();
		
	}

	// 웹에서 사용하는 자원을 다루는 방법을 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		String webPath = "/images/**"; // /images/로 시작되는 모든 요청
		
		
		

		// /images/로 시작되는 모든 요청을 resoucePath로 연결
		registry.addResourceHandler(webPath).addResourceLocations(resourcePath);
	}
	
	
	
	
}
