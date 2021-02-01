package com.company.ecommerce.infrastructure.repository.product;

import com.company.ecommerce.domain.product.Product;
import com.company.ecommerce.domain.product.ProductId;
import com.company.ecommerce.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class H2ProductRepository implements ProductRepository {

    @Autowired
    private HibernateProductRepository repository;

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public void remove(ProductId id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return repository.findById(id);
    }

    @Override
    public ProductId generateId() {
        return new ProductId(UUID.randomUUID().toString());
    }
}
