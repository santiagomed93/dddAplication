package com.company.ecommerce.application.customer;

import java.util.Set;

public class CustomerDto {
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private long countryId;
    private Set<CreditCardDto> creditCards;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<CreditCardDto> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCardDto> creditCards) {
        this.creditCards = creditCards;
    }
}
