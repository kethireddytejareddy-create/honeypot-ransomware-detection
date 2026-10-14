package com.security.service;

import com.security.model.SecurityAlert;
import com.security.repository.SecurityAlertRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    private final SecurityAlertRepository repository;
    private final EmailService emailService;

    public AlertService(
            SecurityAlertRepository repository,
            EmailService emailService) {

        this.repository = repository;
        this.emailService = emailService;
    }

    public SecurityAlert createAlert(
            String attackerIp,
            String alertType,
            String description) {

        SecurityAlert alert = new SecurityAlert(
                attackerIp,
                alertType,
                description,
                "DETECTED",
                LocalDateTime.now()
        );

        SecurityAlert savedAlert = repository.save(alert);

        emailService.sendAlertEmail(
                attackerIp,
                description
        );

        return savedAlert;
    }

    public List<SecurityAlert> getAllAlerts() {
        return repository.findAll();
    }
}