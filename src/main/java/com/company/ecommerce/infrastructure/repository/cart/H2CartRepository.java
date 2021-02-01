package com.company.ecommerce.infrastructure.repository.cart;

import com.company.ecommerce.domain.cart.Cart;
import com.company.ecommerce.domain.cart.CartId;
import com.company.ecommerce.domain.cart.CartRepository;
import com.company.ecommerce.domain.customer.CustomerId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class H2CartRepository implements CartRepository {

    @Autowired
    private HibernateCartRepository repository;

    @Override
    public CartId generateId() {
        return new CartId(UUID.randomUUID().toString());
    }

    @Override
    public Cart save(Cart cart) {
        return repository.save(cart);
    }

    @Override
    public void remove(CartId cartId) {
        repository.deleteById(cartId);
    }

    @Override
    public List<Cart> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Cart> findById(CartId cartId) {
        return repository.findById(cartId);
    }

    @Override
    public Optional<Cart> findCartByCustomerId(CustomerId customerId) {
        return repository.findCartByCustomerId(customerId);
    }
}
