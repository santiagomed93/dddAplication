package com.company.ecommerce.domain.customer;

import org.springframework.util.StringUtils;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class CreditCard {
    @Id
    @GeneratedValue
    private long id;
    private String nameOnCard;
    private String cardNumber;
    private boolean active;
    private String expiry;
    @Embedded
    private CustomerId customerId;

    public CreditCard() {
        super();
    }

    CreditCard(String nameOnCard, String cardNumber, boolean active, String expiry, CustomerId customerId) {
        setNameOnCard(nameOnCard);
        setCardNumber(cardNumber);
        setActive(active);
        setExpiry(expiry);
        setCustomerId(customerId);
    }

    private void setNameOnCard(String nameOnCard) {
        if (StringUtils.isEmpty(nameOnCard)) {
            throw new IllegalArgumentException("The Name On Card is required");
        }
        this.nameOnCard = nameOnCard;
    }


    private void setCardNumber(String cardNumber) {
        if (StringUtils.isEmpty(cardNumber)) {
            throw new IllegalArgumentException("The Card Number is required");
        }
        this.cardNumber = cardNumber;
    }


    private void setActive(boolean active) {
        this.active = active;
    }


    private void setExpiry(String expiry) {
        if (StringUtils.isEmpty(expiry)) {
            throw new IllegalArgumentException("The Expiry is required");
        }
        this.expiry = expiry;
    }

    private void setCustomerId(CustomerId customerId) {
        if (Objects.isNull(customerId)) {
            throw new IllegalArgumentException("The Expiry is required");
        }
        this.customerId = customerId;
    }

    public CustomerId customerId() {
        if (Objects.isNull(customerId)) {
            return null;
        }
        return new CustomerId(customerId.id());
    }

    public String nameOnCard() {
        return nameOnCard;
    }

    public String cardNumber() {
        return cardNumber;
    }

    public String expire() {
        return expiry;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(cardNumber, that.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber);
    }
}
