package com.company.ecommerce.domain.event;

import javax.mail.MessagingException;

public interface DomainEventHandler<T extends DomainEvent> {
    void handleEvent(T event) throws MessagingException;
}
