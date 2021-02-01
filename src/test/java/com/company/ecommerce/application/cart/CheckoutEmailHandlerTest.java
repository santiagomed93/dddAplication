package com.company.ecommerce.application.cart;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.customer.Customer;
import com.company.ecommerce.domain.email.EmailDispatcher;
import com.company.ecommerce.domain.email.EmailMessage;
import com.company.ecommerce.domain.purchaseorder.PurchaseOrder;
import com.company.ecommerce.domain.purchaseorder.PurchaseOrderCreated;
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
public class CheckoutEmailHandlerTest {

    @TestConfiguration
    static class CheckoutEmailHandlerContextConfiguration {

        @Bean
        public CheckoutEmailHandler customerCreatedHandler() {
            return new CheckoutEmailHandler();
        }

    }

    @Autowired
    private CheckoutEmailHandler handler;

    @MockBean
    private EmailDispatcher dispatcher;

    @Test
    public void handleEvent() throws MessagingException {
        Customer customer = TestDomainObjectsFactory.createDefaultCustomer();
        PurchaseOrder order = TestDomainObjectsFactory.createDefaultPurchaseOrder();
        PurchaseOrderCreated purchaseOrderCreated = new PurchaseOrderCreated(order, customer);

        handler.handleEvent(purchaseOrderCreated);

        Mockito.verify(dispatcher).sendEmailMessage(Mockito.any(EmailMessage.class));
        Mockito.verifyNoMoreInteractions(dispatcher);
    }
}