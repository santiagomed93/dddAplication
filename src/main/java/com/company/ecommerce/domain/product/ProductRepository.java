package com.company.ecommerce.domain.product;

import com.company.ecommerce.domain.BaseRepository;

public interface ProductRepository extends BaseRepository<Product, ProductId> {
    ProductId generateId();
}
