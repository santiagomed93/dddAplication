package com.company.ecommerce.domain.customer;

import com.company.ecommerce.TestDomainObjectsFactory;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {

    @Before
    public void setUp() {

    }

    @Test
    public void whenInstantiateWithDefaultConstructor_thenPropertiesShouldBeNull() {
        Customer customer = new Customer();

        assertThat(customer.country()).isEqualTo(null);
        assertThat(customer.creditCards()).isEqualTo(null);
        assertThat(customer.email()).isEqualTo(null);
        assertThat(customer.name()).isEqualTo(null);
        assertThat(customer.id()).isEqualTo(null);
    }

    @Test
    public void whenAddingCreditCardToCustomer_thenTheCreditCardShouldBeAdded() {
        Customer customer = TestDomainObjectsFactory.createDefaultCustomer();
        CreditCard creditCard = TestDomainObjectsFactory.createCreditCardWithCustomCardNumberAndCustomerId("123456789", customer.id());
        customer.addCreditCard(creditCard);

        assertThat(customer.creditCards()).isNotEmpty();
        assertThat(customer.creditCards().size()).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddingCreditCardThatAlreadyExists_thenValidationShouldBeThrown() {
        Customer customer = TestDomainObjectsFactory.createDefaultCustomerWithCreditCards();
        CreditCard creditCard = TestDomainObjectsFactory.createCreditCardWithCustomCardNumberAndCustomerId("1234567890", customer.id());

        customer.addCreditCard(creditCard);
    }

    @Test
    public void whenRemovingCreditCardFromCustomer_thenTheCreditCardShouldBeRemoved() {
        Customer customer = TestDomainObjectsFactory.createDefaultCustomerWithCreditCards();
        assertThat(customer.creditCards()).isNotEmpty();
        assertThat(customer.creditCards().size()).isEqualTo(2);

        customer.removeCreditCardByNumber("1234567890");

        assertThat(customer.creditCards()).isNotEmpty();
        assertThat(customer.creditCards().size()).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenRemovingCreditCardFromCustomerThatDoesNotExists_thenValidationShouldBeThrown() {
        Customer customer = TestDomainObjectsFactory.createDefaultCustomerWithCreditCards();

        customer.removeCreditCardByNumber("777777777");
    }

}
