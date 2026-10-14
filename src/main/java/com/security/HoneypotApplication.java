package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HoneypotApplication {

    public static void main(String[] args) {

        var context = SpringApplication.run(
                HoneypotApplication.class,
                args
        );

        RansomwareDetector detector =
                context.getBean(RansomwareDetector.class);

        detector.startWatcher();
    }
}