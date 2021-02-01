package com.company.ecommerce.domain.cart;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Objects;

public class CartId implements Serializable {

    private String cartId;

    public CartId() {
    }

    public CartId(String cartId) {
        setCartId(cartId);
    }

    private void setCartId(String cartId) {
        if (StringUtils.isEmpty(cartId)) {
            throw new IllegalArgumentException("Cart Id is required");
        }
        this.cartId = cartId;
    }

    public String id() {
        return cartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartId cartId1 = (CartId) o;
        return Objects.equals(cartId, cartId1.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId);
    }
}
