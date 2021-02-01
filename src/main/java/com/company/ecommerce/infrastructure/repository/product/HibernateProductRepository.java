package com.company.ecommerce.infrastructure.repository.product;

import com.company.ecommerce.domain.product.Product;
import com.company.ecommerce.domain.product.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface HibernateProductRepository extends JpaRepository<Product, ProductId> {
}
