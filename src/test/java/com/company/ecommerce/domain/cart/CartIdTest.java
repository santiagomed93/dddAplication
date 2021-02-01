package com.company.ecommerce.domain.cart;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CartIdTest {

    @Test
    public void whenInstantiateWithDefaultConstructor_thenIdShouldBeNull() {
        CartId id = new CartId();

        assertThat(id.id()).isEqualTo(null);
    }

    @Test
    public void whenInstantiateWithIdConstructor_thenIdShouldNotBeNull() {
        CartId id = new CartId("An Id");

        assertThat(id.id()).isEqualTo("An Id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithEmptyId_thenValidationShouldBeThrown() {
        new CartId("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNullId_thenValidationShouldBeThrown() {
        new CartId(null);
    }
}