package com.security.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HoneypotService {

    private final Map<String, Integer> accessCounter = new HashMap<>();

    public synchronized void recordAccess(String ip, String fileName) {

        String key = ip + ":" + fileName;

        int count = accessCounter.getOrDefault(key, 0);

        accessCounter.put(key, count + 1);
    }

    public synchronized int getAccessCount(
            String ip,
            String fileName) {

        String key = ip + ":" + fileName;

        return accessCounter.getOrDefault(key, 0);
    }
}