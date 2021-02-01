package com.company.ecommerce.domain.event;

public interface DomainEventPublisher {
    <T extends DomainEvent> void publishEvent(T event);

    <T extends DomainEventHandler> void registerHandler(T eventHandler);
}
