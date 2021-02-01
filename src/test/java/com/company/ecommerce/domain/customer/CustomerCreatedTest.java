package com.company.ecommerce.domain.customer;

import com.company.ecommerce.TestDomainObjectsFactory;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerCreatedTest {

    private Customer defaultCustomer = TestDomainObjectsFactory.createDefaultCustomer();

    @Test
    public void whenInstantiateWithDefaultConstructor_thenPropertiesShouldBeNull() {
        CustomerCreated event = new CustomerCreated();

        assertThat(event.customer()).isEqualTo(null);
    }

    @Test
    public void whenInstantiateWithEventConstructor_thenPropertiesShouldNotBeNull() {
        CustomerCreated event = new CustomerCreated(defaultCustomer);

        assertThat(event.customer()).isEqualTo(defaultCustomer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNullCostumer_thenValidationShouldBeThrown() {
        new CustomerCreated(null);
    }

}
