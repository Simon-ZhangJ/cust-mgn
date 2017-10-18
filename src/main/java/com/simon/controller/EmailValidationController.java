package com.simon.controller;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simon.service.EmailService;

@RestController
@RequestMapping("/email")
@Transactional
public class EmailValidationController {
    
    private final EmailService emailService;
    
    public EmailValidationController(EmailService emailService) {
        this.emailService = emailService;
    }
    
    @PutMapping(value = "/verify")
    public void verify(@RequestParam("id") String id) {
        System.out.println(id);
        this.emailService.validate(id);
    }
}
