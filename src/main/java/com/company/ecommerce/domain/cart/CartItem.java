package com.company.ecommerce.domain.cart;

import com.company.ecommerce.domain.product.ProductId;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class CartItem implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Embedded
    private CartId cartId;
    @Embedded
    private ProductId productId;
    private float cost;
    private float tax;
    private int quantity;

    public CartItem() {
        super();
    }

    public CartItem(CartId cartId, ProductId productId, float cost, float tax, int quantity) {
        setCartId(cartId);
        setProductId(productId);
        setCost(cost);
        setTax(tax);
        setQuantity(quantity);
    }

    private void setCartId(CartId cartId) {
        if (Objects.isNull(cartId)) {
            throw new IllegalArgumentException("Cart Id is Required");
        }
        this.cartId = cartId;
    }

    private void setProductId(ProductId productId) {
        if (Objects.isNull(productId)) {
            throw new IllegalArgumentException("Product Id is Required");
        }
        this.productId = productId;
    }

    private void setCost(float cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Cost should be grater than 0");
        }
        this.cost = cost;
    }

    private void setTax(float tax) {
        if (tax < 0) {
            throw new IllegalArgumentException("Tax should be grater or equals to 0");
        }
        this.tax = tax;
    }

    private void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity should be grater than 0");
        }
        this.quantity = quantity;
    }

    public CartId cartId() {
        if (Objects.isNull(cartId)) {
            return null;
        }
        return new CartId(cartId.id());
    }

    public ProductId productId() {
        if (Objects.isNull(productId)) {
            return null;
        }
        return new ProductId(productId.id());
    }

    public float cost() {
        return cost;
    }

    public float tax() {
        return tax;
    }

    public int quantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(cartId, cartItem.cartId) &&
                Objects.equals(productId, cartItem.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId);
    }
}
