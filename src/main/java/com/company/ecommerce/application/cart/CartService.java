package com.company.ecommerce.application.cart;

import com.company.ecommerce.application.Transformer;
import com.company.ecommerce.domain.cart.Cart;
import com.company.ecommerce.domain.cart.CartId;
import com.company.ecommerce.domain.cart.CartItem;
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
import com.company.ecommerce.domain.purchaseorder.CheckOutDataBuilder;
import com.company.ecommerce.domain.purchaseorder.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TaxCalculator taxCalculator;

    @Autowired
    private CheckOutService checkOutService;

    @Transactional
    public void createCartForCustomer(String customerId) {
        Customer customer = nonNullCustomer(customerId);
        CartId cartId = repository.generateId();
        Cart cart = new Cart(cartId, customer.id());
        repository.save(cart);
    }

    public <U> U findCustomerCart(String customerId, Transformer<Cart, U> transformer) {
        Cart cart = nonNullCustomerCart(new CustomerId(customerId));
        return transformer.transform(cart);
    }

    public <U> U findCartById(String cartId, Transformer<Cart, U> transformer) {
        Cart cart = nonNullCart(new CartId(cartId));
        return transformer.transform(cart);
    }

    @Transactional
    public void deleteCart(String cartId) {
        Cart cart = nonNullCart(new CartId(cartId));
        repository.remove(cart.id());
    }

    @Transactional
    public void addCartItem(String cartId, String productId, int quantity) {
        Cart cart = nonNullCart(new CartId(cartId));
        Product product = nonNullProduct(productId);
        CartItem cartItem = cart.createCartItem(product, quantity, taxCalculator);
        cart.addCartItem(cartItem);
        repository.save(cart);
    }

    @Transactional
    public void removeCartItemByProductId(String cartId, String productId) {
        Cart cart = nonNullCart(new CartId(cartId));
        ProductId productToRemove = new ProductId(productId);
        cart.removeCartItemByProductId(productToRemove);
        repository.save(cart);
    }

    @Transactional
    public void clearCart(String cartId) {
        Cart cart = nonNullCart(new CartId(cartId));
        cart.clearCart();
        repository.save(cart);
    }

    @Transactional
    public void updateCartItemQuantity(String cartId, String productId, int newQuantity) {
        Cart cart = nonNullCart(new CartId(cartId));
        Product product = nonNullProduct(productId);
        cart.updateCartItemQuantity(product, newQuantity, taxCalculator);
        repository.save(cart);
    }

    @Transactional
    public void checkOutCart(CartCheckOutCommand cartCheckOutCommand) {
        Cart cart = nonNullCustomerCart(new CustomerId(cartCheckOutCommand.getCustomerId()));
        Customer customer = nonNullCustomer(cartCheckOutCommand.getCustomerId());
        CheckOutData checkOutData = new CheckOutDataBuilder().with($ -> {
            $.deliveryAddress = cartCheckOutCommand.getDeliveryAddress();
            $.deliveryAddressNotes = cartCheckOutCommand.getDeliveryAddressNotes();
            $.invoiceAddress = cartCheckOutCommand.getInvoiceAddress();
            $.creditCardNumber = cartCheckOutCommand.getCreditCardNumber();
        }).buildCheckOutData();
        checkOutService.checkOutCart(cart, customer, checkOutData);
    }

    private Cart nonNullCart(CartId cartId) {
        return repository.findById(cartId).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("The Cart with id %s does not exist", cartId.id()));
        });
    }

    private Cart nonNullCustomerCart(CustomerId customerId) {
        return repository.findCartByCustomerId(customerId).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("There is no Cart for Customer with id %s", customerId.id()));
        });
    }

    private Customer nonNullCustomer(String customerId) {
        return customerRepository.findById(new CustomerId(customerId)).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("The Customer with id %s does not exist", customerId));
        });
    }

    private Product nonNullProduct(String productId) {
        ProductId id = new ProductId(productId);
        return productRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("The Product with id %s does not exist", productId));
        });
    }
}
