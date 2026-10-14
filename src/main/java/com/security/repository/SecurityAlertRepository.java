package com.security.repository;

import com.security.model.SecurityAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityAlertRepository
        extends JpaRepository<SecurityAlert, Long> {
}