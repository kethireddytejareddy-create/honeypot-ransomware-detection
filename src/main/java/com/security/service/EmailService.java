package com.security.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendAlertEmail(
            String attackerIp,
            String fileName) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("YOUR_EMAIL@gmail.com");
        message.setSubject("🚨 RANSOMWARE ALERT - Honeypot Detection");

        message.setText(
                "Suspicious ransomware activity detected!\n\n"
                        + "Attacker IP: " + attackerIp + "\n"
                        + "Suspicious File: " + fileName + "\n"
                        + "Status: DETECTED\n\n"
                        + "Honeypot system has blocked the request."
        );

        mailSender.send(message);
    }
}