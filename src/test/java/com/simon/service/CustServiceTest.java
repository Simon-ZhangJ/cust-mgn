package com.simon.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.simon.configuration.AppConfig;
import com.simon.entity.Cust;
import com.simon.entity.EmailAddress;
import com.simon.repository.CustRepository;
import com.simon.repository.EmailLogRepository;
import com.simon.repository.EmailTemplateRepository;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(classes = {AppConfig.class})  
public class CustServiceTest {
    @Mock
    private CustRepository custRepository;
    
    @Mock
    private EmailTemplateRepository emailTemplateRepository;

    @Mock
    private EmailLogRepository emailLogRepository;
    
    private CustService custService;
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        
        custService = new CustService(custRepository, emailTemplateRepository, emailLogRepository);
    }
    
    @Test
    public void createCustTest() {
        
        Cust mockCust = new Cust("Simon", "Zhang");
        mockCust.setId(1L);
        List<EmailAddress> mockEmailAddrList = new ArrayList<>();
        EmailAddress mockEailAddr = new EmailAddress();
        mockEailAddr.setEmail("simon.zhang@gmail.com");
        mockEmailAddrList.add(mockEailAddr);
        mockCust.setEmailAddressList(mockEmailAddrList);
        
        Cust param = new Cust();
        param.setFirstName("Simon");
        param.setLastName("Zhang");
        
        List<EmailAddress> emailAddrList = new ArrayList<>();
        
        EmailAddress emailAddr = new EmailAddress();
        emailAddr.setEmail("simon.zhang@gmail.com");
        emailAddrList.add(emailAddr);
        
        param.setEmailAddressList(emailAddrList);
        
        when(custRepository.save(argThat(new IsAnyCust()))).thenReturn(mockCust);

        Cust cust = custService.createCust(param);
        
        assertEquals(cust.getId(), Long.valueOf(1L));
        assertTrue(cust.getEmailAddressList().size() == 1);
    }
    
    class IsAnyCust extends ArgumentMatcher<Cust> {
        public boolean matches(Object cust) {
            return cust.getClass() == Cust.class;
        }
     }
}
