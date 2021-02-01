package com.company.ecommerce.domain.customer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerIdTest {

    @Test
    public void whenInstantiateWithDefaultConstructor_thenIdShouldBeNull() {
        CustomerId id = new CustomerId();

        assertThat(id.id()).isEqualTo(null);
    }

    @Test
    public void whenInstantiateWithIdConstructor_thenIdShouldNotBeNull() {
        CustomerId id = new CustomerId("An Id");

        assertThat(id.id()).isEqualTo("An Id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithEmptyId_thenValidationShouldBeThrown() {
        new CustomerId("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNullId_thenValidationShouldBeThrown() {
        new CustomerId(null);
    }
}
