package com.company.ecommerce.domain.purchaseorder;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseOrderIdTest {

    @Test
    public void whenInstantiateWithDefaultConstructor_thenIdShouldBeNull() {
        PurchaseOrderId id = new PurchaseOrderId();

        assertThat(id.id()).isEqualTo(null);
    }

    @Test
    public void whenInstantiateWithIdConstructor_thenIdShouldNotBeNull() {
        PurchaseOrderId id = new PurchaseOrderId("An Id");

        assertThat(id.id()).isEqualTo("An Id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithEmptyId_thenValidationShouldBeThrown() {
        new PurchaseOrderId("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNullId_thenValidationShouldBeThrown() {
        new PurchaseOrderId(null);
    }

}