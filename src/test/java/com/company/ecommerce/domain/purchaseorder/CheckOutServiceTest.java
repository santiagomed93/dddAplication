package com.company.ecommerce.domain.purchaseorder;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.cart.Cart;
import com.company.ecommerce.domain.cart.CartRepository;
import com.company.ecommerce.domain.customer.Customer;
import com.company.ecommerce.domain.event.DomainEventPublisher;
import com.company.ecommerce.domain.exception.ResourceNotFoundException;
import com.company.ecommerce.domain.product.Product;
import com.company.ecommerce.domain.product.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class CheckOutServiceTest {

    @TestConfiguration
    static class CheckOutServiceTestContextConfiguration {

        @Bean
        public CheckOutService checkOutService() {
            return new CheckOutService(productRepository(), purchaseOrderRepository(), domainEventPublisher(), cartRepository());
        }

        @Bean
        public ProductRepository productRepository() {
            return Mockito.mock(ProductRepository.class);
        }

        @Bean
        public PurchaseOrderRepository purchaseOrderRepository() {
            return Mockito.mock(PurchaseOrderRepository.class);
        }

        @Bean
        public CartRepository cartRepository() {
            return Mockito.mock(CartRepository.class);
        }

        @Bean
        public DomainEventPublisher domainEventPublisher() {
            return Mockito.mock(DomainEventPublisher.class);
        }
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private DomainEventPublisher publisher;

    @Autowired
    private CheckOutService service;

    private Customer defaultCustomer;

    private Cart defaultCart;

    private Product firstProduct;

    private Product secondProduct;

    @Before
    public void setUp() {
        defaultCustomer = TestDomainObjectsFactory.createDefaultCustomerWithCreditCards();
        defaultCart = TestDomainObjectsFactory.createCartWithItems();

        firstProduct = TestDomainObjectsFactory.createProductWithCustomId("1");
        secondProduct = TestDomainObjectsFactory.createProductWithCustomId("2");

        Mockito.when(productRepository.findById(firstProduct.id())).thenReturn(Optional.of(firstProduct));
        Mockito.when(productRepository.findById(secondProduct.id())).thenReturn(Optional.of(secondProduct));
        Mockito.when(purchaseOrderRepository.generateId()).thenReturn(new PurchaseOrderId("1"));
    }

    @Test
    public void whenCheckingOutCart_thenPurchaseOrderShouldBeCreated() {
        CheckOutData checkOutData = TestDomainObjectsFactory.createDefaultCheckOutData();

        service.checkOutCart(defaultCart, defaultCustomer, checkOutData);

        Mockito.verify(productRepository).findById(firstProduct.id());
        Mockito.verify(productRepository).findById(secondProduct.id());
        Mockito.verify(cartRepository).save(defaultCart);
        Mockito.verify(purchaseOrderRepository).save(Mockito.any(PurchaseOrder.class));
        Mockito.verify(publisher).publishEvent(Mockito.any(PurchaseOrderCreated.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCheckingOutCartWithInvalidCreditCard_thenValidationShouldBeThrown() {
        CheckOutData checkOutData = TestDomainObjectsFactory.createCheckOutDataWithCustomCreditCardNumber("88888888888");

        service.checkOutCart(defaultCart, defaultCustomer, checkOutData);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenCheckingOutCartWithProductThatDoesNotExists_thenValidationShouldBeThrown() {
        CheckOutData checkOutData = TestDomainObjectsFactory.createCheckOutDataWithCustomCreditCardNumber("88888888888");
        Mockito.when(productRepository.findById(firstProduct.id())).thenReturn(Optional.empty());

        service.checkOutCart(defaultCart, defaultCustomer, checkOutData);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCheckingOutCartWithQuantityHigherThanProductQuantityInStock_thenValidationShouldBeThrown() {
        CheckOutData checkOutData = TestDomainObjectsFactory.createDefaultCheckOutData();
        firstProduct.decreaseQuantityInStock(99);

        service.checkOutCart(defaultCart, defaultCustomer, checkOutData);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCheckingOutCartWithoutItems_thenValidationShouldBeThrown() {
        CheckOutData checkOutData = TestDomainObjectsFactory.createDefaultCheckOutData();
        Cart cart = TestDomainObjectsFactory.createDefaultCart();

        service.checkOutCart(cart, defaultCustomer, checkOutData);
    }
}
