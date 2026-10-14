package com.security;

import com.security.service.AlertService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

@Service
public class RansomwareDetector {

    private final AlertService alertService;

    private final Path honeypotPath =
            Paths.get(System.getProperty("user.dir"), "honeypot");

    public RansomwareDetector(AlertService alertService) {
        this.alertService = alertService;
    }

    public void startWatcher() {
        if (!Files.isDirectory(honeypotPath)) {
            System.err.println("Honeypot folder not found: " + honeypotPath);
            return;
        }

        try (WatchService watchService =
                     FileSystems.getDefault().newWatchService()) {

            honeypotPath.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE
            );

            System.out.println("HONEYPOT WATCHER STARTED");
            System.out.println("Watching folder: " + honeypotPath);

            while (!Thread.currentThread().isInterrupted()) {
                WatchKey key = watchService.take();

                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                        System.err.println("Some file events may have been missed.");
                        continue;
                    }

                    Path fileName = (Path) event.context();
                    String action = event.kind().name();

                    String description =
                            "Honeypot file activity detected. Action: "
                                    + action + ", File: " + fileName;

                    System.out.println("HONEYPOT ACTIVITY DETECTED");
                    System.out.println(description);

                    try {
                        alertService.createAlert(
                                "127.0.0.1",
                                "HONEYPOT " + action,
                                description
                        );

                        System.out.println("Alert saved to database: " + fileName);
                    } catch (Exception e) {
                        System.err.println(
                                "Could not save alert: " + e.getMessage());
                    }
                }

                if (!key.reset()) {
                    System.err.println("Honeypot watcher stopped.");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Watcher error: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Watcher interrupted.");
        }
    }
}