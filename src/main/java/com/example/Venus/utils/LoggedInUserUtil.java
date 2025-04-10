package com.example.Venus.utils;

import com.example.Venus.entities.Users;
import com.example.Venus.exception.GenericException;
import com.example.Venus.exception.ResourceNotFoundException;
import com.example.Venus.exception.UnauthorizedException;
import com.example.Venus.repo.UsersRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
public class LoggedInUserUtil {
    private final UsersRepo usersRepo;
    private final MessageSourceUtils messageSourceUtils;

    public Long getLoggedInUserId() {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("Fetching the logged in user's details from DataBase.");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        try{
            //TODO: Throw an exception
            Users principal = usersRepo.findByEmail((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).orElseThrow();
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            log.info("Logged In User: {}", principal);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return principal.getId();
        }catch(Exception ex){
            throw new GenericException("Invalid credentials");
            //throw new GenericException(APP_CONSTANTS.UNAUTHORIZED, messageSourceUtils.getMessage("invalid.credentials"), AppUtil.getMethodName(), null, APP_CONSTANTS.SERVICE_NAME);
        }

    }

    public Users getLoggedInUser() {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("Fetching the logged in user's details from DB.");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        try{
            //TODO: Throw an exception
            Users principal = usersRepo.findByEmail((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).orElseThrow();
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            log.info("Logged In User: {}", principal);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return principal;
        }catch(Exception ex){
            throw new GenericException("Invalid credentials");
            //throw new GenericException(APP_CONSTANTS.UNAUTHORIZED, messageSourceUtils.getMessage("invalid.credentials"), AppUtil.getMethodName(), null, APP_CONSTANTS.SERVICE_NAME);
        }
    }
    public String getLoggedInUserIdFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Assuming the user ID is stored as the principal name (e.g., username or ID)
            return String.valueOf(authentication.getName());
        } else {
            throw new GenericException("User is not authenticated");
        }
    }

}
