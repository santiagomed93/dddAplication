package com.company.ecommerce.domain.customer;

import java.util.function.Consumer;

public class CreditCardBuilder {
    public String nameOnCard;
    public String cardNumber;
    public boolean active;
    public String expiry;
    public CustomerId customerId;

    public CreditCardBuilder with(
            Consumer<CreditCardBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public CreditCard buildCreditCard() {
        return new CreditCard(nameOnCard, cardNumber, active, expiry, customerId);
    }
}
