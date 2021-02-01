package com.company.ecommerce.domain.purchaseorder;

import com.company.ecommerce.domain.customer.CustomerId;

import java.util.Set;
import java.util.function.Consumer;

public class PurchaseOrderBuilder {
    public PurchaseOrderId id;
    public CustomerId customerId;
    public String deliveryAddress;
    public String deliveryAddressNotes;
    public String invoiceAddress;
    public float totalCost;
    public float totalTaxes;
    public Set<PurchaseItem> items;

    public PurchaseOrderBuilder with(
            Consumer<PurchaseOrderBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public PurchaseOrder buildPurchaseOrder() {
        return new PurchaseOrder(id, customerId, deliveryAddress, deliveryAddressNotes, invoiceAddress, totalCost, totalTaxes, items);
    }

}
