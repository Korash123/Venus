package com.example.Venus.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
    @created 10/05/2025 11:52 AM
    @project iam
    @author korash.waiba
*/
@Service
@Slf4j
public class AuditorAwareImplUtil implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.of("system");
        }else{
            return Optional.of((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

    }
}
