package com.company.ecommerce.domain.customer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreditCardTest {

    @Test
    public void whenInstantiateWithDefaultConstructor_thenPropertiesShouldBeNull() {
        CreditCard creditCard = new CreditCard();

        assertThat(creditCard.customerId()).isEqualTo(null);
        assertThat(creditCard.cardNumber()).isEqualTo(null);
        assertThat(creditCard.expire()).isEqualTo(null);
        assertThat(creditCard.isActive()).isEqualTo(false);
        assertThat(creditCard.nameOnCard()).isEqualTo(null);
    }

}