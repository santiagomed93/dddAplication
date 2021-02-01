package com.company.ecommerce.domain.purchaseorder;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseOrderTest {

    @Test
    public void whenInstantiateWithDefaultConstructor_thenAllPropertiesShouldBeNull() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        assertThat(purchaseOrder.items()).isEqualTo(null);
        assertThat(purchaseOrder.totalTaxes()).isEqualTo(0);
        assertThat(purchaseOrder.totalCost()).isEqualTo(0);
        assertThat(purchaseOrder.invoiceAddress()).isEqualTo(null);
        assertThat(purchaseOrder.deliveryAddressNotes()).isEqualTo(null);
        assertThat(purchaseOrder.deliveryAddress()).isEqualTo(null);
        assertThat(purchaseOrder.customerId()).isEqualTo(null);
        assertThat(purchaseOrder.id()).isEqualTo(null);
    }

}