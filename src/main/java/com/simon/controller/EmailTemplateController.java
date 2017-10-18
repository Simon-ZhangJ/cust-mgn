package com.simon.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simon.entity.EmailTemplate;
import com.simon.exception.BaseException;
import com.simon.service.EmailTemplateService;

@RestController
@RequestMapping("/templates")
@Transactional
public class EmailTemplateController {
    private final EmailTemplateService emailTemplateService;

    public EmailTemplateController(EmailTemplateService emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }
    
    @GetMapping
    public List<EmailTemplate> templates() {
        return this.emailTemplateService.queryAllTemplates();
    }
    
    @PostMapping(consumes = "application/json")
    @ResponseStatus(value = HttpStatus.CREATED)
    public EmailTemplate create(@RequestBody EmailTemplate param) {
        EmailTemplate template = this.emailTemplateService.createTemplate(param);
        return template;
    }
    
    @PutMapping(value = "/{id:[\\d]+}", consumes = "application/json")
    public EmailTemplate update(@PathVariable("id") int id, @RequestBody EmailTemplate param) {
        // long id = param.getId();
        
        String name = param.getName();
        String subject = param.getSubject();
        String body = param.getBody();
        
        EmailTemplate template = this.emailTemplateService.updateTemplate(id, name, subject, body);
        return template;
    }
    
    @PutMapping(value = "/activate")
    public void verify(@RequestParam("id") String id) {
        System.out.println(id);
        // this.emailService.validate(id);
        Long templateId = null;
        try {
            templateId = Long.parseLong(id);
        } 
        catch (Exception e) {
            throw new BaseException("wrong template id");
        }
        
        this.emailTemplateService.activate(templateId);
    }
    
    @DeleteMapping(value = "/{id:[\\d]+}")
    public void delete(@PathVariable("id") int id) {
        this.emailTemplateService.deleteTemplate(id);
    }
}
