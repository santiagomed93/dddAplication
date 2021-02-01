package com.company.ecommerce.domain.cart;

import com.company.ecommerce.domain.Aggregate;
import com.company.ecommerce.domain.countrytax.TaxCalculator;
import com.company.ecommerce.domain.customer.CustomerId;
import com.company.ecommerce.domain.product.Product;
import com.company.ecommerce.domain.product.ProductId;

import javax.persistence.*;
import java.util.*;
import java.util.function.Predicate;

@Entity
public class Cart implements Aggregate {
    @EmbeddedId
    private CartId id;
    @Embedded
    private CustomerId customerId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cartId")
    private Set<CartItem> items;

    public Cart() {
        super();
    }

    public Cart(CartId cartId, CustomerId customerId) {
        setId(cartId);
        setCustomerId(customerId);
        items = new HashSet<>();
    }

    public void addCartItem(CartItem cartItem) {
        findCartItemByProductId(cartItem.productId()).ifPresent(item -> {
            throw new IllegalArgumentException(String.format("The product with " +
                    "id %s is already added in the cart", cartItem.productId().id()));
        });
        items.add(cartItem);
    }

    public void removeCartItemByProductId(ProductId productId) {
        CartItem currentItem = findCartItemOrThrowValidation(productId);
        items.remove(currentItem);
    }

    public void clearCart() {
        items.clear();
    }

    public float calculateTotalCost() {
        float totalCost = 0;
        for (CartItem item : items) {
            totalCost += item.cost() * item.quantity();
        }
        return totalCost;
    }

    public float calculateTotalTax() {
        float totalTax = 0;
        for (CartItem item : items) {
            totalTax += item.tax() * item.quantity();
        }
        return totalTax;
    }

    public void updateCartItemQuantity(Product product, int newQuantity,
                                       TaxCalculator taxDomainService) {
        CartItem currentItem = findCartItemOrThrowValidation(product.id());
        items.remove(currentItem);
        CartItem item = this.createCartItem(product, newQuantity, taxDomainService);
        items.add(item);
    }

    public CartItem createCartItem(Product product, int quantity,
                                   TaxCalculator taxCalculator) {
        final float tax = taxCalculator.calculateTax(product, customerId);
        return new CartItem(id, product.id(), product.price(), tax, quantity);
    }

    private CartItem findCartItemOrThrowValidation(ProductId productId) {
        return findCartItemByProductId(productId).orElseThrow(() -> {
            throw new IllegalArgumentException(String.format("The product with id %s is not in the cart", productId.id()));
        });
    }

    private Optional<CartItem> findCartItemByProductId(ProductId productId) {
        return items.stream().filter(sameProductId(productId)).findFirst();
    }

    private Predicate<CartItem> sameProductId(ProductId productId) {
        return data -> data.productId().equals(productId);
    }

    private void setId(CartId id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Cart Id is Required");
        }
        this.id = id;
    }

    private void setCustomerId(CustomerId customerId) {
        if (Objects.isNull(customerId)) {
            throw new IllegalArgumentException("Customer Id is Required");
        }
        this.customerId = customerId;
    }

    public CartId id() {
        if (Objects.isNull(id)) {
            return null;
        }
        return new CartId(id.id());
    }

    public CustomerId customerId() {
        if (Objects.isNull(customerId)) {
            return null;
        }
        return new CustomerId(customerId.id());
    }

    public Set<CartItem> items() {
        if (Objects.isNull(items)) {
            return null;
        }
        return Collections.unmodifiableSet(items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
