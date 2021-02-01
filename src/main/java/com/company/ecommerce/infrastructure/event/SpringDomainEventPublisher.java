package com.company.ecommerce.infrastructure.event;

import com.company.ecommerce.domain.event.DomainEvent;
import com.company.ecommerce.domain.event.DomainEventHandler;
import com.company.ecommerce.domain.event.DomainEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringDomainEventPublisher implements DomainEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public <T extends DomainEvent> void publishEvent(T event) {
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public <T extends DomainEventHandler> void registerHandler(T eventHandler) {
        throw new UnsupportedOperationException("You do not need to register the handlers use @TransactionalEventHandler");
    }
}
