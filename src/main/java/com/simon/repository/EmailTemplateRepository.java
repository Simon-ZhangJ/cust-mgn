package com.simon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simon.entity.EmailTemplate;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
    public List<EmailTemplate> findByActiveTrueOrderByCreatedTimeDesc();
}
