package com.security.controller;

import com.security.model.SecurityAlert;
import com.security.service.AlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public List<SecurityAlert> getAlerts() {
        return alertService.getAllAlerts();
    }
}