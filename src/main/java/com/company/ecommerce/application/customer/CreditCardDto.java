package com.company.ecommerce.application.customer;

public class CreditCardDto {
    private String nameOnCard;
    private String cardNumber;
    private boolean active;
    private String expiry;
    private String customerId;

    public CreditCardDto(String nameOnCard, String cardNumber, boolean active, String expiry, String customerId) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.active = active;
        this.expiry = expiry;
        this.customerId = customerId;
    }

    public CreditCardDto() {
        super();
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
