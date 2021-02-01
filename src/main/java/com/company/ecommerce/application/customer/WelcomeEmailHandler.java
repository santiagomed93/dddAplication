package com.company.ecommerce.application.customer;

import com.company.ecommerce.domain.customer.CustomerCreated;
import com.company.ecommerce.domain.customer.CustomerEmailGenerator;
import com.company.ecommerce.domain.email.EmailDispatcher;
import com.company.ecommerce.domain.email.EmailMessage;
import com.company.ecommerce.domain.event.DomainEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.mail.MessagingException;

@Component
public class WelcomeEmailHandler implements DomainEventHandler<CustomerCreated> {

    @Autowired
    private EmailDispatcher dispatcher;

    @Override
    @TransactionalEventListener
    public void handleEvent(CustomerCreated event) throws MessagingException {
        EmailMessage message = CustomerEmailGenerator.welcomeEmailMessage.generate(event);
        dispatcher.sendEmailMessage(message);
    }
}
