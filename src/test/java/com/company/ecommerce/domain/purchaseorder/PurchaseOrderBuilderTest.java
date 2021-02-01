package com.company.ecommerce.domain.purchaseorder;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.customer.CustomerId;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseOrderBuilderTest {

    private PurchaseOrderId defaultPurchaseOrderId = new PurchaseOrderId("1");
    private CustomerId defaultCustomerId = new CustomerId("1");
    private Set<PurchaseItem> defaultItemsSet = TestDomainObjectsFactory.createDefaultPurchaseItemsList();

    @Test
    public void whenBuildingPurchaseOrderWithValidData_thenPurchaseOrderShouldBeCreated() {
        PurchaseOrder order = new PurchaseOrderBuilder().with($ -> {
            $.id = defaultPurchaseOrderId;
            $.customerId = defaultCustomerId;
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.totalCost = 100;
            $.totalTaxes = 10;
            $.items = defaultItemsSet;
        }).buildPurchaseOrder();

        assertThat(order.id()).isEqualTo(defaultPurchaseOrderId);
        assertThat(order.customerId()).isEqualTo(defaultCustomerId);
        assertThat(order.deliveryAddress()).isEqualTo("Delivery Address");
        assertThat(order.deliveryAddressNotes()).isEqualTo("Delivery Address Notes");
        assertThat(order.invoiceAddress()).isEqualTo("Invoice Address");
        assertThat(order.totalCost()).isEqualTo(100);
        assertThat(order.totalTaxes()).isEqualTo(10);
        assertThat(order.items()).isNotEmpty();
        assertThat(order.items()).isEqualTo(defaultItemsSet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingPurchaseOrderWithNullId_thenValidationShouldBeThrown() {
        new PurchaseOrderBuilder().with($ -> {
            $.customerId = defaultCustomerId;
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.totalCost = 100;
            $.totalTaxes = 10;
            $.items = null;
        }).buildPurchaseOrder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingPurchaseOrderWithNullCustomerId_thenValidationShouldBeThrown() {
        new PurchaseOrderBuilder().with($ -> {
            $.id = defaultPurchaseOrderId;
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.totalCost = 100;
            $.totalTaxes = 10;
            $.items = null;
        }).buildPurchaseOrder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingPurchaseOrderWithEmptyDeliveryAddress_thenValidationShouldBeThrown() {
        new PurchaseOrderBuilder().with($ -> {
            $.id = defaultPurchaseOrderId;
            $.customerId = defaultCustomerId;
            $.deliveryAddress = "";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.totalCost = 100;
            $.totalTaxes = 10;
            $.items = null;
        }).buildPurchaseOrder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingPurchaseOrderWithNullDeliveryAddress_thenValidationShouldBeThrown() {
        new PurchaseOrderBuilder().with($ -> {
            $.id = defaultPurchaseOrderId;
            $.customerId = defaultCustomerId;
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.totalCost = 100;
            $.totalTaxes = 10;
            $.items = null;
        }).buildPurchaseOrder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingPurchaseOrderWithEmptyInvoiceAddress_thenValidationShouldBeThrown() {
        new PurchaseOrderBuilder().with($ -> {
            $.id = defaultPurchaseOrderId;
            $.customerId = defaultCustomerId;
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "";
            $.totalCost = 100;
            $.totalTaxes = 10;
            $.items = null;
        }).buildPurchaseOrder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingPurchaseOrderWithNullInvoiceAddress_thenValidationShouldBeThrown() {
        new PurchaseOrderBuilder().with($ -> {
            $.id = defaultPurchaseOrderId;
            $.customerId = defaultCustomerId;
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.totalCost = 100;
            $.totalTaxes = 10;
            $.items = null;
        }).buildPurchaseOrder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingPurchaseOrderWithNegativeTotalCost_thenValidationShouldBeThrown() {
        new PurchaseOrderBuilder().with($ -> {
            $.id = defaultPurchaseOrderId;
            $.customerId = defaultCustomerId;
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.totalCost = -100;
            $.totalTaxes = 10;
            $.items = null;
        }).buildPurchaseOrder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingPurchaseOrderWithNegativeTotalTaxes_thenValidationShouldBeThrown() {
        new PurchaseOrderBuilder().with($ -> {
            $.id = defaultPurchaseOrderId;
            $.customerId = defaultCustomerId;
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.totalCost = 100;
            $.totalTaxes = -10;
            $.items = null;
        }).buildPurchaseOrder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingPurchaseOrderWithoutItems_thenValidationShouldBeThrown() {
        new PurchaseOrderBuilder().with($ -> {
            $.id = defaultPurchaseOrderId;
            $.customerId = defaultCustomerId;
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.totalCost = 100;
            $.totalTaxes = 10;
            $.items = Collections.emptySet();
        }).buildPurchaseOrder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingPurchaseOrderWithNullItems_thenValidationShouldBeThrown() {
        new PurchaseOrderBuilder().with($ -> {
            $.id = defaultPurchaseOrderId;
            $.customerId = defaultCustomerId;
            $.deliveryAddress = "Delivery Address";
            $.deliveryAddressNotes = "Delivery Address Notes";
            $.invoiceAddress = "Invoice Address";
            $.totalCost = 100;
            $.totalTaxes = 10;
        }).buildPurchaseOrder();
    }

}