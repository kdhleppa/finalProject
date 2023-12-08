package com.camplex.project.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Configuration
//@Slf4j
//@AllArgsConstructor
//public class XssConfig implements WebMvcConfigurer {
//
//    private final ObjectMapper objectMapper;
//
//    @Bean
//    public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
//        ObjectMapper copy = objectMapper.copy();
//        copy.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
//        return new MappingJackson2HttpMessageConverter(copy);
//    }
//    
//}

