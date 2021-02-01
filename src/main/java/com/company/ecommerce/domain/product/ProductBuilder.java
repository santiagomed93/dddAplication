package com.company.ecommerce.domain.product;

import java.util.function.Consumer;

public class ProductBuilder {
    public ProductId id;
    public String name;
    public String description;
    public float price;
    public int quantityOnStock;

    public ProductBuilder with(
            Consumer<ProductBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public Product buildProduct() {
        return new Product(id, name, description, price, quantityOnStock);
    }
}
