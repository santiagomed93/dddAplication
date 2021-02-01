package com.company.ecommerce.application.customer;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.customer.Customer;
import com.company.ecommerce.domain.customer.CustomerCreated;
import com.company.ecommerce.domain.email.EmailDispatcher;
import com.company.ecommerce.domain.email.EmailMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;


@RunWith(SpringRunner.class)
public class WelcomeEmailHandlerTest {

    @TestConfiguration
    static class WelcomeEmailHandlerContextConfiguration {

        @Bean
        public WelcomeEmailHandler customerCreatedHandler() {
            return new WelcomeEmailHandler();
        }

    }

    @Autowired
    private WelcomeEmailHandler handler;

    @MockBean
    private EmailDispatcher dispatcher;

    @Test
    public void handleEvent() throws MessagingException {
        Customer customer = TestDomainObjectsFactory.createDefaultCustomer();
        CustomerCreated customerCreatedEvent = new CustomerCreated(customer);

        handler.handleEvent(customerCreatedEvent);

        Mockito.verify(dispatcher).sendEmailMessage(Mockito.any(EmailMessage.class));
        Mockito.verifyNoMoreInteractions(dispatcher);
    }
}