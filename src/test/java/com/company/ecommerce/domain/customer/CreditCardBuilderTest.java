package com.company.ecommerce.domain.customer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreditCardBuilderTest {

    private CustomerId defaultCustomerId = new CustomerId("1");

    @Test
    public void whenBuildingCreditCardWithValidData_thenCreditCardShouldBeCreated() {
        CreditCard creditCard = new CreditCardBuilder().with($ -> {
            $.active = true;
            $.cardNumber = "123456789";
            $.customerId = defaultCustomerId;
            $.nameOnCard = "TEST NAME";
            $.expiry = "10/99";
        }).buildCreditCard();

        assertThat(creditCard.customerId()).isEqualTo(defaultCustomerId);
        assertThat(creditCard.cardNumber()).isEqualTo("123456789");
        assertThat(creditCard.expire()).isEqualTo("10/99");
        assertThat(creditCard.isActive()).isEqualTo(true);
        assertThat(creditCard.nameOnCard()).isEqualTo("TEST NAME");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCreditCardWithNullCardNumber_thenValidationShouldBeThrown() {
        new CreditCardBuilder().with($ -> {
            $.active = true;
            $.customerId = defaultCustomerId;
            $.nameOnCard = "TEST NAME";
            $.expiry = "10/99";
        }).buildCreditCard();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCreditCardWithEmptyCardNumber_thenValidationShouldBeThrown() {
        new CreditCardBuilder().with($ -> {
            $.active = true;
            $.cardNumber = "";
            $.customerId = defaultCustomerId;
            $.nameOnCard = "TEST NAME";
            $.expiry = "10/99";
        }).buildCreditCard();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCreditCardWithNullCustomerId_thenValidationShouldBeThrown() {
        new CreditCardBuilder().with($ -> {
            $.active = true;
            $.cardNumber = "123456789";
            $.nameOnCard = "TEST NAME";
            $.expiry = "10/99";
        }).buildCreditCard();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCreditCardWithNullNameOnCard_thenValidationShouldBeThrown() {
        new CreditCardBuilder().with($ -> {
            $.active = true;
            $.cardNumber = "123456789";
            $.customerId = defaultCustomerId;
            $.expiry = "10/99";
        }).buildCreditCard();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCreditCardWithEmptyNameOnCard_thenValidationShouldBeThrown() {
        new CreditCardBuilder().with($ -> {
            $.active = true;
            $.cardNumber = "123456789";
            $.customerId = defaultCustomerId;
            $.nameOnCard = "";
            $.expiry = "10/99";
        }).buildCreditCard();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCreditCardWithNullExpiry_thenValidationShouldBeThrown() {
        new CreditCardBuilder().with($ -> {
            $.active = true;
            $.cardNumber = "123456789";
            $.customerId = defaultCustomerId;
            $.nameOnCard = "TEST NAME";
        }).buildCreditCard();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCreditCardWithEmptyExpiry_thenValidationShouldBeThrown() {
        new CreditCardBuilder().with($ -> {
            $.active = true;
            $.cardNumber = "123456789";
            $.customerId = defaultCustomerId;
            $.nameOnCard = "TEST NAME";
            $.expiry = "";
        }).buildCreditCard();
    }

}
