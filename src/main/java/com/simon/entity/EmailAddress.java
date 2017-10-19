package com.simon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.simon.exception.BaseException;

@Entity
@Table(name="EMAIL_ADDRESS")
public class EmailAddress {
    public void setEmail(String email) {
        this.email = email;
    }

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;
    
    @Column(name="EMAIL")
    private String email;
    
    @Column(name="VALIDATED")
    private boolean validated;
    
    public EmailAddress() {
        
    }
    
    public EmailAddress(String email) {
        this.email = email;
        this.validated = false;
    }

    public String getEmail() {
        return email;
    }

    public boolean isValidated() {
        return validated;
    }
    
    public String getId() {
        return id;
    }

    public void validate() {
        if (this.isValidated()) {
            throw new BaseException("The email address has already been validated");
        }
        
        this.validated = true;
    }
}
