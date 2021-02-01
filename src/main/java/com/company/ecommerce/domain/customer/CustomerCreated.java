package com.company.ecommerce.domain.customer;

import com.company.ecommerce.domain.event.DomainEvent;

import java.time.Instant;
import java.util.Objects;

public class CustomerCreated implements DomainEvent {

    private Instant occurredOn;
    private Customer customer;

    public CustomerCreated() {
        super();
    }

    public CustomerCreated(Customer customer) {
        this.occurredOn = Instant.now();
        setCustomer(customer);
    }

    private void setCustomer(Customer customer) {
        if (Objects.isNull(customer)) {
            throw new IllegalArgumentException("Customer Created Event cannot be created with empty Customer");
        }
        this.customer = customer;
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }

    public Customer customer() {
        return customer;
    }
}
