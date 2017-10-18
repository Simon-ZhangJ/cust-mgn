package com.simon.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.simon.entity.EmailLog;
import com.simon.exception.DataNotFoundException;
import com.simon.repository.EmailLogRepository;

@Service
public class EmailLogService {
    private final EmailLogRepository emailLogRepository;
    
    public EmailLogService(EmailLogRepository emailLogRepository) {
        this.emailLogRepository = emailLogRepository;
    }
    
    public Page<EmailLog> queryEmailLogByPage(Integer pageIdx, Integer pageSize) {  
        Pageable pageable = new PageRequest(pageIdx, pageSize, Sort.Direction.DESC, "sentDate");  
        Page<EmailLog> page = emailLogRepository.findAll(pageable);
        
        return page;
    } 
    
    public List<EmailLog> queryAll() {
        List<EmailLog> retList = this.emailLogRepository.findAll();
        if (retList == null || retList.isEmpty()) {
            throw new DataNotFoundException("no email log found");
        }
        return retList;
    }
}
