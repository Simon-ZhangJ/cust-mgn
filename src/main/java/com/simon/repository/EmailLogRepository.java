package com.simon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simon.entity.EmailLog;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {

}
