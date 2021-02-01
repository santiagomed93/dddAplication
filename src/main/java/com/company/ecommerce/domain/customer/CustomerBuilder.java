package com.company.ecommerce.domain.customer;

import com.company.ecommerce.domain.country.Country;
import com.company.ecommerce.domain.event.DomainEventPublisher;

import java.util.function.Consumer;

public class CustomerBuilder {
    public CustomerId id;
    public String firstName;
    public String lastName;
    public String email;
    public Country country;

    public CustomerBuilder with(
            Consumer<CustomerBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public Customer buildCustomer(DomainEventPublisher publisher) {
        Customer customer = new Customer(id, new Name(firstName, lastName), email, country);
        publisher.publishEvent(new CustomerCreated(customer));
        return customer;
    }
}
