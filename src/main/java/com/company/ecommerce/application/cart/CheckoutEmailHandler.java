package com.company.ecommerce.application.cart;

import com.company.ecommerce.domain.email.EmailDispatcher;
import com.company.ecommerce.domain.email.EmailMessage;
import com.company.ecommerce.domain.event.DomainEventHandler;
import com.company.ecommerce.domain.purchaseorder.PurchaseOrderCreated;
import com.company.ecommerce.domain.purchaseorder.PurchaseOrderEmailGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.mail.MessagingException;

@Component
public class CheckoutEmailHandler implements DomainEventHandler<PurchaseOrderCreated> {

    @Autowired
    private EmailDispatcher dispatcher;

    @Override
    @TransactionalEventListener
    public void handleEvent(PurchaseOrderCreated event) throws MessagingException {
        EmailMessage message = PurchaseOrderEmailGenerator.purchaseOrderCreatedEmailMessage.generate(event);
        dispatcher.sendEmailMessage(message);
    }
}
