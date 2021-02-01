package com.company.ecommerce.domain.customer;

import com.company.ecommerce.domain.email.EmailGenerator;
import com.company.ecommerce.domain.email.EmailMessage;

public class CustomerEmailGenerator {

    private static final String WELCOME_MESSAGE = "Dear %s \nWelcome to E-commerce application, now you can start buying your dreams.\nSee you soon!!!!";
    private static final String WELCOME_SUBJECT = "Welcome to E-commerce Application";

    public static final EmailGenerator<CustomerCreated> welcomeEmailMessage = customerCreatedEvent -> {
        String body = String.format(WELCOME_MESSAGE, customerCreatedEvent.customer().name().fullName());
        return new EmailMessage(customerCreatedEvent.customer().email(), WELCOME_SUBJECT, body);
    };

}
