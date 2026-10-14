package com.security.controller;

import com.security.service.AlertService;
import com.security.service.HoneypotService;
import com.security.service.RansomwareDetectionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/honeypot")
@CrossOrigin
public class HoneypotController {

    private final HoneypotService honeypotService;
    private final RansomwareDetectionService detectionService;
    private final AlertService alertService;

    public HoneypotController(
            HoneypotService honeypotService,
            RansomwareDetectionService detectionService,
            AlertService alertService) {

        this.honeypotService = honeypotService;
        this.detectionService = detectionService;
        this.alertService = alertService;
    }

    @PostMapping("/access")
    public Map<String, Object> accessHoneypot(
            @RequestParam String fileName,
            HttpServletRequest request) {

        String ip = request.getRemoteAddr();

        honeypotService.recordAccess(ip, fileName);

        int count = honeypotService.getAccessCount(ip, fileName);

        boolean suspicious =
                detectionService.isSuspiciousExtension(fileName)
                        || detectionService.isRapidModification(count);

        if (suspicious) {

            alertService.createAlert(
                    ip,
                    "RANSOMWARE_ACTIVITY",
                    "Suspicious honeypot activity detected: " + fileName
            );

            return Map.of(
                    "status", "ALERT",
                    "message", "Suspicious activity detected. Access blocked."
            );
        }

        return Map.of(
                "status", "OK",
                "message", "Request processed normally."
        );
    }
}