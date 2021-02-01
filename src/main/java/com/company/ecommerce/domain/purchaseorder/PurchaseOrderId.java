package com.company.ecommerce.domain.purchaseorder;

import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PurchaseOrderId implements Serializable {

    private String purchaseOrderId;

    public PurchaseOrderId() {
    }

    public PurchaseOrderId(String purchaseId) {
        setPurchaseOrderId(purchaseId);
    }

    private void setPurchaseOrderId(String purchaseOrderId) {
        if (StringUtils.isEmpty(purchaseOrderId)) {
            throw new IllegalArgumentException("Purchase Order Id is required");
        }
        this.purchaseOrderId = purchaseOrderId;
    }

    public String id() {
        return purchaseOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrderId that = (PurchaseOrderId) o;
        return Objects.equals(purchaseOrderId, that.purchaseOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseOrderId);
    }
}
