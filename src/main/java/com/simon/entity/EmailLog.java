package com.simon.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="EMAIL_LOG")
public class EmailLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    
    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public long getId() {
        return id;
    }

    public long getCustId() {
        return custId;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public Date getSentDate() {
        return sentDate;
    }

    @Column(name = "CUST_ID")
    private long custId;
    
    @Column(name = "EMAIL")
    private String emailAddr;
    
    @Column(name = "SUBJECT")
    private String subject;
    
    @Column(name = "BODY")
    private String body;
    
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="SENT_DATE")
    private Date sentDate;
    
    public EmailLog() {
        
    }
    
    public EmailLog(long custId, String emailAddr, String subject, String body) {
        this.custId = custId;
        this.emailAddr = emailAddr;
        this.subject = subject;
        this.body = body;
    }
}
