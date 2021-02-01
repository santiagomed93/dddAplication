package com.company.ecommerce.infrastructure.repository.cart;

import com.company.ecommerce.domain.cart.Cart;
import com.company.ecommerce.domain.cart.CartId;
import com.company.ecommerce.domain.customer.CustomerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface HibernateCartRepository extends JpaRepository<Cart, CartId> {
    Optional<Cart> findCartByCustomerId(CustomerId customerId);
}
