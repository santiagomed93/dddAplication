package com.company.ecommerce.domain.purchaseorder;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.customer.Customer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseOrderCreatedTest {

    private PurchaseOrder defaultPurchaseOrder = TestDomainObjectsFactory.createDefaultPurchaseOrder();
    private Customer defaultCustomer = TestDomainObjectsFactory.createDefaultCustomer();

    @Test
    public void whenInstantiateWithDefaultConstructor_thenPropertiesShouldBeNull() {
        PurchaseOrderCreated event = new PurchaseOrderCreated();

        assertThat(event.purchaseOrder()).isEqualTo(null);
    }

    @Test
    public void whenInstantiateWithEventConstructor_thenPropertiesShouldNotBeNull() {
        PurchaseOrderCreated event = new PurchaseOrderCreated(defaultPurchaseOrder, defaultCustomer);

        assertThat(event.purchaseOrder()).isEqualTo(defaultPurchaseOrder);
        assertThat(event.customer()).isEqualTo(defaultCustomer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNullPurchaseOrder_thenValidationShouldBeThrown() {
        new PurchaseOrderCreated(null, defaultCustomer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNullCustomer_thenValidationShouldBeThrown() {
        new PurchaseOrderCreated(defaultPurchaseOrder, null);
    }

}