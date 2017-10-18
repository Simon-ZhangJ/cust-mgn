package com.simon.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.simon.exception.BaseException;
import com.simon.util.StringUtil;

@Entity
@Table(name="CUST")
public class Cust {
    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    
    @Column(name="FIRST_NAME")
    private String firstName;
    
    @Column(name="LAST_NAME")
    private String lastName;
    
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="CREATED_TIME")
    private Date createdTime;
    
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="UPDATED_TIME")
    private Date updatedTime;
    
    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="CUST_ID")
    public List<EmailAddress> emailAddressList;
    
    public Cust() {
        
    }
    
    public Cust(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        Date now = new Date();
        this.createdTime = now;
        this.updatedTime = now;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<EmailAddress> getEmailAddressList() {
        return emailAddressList;
    }
    
    public void changeName(String firstName, String lastName) {
        if (StringUtil.isNotEmpty(firstName)) {
            this.firstName = firstName;
        }
        if (StringUtil.isNotEmpty(lastName)) {
            this.lastName = lastName;
        }
        
        this.updatedTime = new Date();
    }
    
    public void addEmailAddr(String emailAddr) {
        // email address can't be empty
        if (StringUtil.isEmpty(emailAddr)) {
            throw new BaseException("Email address can not be empty");
        }
        
        // validate email address
        if (!this.validateEmail(emailAddr)) {
            throw new BaseException("Invalid email address");
        }
        
        // if the address has been attached
        if (this.exist(emailAddr)) {
            throw new BaseException("The email address has been added to the customer");
        }
        if (this.emailAddressList == null) {
            this.emailAddressList = new ArrayList<>();
        }
        
        EmailAddress emailAddress = new EmailAddress(emailAddr);
        this.emailAddressList.add(emailAddress);
        
        this.updatedTime = new Date();
    }
    
    private boolean exist(String emailAddr) {
        if (this.emailAddressList != null) {
            for (EmailAddress emailAddress : this.emailAddressList) {
                if (emailAddr.equals(emailAddress.getEmail())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean validateEmail(String emailAddr) {
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(emailAddr);
        //进行正则匹配
        return m.matches();
    }

    public Date getCreatedTime() {
        return createdTime;
    }
    
}
