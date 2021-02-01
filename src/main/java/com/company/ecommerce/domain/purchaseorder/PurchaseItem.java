package com.company.ecommerce.domain.purchaseorder;

import com.company.ecommerce.domain.cart.CartItem;
import com.company.ecommerce.domain.product.ProductId;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class PurchaseItem implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Embedded
    private ProductId productId;
    @Embedded
    private PurchaseOrderId purchaseOrderId;
    private int quantity;

    public PurchaseItem() {
        super();
    }

    public PurchaseItem(ProductId productId, PurchaseOrderId purchaseOrderId, int quantity) {
        setProductId(productId);
        setPurchaseOrderId(purchaseOrderId);
        setQuantity(quantity);
    }

    public PurchaseItem(CartItem cartItem, PurchaseOrderId purchaseOrderId) {
        setQuantity(cartItem.quantity());
        setProductId(cartItem.productId());
        setPurchaseOrderId(purchaseOrderId);
    }

    public ProductId productId() {
        if (Objects.isNull(productId)) {
            return null;
        }
        return new ProductId(productId.id());
    }

    public PurchaseOrderId purchaseOrderId() {
        if (Objects.isNull(purchaseOrderId)) {
            return null;
        }
        return new PurchaseOrderId(purchaseOrderId.id());
    }

    public int quantity() {
        return quantity;
    }

    private void setProductId(ProductId productId) {
        if (Objects.isNull(productId)) {
            throw new IllegalArgumentException("Product Id is Required");
        }
        this.productId = productId;
    }

    private void setPurchaseOrderId(PurchaseOrderId purchaseOrderId) {
        if (Objects.isNull(purchaseOrderId)) {
            throw new IllegalArgumentException("Purchase Order Id is Required");
        }
        this.purchaseOrderId = purchaseOrderId;
    }

    private void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("The quantity should be grater than zero");
        }
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseItem that = (PurchaseItem) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(purchaseOrderId, that.purchaseOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, purchaseOrderId);
    }
}
