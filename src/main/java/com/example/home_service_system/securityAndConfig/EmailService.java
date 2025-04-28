package com.example.home_service_system.securityAndConfig;

import com.example.home_service_system.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${app.baseUrl}")
    private String baseUrl;

    public void sendVerificationEmail(User user) throws MessagingException {
        String verificationLink = baseUrl + "/verify?token=" + user.getVerificationToken();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(user.getEmail());
        helper.setSubject("Verify Your Email");
        helper.setText("Click the link to verify your email: " + verificationLink, true);

        javaMailSender.send(message);
    }
}