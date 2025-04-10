package com.example.Venus.filter;

import com.example.Venus.contants.APP_CONSTANTS;
import com.example.Venus.exception.TokenNotFoundException;
import com.example.Venus.exception.UserInvalidCredentialsException;
import com.example.Venus.repo.TokenRepository;
import com.example.Venus.service.userImplementation.CustomUserDetailsService;
import com.example.Venus.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;
    private final CustomUserDetailsService customUserDetailsService;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        final String authHeader = request.getHeader("Authorization");
//        final String jwtToken;
//        if (authHeader == null || !authHeader.startsWith("Bearer")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        jwtToken = authHeader.substring(7);
//        final String userEmail = jwtUtils.extractUsername(jwtToken);
//
//        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            try {
//                var userDetails = customUserDetailsService.loadUserByUsername(userEmail);
//
//                if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
//                    Claims claims = jwtUtils.extractAllClaims(jwtToken);
//                    var authenticationToken = new UsernamePasswordAuthenticationToken(
//                            userDetails.getUsername(), null, userDetails.getAuthorities());
//
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                }
//            } catch (Exception e) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("Invalid JWT Token: " + e.getMessage());
//                return;
//            }
//        }
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Check for valid Authorization header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String jwtToken = authHeader.substring(7);
            final String userEmail = jwtUtils.extractUsername(jwtToken);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    var authLog = tokenRepository.findByEmailAndAccessToken(userEmail, jwtToken)
                            .orElseThrow(() -> new TokenNotFoundException("Token not found for email: " + userEmail));

//                    if (Boolean.TRUE.equals(authLog.getIsLogout()) ||
//                            !jwtUtils.isTokenValid(jwtToken, customUserDetailsService.loadUserByUsername(userEmail))) {
//
//                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                        response.getWriter().write(Boolean.TRUE.equals(authLog.getIsLogout()) ?
//                                APP_CONSTANTS.USER_LOGOUT : APP_CONSTANTS.INVALID_TOKEN);
//                        return;
//                    }

                    if (Boolean.TRUE.equals(authLog.getIsLogout()) ||
                            !jwtUtils.isTokenValid(jwtToken, customUserDetailsService.loadUserByUsername(userEmail))) {

                        throw new UserInvalidCredentialsException(
                                400,
                                Boolean.TRUE.equals(authLog.getIsLogout()) ?
                                        APP_CONSTANTS.USER_LOGOUT : APP_CONSTANTS.INVALID_TOKEN
                        );
                    }


                    var userDetails = customUserDetailsService.loadUserByUsername(userEmail);
                    Claims claims = jwtUtils.extractAllClaims(jwtToken);
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(), null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                } catch (UserInvalidCredentialsException e) {
                response.setStatus(e.getCode());
                response.getWriter().write(e.getMsg());
                return;

                } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(APP_CONSTANTS.ERROR_OCCUR + e.getMessage());
                return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }



//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        final String authHeader = request.getHeader("Authorization");
//        final String jwtToken;
//        final String requestPath = request.getRequestURI();
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        jwtToken = extractAccessTokenFromHeader(authHeader);
//
//        // Handle logout requests
//        if (requestPath.equalsIgnoreCase(URL_CONSTANTS.COMMON.LOGOUT)) {
//            handleLogout(jwtToken, response);
//            return;
//        }
//
//        final String userEmail = jwtUtils.extractUsername(jwtToken);
//
//        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            try {
//                var userDetails = customUserDetailsService.loadUserByUsername(userEmail);
//
//                if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
//                    Claims claims = jwtUtils.extractAllClaims(jwtToken);
//                    var authenticationToken = new UsernamePasswordAuthenticationToken(
//                            userDetails.getUsername(), null, userDetails.getAuthorities());
//
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                }
//            } catch (Exception e) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("Invalid JWT Token: " + e.getMessage());
//                return;
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private void handleLogout(String jwtToken, HttpServletResponse response) throws IOException {
//
//        try {
//            String email = jwtUtils.extractUsername(jwtToken);
//
//            AuthLog authLog = tokenRepository.findByEmailAndAccessToken(email, jwtToken)
//                    .orElseThrow(() -> new TokenNotFoundException("Token not found for email: " + email));
//
//            tokenRepository.delete(authLog);
//
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getWriter().write("Logout successful.");
//            log.info("User with email {} logged out successfully.", email);
//        } catch (TokenNotFoundException ex) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Token not found or already invalidated.");
//        } catch (Exception ex) {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("An error occurred during logout: " + ex.getMessage());
//        }
//    }
//
//    public String extractAccessTokenFromHeader(String authorizationHeader) {
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            return null;
//        }
//        return authorizationHeader.substring(7);
//    }



}
