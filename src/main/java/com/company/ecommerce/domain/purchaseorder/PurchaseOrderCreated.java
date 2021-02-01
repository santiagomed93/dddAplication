package com.company.ecommerce.domain.purchaseorder;

import com.company.ecommerce.domain.customer.Customer;
import com.company.ecommerce.domain.event.DomainEvent;

import java.time.Instant;
import java.util.Objects;

public class PurchaseOrderCreated implements DomainEvent {

    private PurchaseOrder purchaseOrder;
    private Customer customer;
    private Instant ocurredOn;

    public PurchaseOrderCreated() {
        super();
    }

    public PurchaseOrderCreated(PurchaseOrder purchaseOrder, Customer customer) {
        this.ocurredOn = Instant.now();
        setPurchaseOrder(purchaseOrder);
        setCustomer(customer);
    }

    private void setCustomer(Customer customer) {
        if (Objects.isNull(customer)) {
            throw new IllegalArgumentException("You cannot create a Purchase Order Created Event with a null Customer");
        }
        this.customer = customer;
    }

    private void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        if (Objects.isNull(purchaseOrder)) {
            throw new IllegalArgumentException("You cannot create a Purchase Order Created Event for a null purchase order");
        }
        this.purchaseOrder = purchaseOrder;
    }

    public Customer customer() {
        return customer;
    }

    public PurchaseOrder purchaseOrder() {
        return purchaseOrder;
    }

    @Override
    public Instant occurredOn() {
        return ocurredOn;
    }
}
