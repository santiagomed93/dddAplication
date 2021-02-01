package com.company.ecommerce.domain.product;

import org.springframework.util.StringUtils;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Product {
    @EmbeddedId
    private ProductId id;
    private String name;
    private String description;
    private float price;
    private int quantityOnStock;

    public Product() {
        super();
    }

    Product(ProductId id, String name, String description, float price, int quantityOnStock) {
        setId(id);
        setName(name);
        setDescription(description);
        setQuantityOnStock(quantityOnStock);
        setPrice(price);
    }

    public void update(String newName, String newDescription, float newPrice, int newQuantityOnStock) {
        setName(newName);
        setDescription(newDescription);
        setQuantityOnStock(newQuantityOnStock);
        setPrice(newPrice);
    }

    public void decreaseQuantityInStock(int quantity) {
        if (validateIfProductQuantityIsAvailableOnStock(quantity)) {
            this.quantityOnStock -= quantity;
        }
    }

    private boolean validateIfProductQuantityIsAvailableOnStock(int quantity) {
        if (quantity > this.quantityOnStock) {
            String message = String.format("There are not enough products in stock. " +
                    "There are only %s products in stock", this.quantityOnStock);
            throw new IllegalArgumentException(message);
        }
        return true;
    }

    private void setId(ProductId productId) {
        if (Objects.isNull(productId)) {
            throw new IllegalArgumentException("The Product Id should be Valid");
        }
        this.id = productId;
    }

    private void setName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name is Required");
        }
        this.name = name;
    }

    private void setDescription(String description) {
        if (StringUtils.isEmpty(description)) {
            throw new IllegalArgumentException("Description is Required");
        }
        this.description = description;
    }

    private void setPrice(float price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price should be grater than 0");
        }
        this.price = price;
    }

    private void setQuantityOnStock(int quantityOnStock) {
        if (quantityOnStock <= 0) {
            throw new IllegalArgumentException("Quantity in Stock should be equals or grater than 0");
        }
        this.quantityOnStock = quantityOnStock;
    }

    public ProductId id() {
        if (Objects.isNull(id)) {
            return null;
        }
        return new ProductId(id.id());
    }

    public String name() {
        return name;
    }

    public float price() {
        return price;
    }

    public int quantityOnStock() {
        return quantityOnStock;
    }

    public String description() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
