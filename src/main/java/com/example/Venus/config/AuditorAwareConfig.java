package com.example.Venus.config;

import com.example.Venus.utils.AuditorAwareImplUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
    @created 03/07/2025 11:51 AM
    @project iam
    @author korash.waiba
*/
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@RequiredArgsConstructor
public class AuditorAwareConfig {
    private final AuditorAwareImplUtil auditorAwareImplUtil;

    @Bean
    public AuditorAware<String> auditorProvider(){
        return auditorAwareImplUtil;
    }
}
