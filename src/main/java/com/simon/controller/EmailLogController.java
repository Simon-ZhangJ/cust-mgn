package com.simon.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simon.entity.EmailLog;
import com.simon.service.EmailLogService;

@RestController
@RequestMapping("/emaillogs")
@Transactional
public class EmailLogController {
    
    private final EmailLogService emailLogService;
    
    public EmailLogController(EmailLogService emailLogService) {
        this.emailLogService = emailLogService;
    }
    
    @GetMapping(value = "/page/{pageId}")
    public Page<EmailLog> query(@PathVariable("pageId") int pageId) {
        
        Page<EmailLog> page = this.emailLogService.queryEmailLogByPage(pageId-1, 2);
        return page;
    }
    
    @GetMapping
    public List<EmailLog> logs() {
        return this.emailLogService.queryAll();
    }
}
