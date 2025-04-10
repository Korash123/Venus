package com.example.Venus.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/*
    @created 07/05/2025 8:26 PM
    @project iam
    @author korash.waiba
*/
@Configuration
public class MessageSourceConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages"); // Load from src/main/resources
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
