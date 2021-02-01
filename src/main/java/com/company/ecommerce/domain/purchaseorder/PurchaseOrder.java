package com.company.ecommerce.domain.purchaseorder;

import com.company.ecommerce.domain.customer.CustomerId;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
public class PurchaseOrder {
    @EmbeddedId
    private PurchaseOrderId id;
    @Embedded
    private CustomerId customerId;
    private String deliveryAddress;
    private String deliveryAddressNotes;
    private String invoiceAddress;
    private float totalCost;
    private float totalTaxes;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "purchaseOrderId")
    private Set<PurchaseItem> items;

    public PurchaseOrder() {
        super();
    }

    public PurchaseOrder(PurchaseOrderId id, CustomerId customerId, String deliveryAddress, String deliveryAddressNotes, String invoiceAddress, float totalCost, float totalTaxes, Set<PurchaseItem> items) {
        setId(id);
        setCustomerId(customerId);
        setDeliveryAddress(deliveryAddress);
        setDeliveryAddressNotes(deliveryAddressNotes);
        setInvoiceAddress(invoiceAddress);
        setTotalCost(totalCost);
        setTotalTaxes(totalTaxes);
        setItems(items);
    }

    public PurchaseOrderId id() {
        if (Objects.isNull(id)) {
            return null;
        }
        return new PurchaseOrderId(id.id());
    }

    public CustomerId customerId() {
        if (Objects.isNull(customerId)) {
            return null;
        }
        return new CustomerId(customerId.id());
    }

    public String deliveryAddress() {
        return deliveryAddress;
    }

    public String deliveryAddressNotes() {
        return deliveryAddressNotes;
    }

    public String invoiceAddress() {
        return invoiceAddress;
    }

    public float totalCost() {
        return totalCost;
    }

    public float totalTaxes() {
        return totalTaxes;
    }

    public Set<PurchaseItem> items() {
        if (Objects.isNull(items)) {
            return null;
        }
        return Collections.unmodifiableSet(items);
    }

    private void setId(PurchaseOrderId id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Purchase Order Id is Required");
        }
        this.id = id;
    }

    private void setCustomerId(CustomerId customerId) {
        if (Objects.isNull(customerId)) {
            throw new IllegalArgumentException("Customer Id is Required");
        }
        this.customerId = customerId;
    }

    private void setDeliveryAddress(String deliveryAddress) {
        if (StringUtils.isEmpty(deliveryAddress)) {
            throw new IllegalArgumentException("Delivery Address is Required");
        }
        this.deliveryAddress = deliveryAddress;
    }

    private void setDeliveryAddressNotes(String deliveryAddressNotes) {
        this.deliveryAddressNotes = deliveryAddressNotes;
    }

    private void setInvoiceAddress(String invoiceAddress) {
        if (StringUtils.isEmpty(invoiceAddress)) {
            throw new IllegalArgumentException("Invoice Address is Required");
        }
        this.invoiceAddress = invoiceAddress;
    }

    private void setTotalCost(float totalCost) {
        if (totalCost < 0) {
            throw new IllegalArgumentException("Total Cost cannot be Negative");
        }
        this.totalCost = totalCost;
    }

    private void setTotalTaxes(float totalTaxes) {
        if (totalTaxes < 0) {
            throw new IllegalArgumentException("Total Cost cannot be Negative");
        }
        this.totalTaxes = totalTaxes;
    }

    private void setItems(Set<PurchaseItem> items) {
        if (CollectionUtils.isEmpty(items)) {
            throw new IllegalArgumentException("A purchase order needs items");
        }
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrder that = (PurchaseOrder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
