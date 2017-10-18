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
@Table(name="EMAIL_TEMPLATE")
public class EmailTemplate {
    
    public void setActive(boolean active) {
        this.active = active;
    }

    public EmailTemplate() {
        
    }
    
    public EmailTemplate(String name, String subject, String body) {
        this.name = name;
        this.subject = subject;
        this.body = body;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="SUBJECT")
    private String subject;
    
    @Column(name="BODY")
    private String body;
    
    @Column(name="ACTIVE")
    private boolean active;
    
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="CREATED_TIME")
    private Date createdTime;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public boolean isActive() {
        return active;
    }
}
