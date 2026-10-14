package com.security.service;

import org.springframework.stereotype.Service;

@Service
public class RansomwareDetectionService {

    public boolean isSuspiciousExtension(String fileName) {

        if (fileName == null) {
            return false;
        }

        String lower = fileName.toLowerCase();

        return lower.endsWith(".encrypted")
                || lower.endsWith(".locked")
                || lower.endsWith(".enc")
                || lower.endsWith(".crypt");
    }

    public boolean isRapidModification(int modificationCount) {

        return modificationCount >= 5;
    }
}