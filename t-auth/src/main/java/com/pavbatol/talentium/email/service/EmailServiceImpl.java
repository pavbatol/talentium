package com.pavbatol.talentium.email.service;

import com.pavbatol.talentium.app.exception.SendingMailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${app.mail.sender:unknown}")
    private String sender;
    private final JavaMailSender javaMailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) throws SendingMailException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new SendingMailException("Failed to send email to " + to, ex.getMessage());
        }
    }
}

