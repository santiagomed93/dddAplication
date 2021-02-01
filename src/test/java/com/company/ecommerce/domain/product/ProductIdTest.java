package com.company.ecommerce.domain.product;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductIdTest {
    @Test
    public void whenInstantiateWithDefaultConstructor_thenIdShouldBeNull() {
        ProductId id = new ProductId();

        assertThat(id.id()).isEqualTo(null);
    }

    @Test
    public void whenInstantiateWithIdConstructor_thenIdShouldNotBeNull() {
        ProductId id = new ProductId("An Id");

        assertThat(id.id()).isEqualTo("An Id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithEmptyId_thenValidationShouldBeThrown() {
        new ProductId("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNullId_thenValidationShouldBeThrown() {
        new ProductId(null);
    }
}
