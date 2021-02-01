package com.company.ecommerce.application.product;

import com.company.ecommerce.application.Transformer;
import com.company.ecommerce.domain.exception.ResourceNotFoundException;
import com.company.ecommerce.domain.product.Product;
import com.company.ecommerce.domain.product.ProductBuilder;
import com.company.ecommerce.domain.product.ProductId;
import com.company.ecommerce.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public void createProduct(CreateProductCommand createProductCommand) {
        ProductId productId = repository.generateId();
        Product product = new ProductBuilder().with($ -> {
            $.id = productId;
            $.description = createProductCommand.getDescription();
            $.name = createProductCommand.getName();
            $.price = createProductCommand.getPrice();
            $.quantityOnStock = createProductCommand.getQuantityOnStock();
        }).buildProduct();
        repository.save(product);
    }

    @Transactional
    public void deleteProduct(String id) {
        Product product = nonNullProduct(new ProductId(id));
        repository.remove(product.id());
    }

    @Transactional
    public void decreaseQuantityStock(String id, int quantity) {
        Product product = nonNullProduct(new ProductId(id));
        product.decreaseQuantityInStock(quantity);
        repository.save(product);
    }

    @Transactional
    public void updateProduct(String id, String newName, String newDescription, float newPrice, int newQuanity) {
        Product product = nonNullProduct(new ProductId(id));
        product.update(newName, newDescription, newPrice, newQuanity);
        repository.save(product);
    }

    public <U> U findProductById(String id, Transformer<Product, U> transformer) {
        Product product = nonNullProduct(new ProductId(id));
        return transformer.transform(product);
    }

    public <U> U findAllProducts(Transformer<List<Product>, U> transformer) {
        List<Product> products = repository.findAll();
        return transformer.transform(products);
    }

    private Product nonNullProduct(ProductId id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("Product with id %s does not exist", id.id()));
        });
    }
}
