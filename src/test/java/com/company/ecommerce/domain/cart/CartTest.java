package com.company.ecommerce.domain.cart;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.countrytax.TaxCalculator;
import com.company.ecommerce.domain.customer.CustomerId;
import com.company.ecommerce.domain.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class CartTest {
    private CartId defaultCartId;
    private CustomerId defaultCustomerId;
    private TaxCalculator taxDomainService;

    @Before
    public void setUp() {
        defaultCartId = new CartId("1");
        defaultCustomerId = new CustomerId("1");
        taxDomainService = Mockito.mock(TaxCalculator.class);
        Mockito.when(taxDomainService.calculateTax(Mockito.any(Product.class), Mockito.any(CustomerId.class))).thenReturn(10.0f);
    }

    @Test
    public void whenInstantiateWithDefaultConstructor_thenAllPropertiesShouldBeEmpty() {
        Cart cart = new Cart();

        assertThat(cart.id()).isEqualTo(null);
        assertThat(cart.customerId()).isEqualTo(null);
        assertThat(cart.items()).isEqualTo(null);
    }

    @Test
    public void whenInstantiateWithAllPropertiesConstructor_thenAllPropertiesShouldNotBeEmpty() {
        Cart cart = new Cart(defaultCartId, defaultCustomerId);

        assertThat(cart.id()).isEqualTo(defaultCartId);
        assertThat(cart.customerId()).isEqualTo(defaultCustomerId);
        assertThat(cart.items()).isNotNull();
        assertThat(cart.items().size()).isEqualTo(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNullCartId_thenValidationShouldBeThrown() {
        new Cart(null, defaultCustomerId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNullCustomerId_thenValidationShouldBeThrown() {
        new Cart(defaultCartId, null);
    }

    @Test
    public void whenCreatingValidCartItem_thenCartItemShouldBeCreated() {
        Cart cart = TestDomainObjectsFactory.createDefaultCart();
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        CartItem cartItem = cart.createCartItem(product, 2, taxDomainService);

        assertThat(cartItem.cartId()).isEqualTo(cart.id());
        assertThat(cartItem.productId()).isEqualTo(product.id());
        assertThat(cartItem.cost()).isEqualTo(product.price());
        assertThat(cartItem.tax()).isEqualTo(10);
        assertThat(cartItem.quantity()).isEqualTo(2);
        Mockito.verify(taxDomainService).calculateTax(product, cart.customerId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingCartItemWithNegativeQuantity_thenValidationShouldBeThrown() {
        Cart cart = TestDomainObjectsFactory.createDefaultCart();
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        cart.createCartItem(product, -2, taxDomainService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingCartItemWithQuantityEqualsToZero_thenValidationShouldBeThrown() {
        Cart cart = TestDomainObjectsFactory.createDefaultCart();
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        cart.createCartItem(product, 0, taxDomainService);
    }

    @Test
    public void whenAddingValidCartItem_thenCartItemShouldBeAdded() {
        Cart cart = TestDomainObjectsFactory.createDefaultCart();
        Product product = TestDomainObjectsFactory.createDefaultProduct();
        CartItem cartItem = new CartItem(cart.id(), product.id(), 100, 7, 2);

        cart.addCartItem(cartItem);

        assertThat(cart.items()).isNotNull();
        assertThat(cart.items().size()).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddingCartItemThatAlreadyExist_thenCartItemShouldBeAdded() {
        Cart cart = TestDomainObjectsFactory.createCartWithItems();
        Product product = TestDomainObjectsFactory.createDefaultProduct();
        CartItem cartItem = new CartItem(cart.id(), product.id(), 100, 7, 2);

        cart.addCartItem(cartItem);
    }

    @Test
    public void whenRemovingValidCartItem_thenCartItemShouldBeRemoved() {
        Cart cart = TestDomainObjectsFactory.createCartWithItems();
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        cart.removeCartItemByProductId(product.id());

        assertThat(cart.items()).isNotNull();
        assertThat(cart.items().size()).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenRemovingCartItemThatDoesNotExist_thenValidationShouldBeThrown() {
        Cart cart = TestDomainObjectsFactory.createCartWithItems();
        Product product = TestDomainObjectsFactory.createProductWithCustomId("5");

        cart.removeCartItemByProductId(product.id());
    }

    @Test
    public void whenClearingCart_thenItemsListShouldBeEmpty() {
        Cart cart = TestDomainObjectsFactory.createCartWithItems();

        cart.clearCart();

        assertThat(cart.items()).isNotNull();
        assertThat(cart.items().size()).isEqualTo(0);
    }

    @Test
    public void whenCalculatingTotalCostForCart_thenTotalCostShouldBeReturned() {
        Cart cart = TestDomainObjectsFactory.createCartWithItems();

        float totalCost = cart.calculateTotalCost();

        assertThat(totalCost).isEqualTo(400);
    }

    @Test
    public void whenCalculatingTotalTaxForCart_thenTotalTaxShouldBeReturned() {
        Cart cart = TestDomainObjectsFactory.createCartWithItems();

        float totalTax = cart.calculateTotalTax();

        assertThat(totalTax).isEqualTo(28);
    }

    @Test
    public void whenCalculatingTotalCostForCartWithEmptyList_thenTotalCostShouldBeZero() {
        Cart cart = TestDomainObjectsFactory.createDefaultCart();

        float totalCost = cart.calculateTotalCost();

        assertThat(totalCost).isEqualTo(0);
    }

    @Test
    public void whenCalculatingTotalTaxForCartWithEmptyList_thenTotalTaxShouldBeZero() {
        Cart cart = TestDomainObjectsFactory.createDefaultCart();

        float totalTax = cart.calculateTotalTax();

        assertThat(totalTax).isEqualTo(0);
    }

    @Test
    public void whenUpdatingCartItemWithValidData_thenCartItemShouldBeUpdated() {
        Cart cart = TestDomainObjectsFactory.createCartWithItems();
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        cart.updateCartItemQuantity(product, 5, taxDomainService);

        assertThat(cart.items()).isNotNull();
        assertThat(cart.items().size()).isEqualTo(2);
        for (CartItem item : cart.items()) {
            if (item.productId() == product.id()) {
                assertThat(item.quantity()).isEqualTo(5);
            }
        }
        Mockito.verify(taxDomainService).calculateTax(product, defaultCustomerId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUpdatingCartWithNegativeQuantity_thenValidationShouldBeThrown() {
        Cart cart = TestDomainObjectsFactory.createCartWithItems();
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        cart.updateCartItemQuantity(product, -5, taxDomainService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUpdatingCartWithQuantityEqualsToZero_thenValidationShouldBeThrown() {
        Cart cart = TestDomainObjectsFactory.createCartWithItems();
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        cart.updateCartItemQuantity(product, 0, taxDomainService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUpdatingCartItemThatDoesNotExist_thenValidationShouldBeThrown() {
        Cart cart = TestDomainObjectsFactory.createCartWithItems();
        Product product = TestDomainObjectsFactory.createProductWithCustomId("5");

        cart.updateCartItemQuantity(product, 5, taxDomainService);
    }

}