package com.simon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simon.entity.EmailAddress;

public interface EmailRepository extends JpaRepository<EmailAddress, String> {
    
}
