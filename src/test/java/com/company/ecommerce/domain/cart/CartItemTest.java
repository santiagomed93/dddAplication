package com.company.ecommerce.domain.cart;

import com.company.ecommerce.domain.product.ProductId;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CartItemTest {

    private CartId defaultCartId;
    private ProductId defaultProductId;

    @Before
    public void setUp() {
        defaultCartId = new CartId("cartid");
        defaultProductId = new ProductId("productid");
    }

    @Test
    public void whenInstantiateWithDefaultConstructor_thenAllPropertiesShouldBeEmpty() {
        CartItem cartItem = new CartItem();

        assertThat(cartItem.cartId()).isEqualTo(null);
        assertThat(cartItem.cost()).isEqualTo(0);
        assertThat(cartItem.productId()).isEqualTo(null);
        assertThat(cartItem.quantity()).isEqualTo(0);
        assertThat(cartItem.tax()).isEqualTo(0);
    }

    @Test
    public void whenInstantiateWithAllPropertiesConstructor_thenAllPropertiesShouldNotBeEmpty() {
        CartItem cartItem = new CartItem(defaultCartId, defaultProductId, 100, 7, 2);

        assertThat(cartItem.cartId()).isEqualTo(defaultCartId);
        assertThat(cartItem.cost()).isEqualTo(100);
        assertThat(cartItem.productId()).isEqualTo(defaultProductId);
        assertThat(cartItem.quantity()).isEqualTo(2);
        assertThat(cartItem.tax()).isEqualTo(7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNullCartId_thenValidationShouldBeThrown() {
        new CartItem(null, defaultProductId, 100, 7, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithProductIdEqualsToNull_thenValidationShouldBeThrown() {

        new CartItem(defaultCartId, null, 100, 7, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNegativeCost_thenValidationShouldBeThrown() {
        new CartItem(defaultCartId, defaultProductId, -100, 7, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNegativeTax_thenValidationShouldBeThrown() {
        new CartItem(defaultCartId, defaultProductId, 100, -7, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithQuantityEqualsToZero_thenValidationShouldBeThrown() {
        new CartItem(defaultCartId, defaultProductId, 100, 7, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNegativeQuantity_thenValidationShouldBeThrown() {
        new CartItem(defaultCartId, defaultProductId, 100, 7, -2);
    }

}