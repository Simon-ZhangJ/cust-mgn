package com.simon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.simon.entity.EmailTemplate;
import com.simon.exception.DataNotFoundException;
import com.simon.repository.EmailTemplateRepository;
import com.simon.util.StringUtil;

@Service
public class EmailTemplateService {

    private final EmailTemplateRepository templateRepository;
    
    public EmailTemplateService(EmailTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }
    
    public List<EmailTemplate> queryAllTemplates() {
        return this.templateRepository.findAll();
    }
    
    public EmailTemplate createTemplate(EmailTemplate param) {
        
        EmailTemplate template = new EmailTemplate(param.getName(), param.getSubject(), param.getBody());
        
        boolean hasActiveTemplate = false;
        List<EmailTemplate> existedTemplateList = this.templateRepository.findAll();
        if (existedTemplateList != null && !existedTemplateList.isEmpty()) {
            for (EmailTemplate existedTemplate : existedTemplateList) {
                if (existedTemplate.isActive()) {
                    hasActiveTemplate = true;
                    break;
                }
            }
        }
        
        if (!hasActiveTemplate) {
            template.setActive(true);
        }
        
        return this.templateRepository.save(template);
    }
    
    public EmailTemplate updateTemplate(long id, String name, String subject, String body) {
        EmailTemplate template = this.templateRepository.findOne(id);
        if (template == null) {
            throw new DataNotFoundException("the email template [" + id + "] does not exists");
        }

        if (StringUtil.isNotEmpty(name)) {
            template.setName(name);
        }

        if (StringUtil.isNotEmpty(subject)) {
            template.setSubject(subject);
        }

        if (StringUtil.isNotEmpty(body)) {
            template.setBody(body);
        }
        
        this.templateRepository.saveAndFlush(template);
        
        return template;
    }
    
    public void activate(long id) {
        EmailTemplate template = this.templateRepository.findOne(id);
        if (template == null) {
            throw new DataNotFoundException("the email template [" + id + "] does not exists");
        }
        
        template.setActive(true);
        this.templateRepository.saveAndFlush(template);
        
        List<EmailTemplate> existedTemplateList = this.templateRepository.findAll();
        if (existedTemplateList != null && !existedTemplateList.isEmpty()) {
            for (EmailTemplate existedTemplate : existedTemplateList) {
                if (existedTemplate.isActive() && existedTemplate.getId() != id) {
                    existedTemplate.setActive(false);
                    this.templateRepository.saveAndFlush(existedTemplate);
                }
            }
        }
    }
    
    public void deleteTemplate(long id) {
        this.templateRepository.delete(id);
    }
}
