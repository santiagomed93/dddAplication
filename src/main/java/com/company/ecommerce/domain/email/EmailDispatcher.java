package com.company.ecommerce.domain.email;

import javax.mail.MessagingException;

public interface EmailDispatcher {
    void sendEmailMessage(EmailMessage emailMessage) throws MessagingException;
}
