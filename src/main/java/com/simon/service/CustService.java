package com.simon.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.simon.entity.Cust;
import com.simon.entity.EmailAddress;
import com.simon.entity.EmailLog;
import com.simon.entity.EmailTemplate;
import com.simon.exception.DataNotFoundException;
import com.simon.repository.CustRepository;
import com.simon.repository.EmailLogRepository;
import com.simon.repository.EmailTemplateRepository;
import com.simon.util.StringUtil;

/**
 * @author Simon
 */
@Service
public class CustService {

    private final CustRepository custRepository;

    private final EmailTemplateRepository emailTemplateRepository;

    private final EmailLogRepository emailLogRepository;

    public CustService(CustRepository repository, EmailTemplateRepository emailTemplateRepository,
        EmailLogRepository emailLogRepository) {
        this.custRepository = repository;
        this.emailTemplateRepository = emailTemplateRepository;
        this.emailLogRepository = emailLogRepository;
    }

    public List<Cust> queryAllCusts() {
        List<Cust> custList = this.custRepository.findAll();
        if (custList == null || custList.isEmpty()) {
            throw new DataNotFoundException("no cust found");
        }
        
        return custList;
    }

    public List<Cust> queryCustsByPartName(String partName) {
        List<Cust> custList = this.custRepository.findByNameLikeIgnoreCase(partName.toUpperCase());
        if (custList == null || custList.isEmpty()) {
            throw new DataNotFoundException("no cust found by name [" + partName + "]");
        }
        
        return custList;
    }

    public Cust queryById(long custId) {
        Cust cust = this.custRepository.findOne(custId);
        if (cust == null) {
            throw new DataNotFoundException("cust [" + custId + "] does not exists");
        }
        return cust;
    }
    
    protected Cust initCustByParam(Cust param) {
        List<EmailAddress> emailAddrList = param.getEmailAddressList();

        Cust cust = new Cust(param.getFirstName(), param.getLastName());
        if (emailAddrList != null && !emailAddrList.isEmpty()) {
            for (EmailAddress emailAddress : emailAddrList) {
                cust.addEmailAddr(emailAddress.getEmail());
            }
        }
        
        return cust;
    }

    public Cust createCust(Cust param) {
        List<EmailAddress> emailAddrList = param.getEmailAddressList();

        Cust cust = new Cust(param.getFirstName(), param.getLastName());
        if (emailAddrList != null && !emailAddrList.isEmpty()) {
            for (EmailAddress emailAddress : emailAddrList) {
                cust.addEmailAddr(emailAddress.getEmail());
            }
        }
        
        cust = this.custRepository.save(cust);
        // send email
        if (cust.getEmailAddressList() != null && !cust.getEmailAddressList().isEmpty()) {
            for (EmailAddress emailAddress : cust.getEmailAddressList()) {
                this.mail(cust, emailAddress);
            }
        }

        return cust;
    }

    public Cust updateCust(long custId, Cust param) {
        Cust cust = this.custRepository.findOne(param.getId());
        if (cust == null) {
            throw new DataNotFoundException("cust [" + custId + "] does not exists");
        }

        if (StringUtil.isNotEmpty(param.getFirstName())) {
            cust.setFirstName(param.getFirstName());
        }

        if (StringUtil.isNotEmpty(param.getLastName())) {
            cust.setLastName(param.getLastName());
        }

        this.custRepository.saveAndFlush(cust);

        return cust;
    }

    public void deleteCust(long custId) {
        Cust cust = this.custRepository.findOne(custId);
        if (cust == null) {
            throw new DataNotFoundException("cust [" + custId + "] does not exists");
        }
        this.custRepository.delete(custId);
    }

    public Cust addEmailAddress(long custId, String emailAddr) {
        Cust cust = this.custRepository.findOne(custId);
        if (cust == null) {
            throw new DataNotFoundException("cust [" + custId + "] does not exists");
        }

        cust.addEmailAddr(emailAddr);

        this.custRepository.saveAndFlush(cust);

        for (EmailAddress emailAddress : cust.getEmailAddressList()) {
            if (emailAddress.getEmail().equals(emailAddr)) {
                // send email
                this.mail(cust, emailAddress);
            }
        }

        return cust;
    }

    public Cust deleteEmailAddress(long custId, String emailAddr) {
        Cust cust = this.custRepository.findOne(custId);
        if (cust == null) {
            throw new DataNotFoundException("cust [" + custId + "] does not exists");
        }

        boolean addrExist = false;
        if (cust.getEmailAddressList() != null) {
            for (int i = 0; i < cust.getEmailAddressList().size(); i++) {
                EmailAddress existedEmailAddr = cust.getEmailAddressList().get(i);
                if (existedEmailAddr.getEmail().equals(emailAddr)) {
                    addrExist = true;
                    cust.getEmailAddressList().remove(i);
                    this.custRepository.saveAndFlush(cust);
                    break;
                }
            }
        }

        if (!addrExist) {
            throw new DataNotFoundException("cust [" + custId + "] does not have email address [" + emailAddr + "]");
        }

        return cust;
    }

    /**
     * send a confirmation email
     * 
     * @param custId
     * @param emailAddress
     */
    private void mail(Cust cust, EmailAddress emailAddress) {
        List<EmailTemplate> templates = this.emailTemplateRepository
            .findByActiveTrueOrderByCreatedTimeDesc();

        EmailTemplate template = null;
        if (templates != null && !templates.isEmpty()) {
            template = templates.get(0);
        }

        if (template != null) {
            // send email
            System.out.println("sending email....");
            System.out.println(template.getName());
            System.out.println(template.getSubject());
            System.out.println(template.getBody());
            System.out.println("sending email....over");

            String subject = template.getSubject();
            String body = template.getBody();

            subject = subject.replaceAll("\\$\\{first_name\\}", cust.getFirstName());
            body = body.replaceAll("\\$\\{email\\}", "http://XXX.com/email/verify?id=" + emailAddress.getId());

            // TODO send email using thymeleaf

            // save to email log
            EmailLog emailLog = new EmailLog(cust.getId(), emailAddress.getEmail(), subject, body);
            emailLog.setSentDate(new Date());
            this.emailLogRepository.save(emailLog);
        }
    }
}
