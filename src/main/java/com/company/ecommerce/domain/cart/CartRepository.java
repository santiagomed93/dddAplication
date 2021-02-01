package com.company.ecommerce.domain.cart;

import com.company.ecommerce.domain.BaseRepository;
import com.company.ecommerce.domain.customer.CustomerId;

import java.util.Optional;

public interface CartRepository extends BaseRepository<Cart, CartId> {
    CartId generateId();

    Optional<Cart> findCartByCustomerId(CustomerId customerId);
}
