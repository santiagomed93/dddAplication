package com.company.ecommerce.application.cart;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.cart.Cart;
import com.company.ecommerce.domain.cart.CartId;
import com.company.ecommerce.domain.cart.CartRepository;
import com.company.ecommerce.domain.countrytax.TaxCalculator;
import com.company.ecommerce.domain.customer.Customer;
import com.company.ecommerce.domain.customer.CustomerId;
import com.company.ecommerce.domain.customer.CustomerRepository;
import com.company.ecommerce.domain.exception.ResourceNotFoundException;
import com.company.ecommerce.domain.product.Product;
import com.company.ecommerce.domain.product.ProductId;
import com.company.ecommerce.domain.product.ProductRepository;
import com.company.ecommerce.domain.purchaseorder.CheckOutData;
import com.company.ecommerce.domain.purchaseorder.CheckOutService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CartServiceTest {

    @TestConfiguration
    static class CartServiceTestContextConfiguration {
        @Bean
        public CartService cartService() {
            return new CartService();
        }
    }

    @Autowired
    private CartService service;

    @MockBean
    private CartRepository repository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private TaxCalculator taxCalculator;

    @MockBean
    private CheckOutService checkOutService;

    private Cart defaultCart;

    @Before
    public void setUp() {
        defaultCart = TestDomainObjectsFactory.createCartWithItems();
        Mockito.when(repository.findById(Mockito.any(CartId.class))).thenReturn(Optional.of(defaultCart));
        Mockito.when(repository.findCartByCustomerId(Mockito.any(CustomerId.class))).thenReturn(Optional.of(defaultCart));
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(defaultCart));
        Mockito.when(repository.generateId()).thenReturn(new CartId("1"));

        Customer defaultCustomer = TestDomainObjectsFactory.createDefaultCustomer();
        Mockito.when(customerRepository.findById(Mockito.any(CustomerId.class))).thenReturn(Optional.of(defaultCustomer));

        Product defaultProduct = TestDomainObjectsFactory.createDefaultProduct();
        Mockito.when(productRepository.findById(Mockito.any(ProductId.class))).thenReturn(Optional.of(defaultProduct));
    }

    @Test
    public void whenCreatingValidCart_thenCartShouldBeCreated() {
        service.createCartForCustomer("1");

        Mockito.verify(repository).save(Mockito.any(Cart.class));
        Mockito.verify(repository).generateId();
        Mockito.verify(customerRepository).findById(Mockito.any(CustomerId.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenCreatingCartWhitInvalidCustomer_thenValidationShouldBeThrown() {
        Mockito.when(customerRepository.findById(Mockito.any(CustomerId.class))).thenReturn(Optional.empty());

        service.createCartForCustomer("1");
    }

    @Test
    public void whenDeletingValidCart_thenCartShouldBeDeleted() {
        service.deleteCart("1");

        Mockito.verify(repository).remove(Mockito.any(CartId.class));
        Mockito.verify(repository).findById(Mockito.any(CartId.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenDeletingCartThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(CartId.class))).thenReturn(Optional.empty());

        service.deleteCart("1");
    }

    @Test
    public void whenFindingCartWithValidId_thenCartShouldBeReturned() {
        CartDto cartDto = service.findCartById("1", CartTransformer.toCartDto);

        assertThat(cartDto.getCartId()).isEqualTo(defaultCart.id().id());
        assertThat(cartDto.getCustomerId()).isEqualTo(defaultCart.customerId().id());
        assertThat(cartDto.getItems()).isNotEmpty();
        assertThat(cartDto.getItems().size()).isEqualTo(defaultCart.items().size());
        assertThat(cartDto.getTotalCost()).isEqualTo(defaultCart.calculateTotalCost());
        assertThat(cartDto.getTotalTax()).isEqualTo(defaultCart.calculateTotalTax());
        Mockito.verify(repository).findById(Mockito.any(CartId.class));
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindingCartThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(CartId.class))).thenReturn(Optional.empty());

        service.findCartById("1", CartTransformer.toCartDto);
    }

    @Test
    public void whenFindingCustomerCartWithValidId_thenCartShouldBeReturned() {
        CartDto cartDto = service.findCustomerCart("1", CartTransformer.toCartDto);

        assertThat(cartDto.getCartId()).isEqualTo(defaultCart.id().id());
        assertThat(cartDto.getCustomerId()).isEqualTo(defaultCart.customerId().id());
        assertThat(cartDto.getItems()).isNotEmpty();
        assertThat(cartDto.getItems().size()).isEqualTo(defaultCart.items().size());
        assertThat(cartDto.getTotalCost()).isEqualTo(defaultCart.calculateTotalCost());
        assertThat(cartDto.getTotalTax()).isEqualTo(defaultCart.calculateTotalTax());
        Mockito.verify(repository).findCartByCustomerId(Mockito.any(CustomerId.class));
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindingCustomerCartThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(repository.findCartByCustomerId(Mockito.any(CustomerId.class))).thenReturn(Optional.empty());

        service.findCustomerCart("1", CartTransformer.toCartDto);
    }

    @Test
    public void whenAddingValidCartItemToCart_thenCartItemShouldBeAdded() {
        Product defaultProduct = TestDomainObjectsFactory.createProductWithCustomId("3");
        Mockito.when(productRepository.findById(Mockito.any(ProductId.class))).thenReturn(Optional.of(defaultProduct));

        service.addCartItem("1", "1", 2);

        Mockito.verify(repository).findById(Mockito.any(CartId.class));
        Mockito.verify(productRepository).findById(Mockito.any(ProductId.class));
        Mockito.verify(repository).save(Mockito.any(Cart.class));
        Mockito.verify(taxCalculator).calculateTax(Mockito.any(Product.class), Mockito.any(CustomerId.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenAddingCartItemForProductThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(productRepository.findById(Mockito.any(ProductId.class))).thenReturn(Optional.empty());

        service.addCartItem("1", "1", 2);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenAddingCartItemForCartThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(CartId.class))).thenReturn(Optional.empty());

        service.addCartItem("1", "1", 2);
    }

    @Test
    public void whenRemovingValidCartItemFromCart_thenCartItemShouldBeRemoved() {
        service.removeCartItemByProductId("1", "1");

        Mockito.verify(repository).findById(Mockito.any(CartId.class));
        Mockito.verify(repository).save(Mockito.any(Cart.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenRemovingCartItemFromCartThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(CartId.class))).thenReturn(Optional.empty());

        service.removeCartItemByProductId("1", "1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenRemovingCartItemForProductThatDoesNotExist_thenValidationShouldBeThrown() {
        service.removeCartItemByProductId("1", "5");
    }

    @Test
    public void whenClearingCart_thenCartItemShouldBeRemoved() {
        service.clearCart("1");

        Mockito.verify(repository).findById(Mockito.any(CartId.class));
        Mockito.verify(repository).save(Mockito.any(Cart.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenClearingCartThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(CartId.class))).thenReturn(Optional.empty());

        service.clearCart("1");
    }

    @Test
    public void whenUpdatingValidCartItemFromCart_thenCartItemShouldBeUpdated() {
        service.updateCartItemQuantity("1", "1", 2);

        Mockito.verify(repository).findById(Mockito.any(CartId.class));
        Mockito.verify(productRepository).findById(Mockito.any(ProductId.class));
        Mockito.verify(repository).save(Mockito.any(Cart.class));
        Mockito.verify(taxCalculator).calculateTax(Mockito.any(Product.class), Mockito.any(CustomerId.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUpdatingCartItemThatDoesNotExist_thenValidationShouldBeThrown() {
        Product product = TestDomainObjectsFactory.createProductWithCustomId("5");
        Mockito.when(productRepository.findById(Mockito.any(ProductId.class))).thenReturn(Optional.of(product));

        service.updateCartItemQuantity("1", "5", 2);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenUpdatingCartItemForProductThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(productRepository.findById(Mockito.any(ProductId.class))).thenReturn(Optional.empty());

        service.updateCartItemQuantity("cartid", "1", 2);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenUpdatingCartItemForCartThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(CartId.class))).thenReturn(Optional.empty());

        service.addCartItem("cartid", "1", 2);
    }


    @Test
    public void whenCheckingOutCart_thenPurchaseOrderShouldBeCreated() {
        CartCheckOutCommand cartCheckOutCommand = TestDomainObjectsFactory.createCartCheckOutCommand();

        service.checkOutCart(cartCheckOutCommand);

        Mockito.verify(repository).findCartByCustomerId(Mockito.any(CustomerId.class));
        Mockito.verify(customerRepository).findById(Mockito.any(CustomerId.class));
        Mockito.verify(checkOutService).checkOutCart(Mockito.any(Cart.class), Mockito.any(Customer.class), Mockito.any(CheckOutData.class));
    }

}