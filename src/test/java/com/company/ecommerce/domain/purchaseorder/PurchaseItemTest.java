package com.company.ecommerce.domain.purchaseorder;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.cart.CartItem;
import com.company.ecommerce.domain.product.ProductId;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseItemTest {

    private ProductId defaultProductId = new ProductId("1");
    private PurchaseOrderId defaultPurchaseOrderId = new PurchaseOrderId("1");

    @Test
    public void whenInstantiateWithDefaultConstructor_thenAllPropertiesShouldBeEmpty() {
        PurchaseItem purchaseItem = new PurchaseItem();

        assertThat(purchaseItem.productId()).isEqualTo(null);
        assertThat(purchaseItem.purchaseOrderId()).isEqualTo(null);
        assertThat(purchaseItem.quantity()).isEqualTo(0);
    }

    @Test
    public void whenInstantiateWithAllPropertiesConstructor_thenAllPropertiesShouldNotBeEmpty() {
        PurchaseItem purchaseItem = new PurchaseItem(defaultProductId, defaultPurchaseOrderId, 5);

        assertThat(purchaseItem.productId()).isEqualTo(defaultProductId);
        assertThat(purchaseItem.purchaseOrderId()).isEqualTo(defaultPurchaseOrderId);
        assertThat(purchaseItem.quantity()).isEqualTo(5);
    }

    @Test
    public void whenCreatingPurchaseItemFromCart_thenPurchaseItemShouldBeCreated() {
        CartItem cartItem = TestDomainObjectsFactory.createDefaultCartItem();

        PurchaseItem purchaseItem = new PurchaseItem(cartItem, defaultPurchaseOrderId);

        assertThat(purchaseItem.productId()).isEqualTo(cartItem.productId());
        assertThat(purchaseItem.purchaseOrderId()).isEqualTo(defaultPurchaseOrderId);
        assertThat(purchaseItem.quantity()).isEqualTo(cartItem.quantity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingPurchaseItemWithNullProductId_thenValidationShouldBeThrown() {
        new PurchaseItem(null, defaultPurchaseOrderId, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingPurchaseItemWithNullPurchaseOrderId_thenValidationShouldBeThrown() {
        new PurchaseItem(defaultProductId, null, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingPurchaseItemWithNegativeQuantity_thenValidationShouldBeThrown() {
        new PurchaseItem(defaultProductId, defaultPurchaseOrderId, -5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingPurchaseItemWithQuantityEqualsToZero_thenValidationShouldBeThrown() {
        new PurchaseItem(defaultProductId, defaultPurchaseOrderId, 0);
    }

}