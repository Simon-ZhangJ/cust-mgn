package com.simon.service;

import org.springframework.stereotype.Service;

import com.simon.entity.EmailAddress;
import com.simon.exception.DataNotFoundException;
import com.simon.repository.EmailRepository;

@Service
public class EmailService {
    private EmailRepository emailRepository;
    
    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }
    
    /**
     * 
     * @param emailAddressId
     * @return -1:EmailAddressNotFound, 0-success
     */
    public void validate(String emailAddressId) {
        EmailAddress emailAddress = this.emailRepository.findOne(emailAddressId);
        if (emailAddress == null) {
            throw new DataNotFoundException("email address [" + emailAddressId + "] does not exists");
        }
        
        emailAddress.validate();
        
        this.emailRepository.saveAndFlush(emailAddress);
    }
}
