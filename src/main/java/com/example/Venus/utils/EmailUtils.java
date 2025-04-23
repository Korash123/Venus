package com.example.Venus.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Date;

/*
    @created 04/06/2025 3:16 PM
    @project users
    @author korash.waiba
*/

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailUtils {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Value("${spring.mail.username}")
    private String senderEmail;




    @Async
    public void sendResetLinkEmail(String email, String fullName, String url,Long resetLinkExpiration, Boolean isResetPassword) throws MessagingException, UnsupportedEncodingException {
        log.info("Sending reset link to {} ", email);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(senderEmail);
        helper.setTo(email);
        helper.setSentDate(new Date());

        if(isResetPassword){
            helper.setSubject("Password Reset Link");
        }else{
            helper.setSubject("Password Set Link");
        }

        Context context = new Context();
        context.setVariable("fullName", fullName);
        context.setVariable("url", url);
        context.setVariable("resetLinkExpiration", resetLinkExpiration);
        String htmlTemplate = springTemplateEngine.process("mail_templates/reset_link_email", context);

        helper.setText(htmlTemplate, true);

        javaMailSender.send(message);
        log.info("Password Email sent successfully to user  {}", email);
    }

    @Async
    public void sendAdminNotificationEmail(String adminEmail, String fullName, String documentType) throws MessagingException, UnsupportedEncodingException {
        log.info("Sending admin notification to {} about the document update.", adminEmail);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(senderEmail);
        helper.setTo(adminEmail);
        helper.setSentDate(new Date());
        helper.setSubject("Education Document Updated");

        Context context = new Context();
        context.setVariable("fullName", fullName);
        context.setVariable("documentType", documentType);
        String htmlTemplate = springTemplateEngine.process("mail_templates/admin_notification_email", context);

        helper.setText(htmlTemplate, true);

        javaMailSender.send(message);
        log.info("Admin email sent successfully to {}", adminEmail);
    }

    @Async
    public void sendEnquiryNotificationEmail(String adminEmail, String name, String phoneNumber, String email, String program) throws MessagingException, UnsupportedEncodingException {
        log.info("Sending enquiry notification to admin: {}", adminEmail);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(senderEmail);
        helper.setTo(adminEmail);
        helper.setSentDate(new Date());
        helper.setSubject("New Enquiry Received");

        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("phoneNumber", phoneNumber);
        context.setVariable("email", email);
        context.setVariable("program", program);

        String htmlTemplate = springTemplateEngine.process("mail_templates/enquiry_notification_email", context);
        helper.setText(htmlTemplate, true);

        javaMailSender.send(message);
        log.info("Enquiry email sent to admin successfully");
    }





}
