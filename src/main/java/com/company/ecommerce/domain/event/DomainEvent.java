package com.company.ecommerce.domain.event;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredOn();
}
