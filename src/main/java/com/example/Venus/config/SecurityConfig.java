package com.example.Venus.config;

import com.example.Venus.filter.JwtFilter;
import com.example.Venus.service.userImplementation.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api-docs/**").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/api/v1/user-reg/register").permitAll()
                        .requestMatchers("/api/v1/user-reg/login").permitAll()
                        .requestMatchers("/api/v1/user-reg/forgot-password").permitAll()
                        .requestMatchers("/api/v1/user-reg/setPassword").permitAll()
                        .requestMatchers("/api/v1/user-reg/logout").permitAll()
                        .requestMatchers("/api/v1/user-reg/refreshToken").permitAll()
                        .requestMatchers("/api/v1/user-reg/get/all").permitAll()





                        .requestMatchers("/api/v1/role/**").authenticated()

                        .requestMatchers("/api/v1/event-news/get/all").permitAll()
                        .requestMatchers("/api/v1/event-news/save").authenticated()
                        .requestMatchers("/api/v1/event-news/get/{id}").authenticated()
                        .requestMatchers("/api/v1/event-news/delete/{id}").authenticated()


                        .requestMatchers("/api/v1/faculty-staffs/get/all").permitAll()
                        .requestMatchers("/api/v1/faculty-staffs/save").authenticated()
                        .requestMatchers("/api/v1/faculty-staffs/getById").authenticated()
                        .requestMatchers("/api/v1/faculty-staffs/delete/{id}").authenticated()



                        .requestMatchers("/api/v1/profile-picture/**").authenticated()

                        .requestMatchers("/api/v1/blog/get/all").permitAll()
                        .requestMatchers("/api/v1/blog/save").authenticated()
                        .requestMatchers("/api/v1/blog/getById").authenticated()
                        .requestMatchers("/api/v1/blog/delete/{id}").authenticated()

                        .requestMatchers("/api/v1/feedback/summary").permitAll()
                        .requestMatchers("/api/v1/feedback/save").authenticated()
                        .requestMatchers("/api/v1/feedback/getById").authenticated()
                        .requestMatchers("/api/v1/feedback/delete/{id}").authenticated()

                        .requestMatchers("/api/v1/pop-over-model/get/all").permitAll()
                        .requestMatchers("/api/v1/pop-over-model/save").authenticated()
                        .requestMatchers("/api/v1/pop-over-model/delete/{id}").authenticated()

                        .requestMatchers("/api/v1/enquiry/save").permitAll()
                        .requestMatchers("/api/v1/enquiry/get/all").authenticated()
                        .requestMatchers("/api/v1/enquiry/delete/{id}").authenticated()

                        .requestMatchers("/api/v1/banner/get/all").permitAll()
                        .requestMatchers("/api/v1/banner/save").authenticated()
                        .requestMatchers("/api/v1/banner/delete/{id}").authenticated()


                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}